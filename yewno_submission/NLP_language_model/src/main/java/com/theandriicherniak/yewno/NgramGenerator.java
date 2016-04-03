package com.theandriicherniak.yewno;

/**
 * Created by andriicherniak on 4/2/16.
 */

import java.io.*;
import java.util.*;

import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.process.PTBTokenizer.PTBTokenizerFactory;

public class NgramGenerator {
    private DocumentPreprocessor dp;
    private int KK = 1;

    public NgramGenerator(String file) {
        dp = new DocumentPreprocessor(file);
    }

    public NgramGenerator(StringReader sr) {
        dp = new DocumentPreprocessor(sr);
    }

    public static boolean isWord(String s) {
        char[] ar = s.toCharArray();
        for (int i = 0; i < ar.length; i++) {
            if (!Character.isLetter(ar[i])) return false;
        }
        return true;
    }

    public void generateUnigram(NgramConsumer consumer) {

        PTBTokenizerFactory<Word> tf1 = (PTBTokenizerFactory<Word>) PTBTokenizer.factory();
        tf1.setOptions("normalizeParentheses=false," +
                "untokenizable=allDelete,ptb3Escaping=false,normalizeParentheses=false," +
                "escapeForwardSlashAsterisk=false," +
                "ptb3Dashes=true");

        dp.setTokenizerFactory(tf1);

        String[] buffer = new String[3000];
        String[] Ngram = new String[4];
        buffer[0] = "//BOS";

        int bL;

        for (List<HasWord> sentence : dp) {
            bL = 1;

            for (HasWord w : sentence) {
                String ss = w.word().toString().toLowerCase();

                if (isWord(ss)) {
                    buffer[bL] = ss;
                    bL++;
                }

            }
            buffer[bL] = "//EOS";

            for (int i = 0; i <= bL; i++) {
                Ngram[0] = buffer[i];

                consumer.consumeNgram(Ngram, 1);
            }

            for (int i = 0; i <= bL - 1; i++) {
                for (int j = i + 1; j <= Math.min(bL, i + 1 + KK); j++) {
                    Ngram[0] = buffer[i];
                    Ngram[1] = buffer[j];

                    consumer.consumeNgram(Ngram, 2);
                }
            }

            for (int i = 0; i <= bL - 2; i++) {
                for (int j = i + 1; j <= Math.min(bL - 1, i + 1 + KK); j++) {
                    for (int k = j + 1; k <= Math.min(bL, j + 1 + KK); k++) {
                        Ngram[0] = buffer[i];
                        Ngram[1] = buffer[i + 1];
                        Ngram[2] = buffer[i + 2];

                        consumer.consumeNgram(Ngram, 3);
                    }
                }
            }
        }
    }


}
