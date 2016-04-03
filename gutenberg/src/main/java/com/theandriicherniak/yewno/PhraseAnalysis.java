package com.theandriicherniak.yewno;

import java.util.*;

import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.process.PTBTokenizer.PTBTokenizerFactory;
import edu.stanford.nlp.util.SystemUtils;

import java.io.*;
/**
 * Created by andriicherniak on 4/1/16.
 */
public class PhraseAnalysis {
    public static boolean isWord(String s){
        char[] ar = s.toCharArray();
        for (int i = 0; i < ar.length; i++) {
            if (!Character.isLetter(ar[i])) return false;
        }
        return true;
    }

    public static void score(NgramLearning model, String phrase){
        Reader reader = new StringReader(phrase);

        DocumentPreprocessor dp = new DocumentPreprocessor(reader);

        PTBTokenizerFactory<Word> tf1 = (PTBTokenizerFactory<Word>) PTBTokenizer.factory();
        tf1.setOptions("normalizeParentheses=false," +
                "untokenizable=allDelete,ptb3Escaping=false,normalizeParentheses=false," +
                "escapeForwardSlashAsterisk=false," +
                "ptb3Dashes=true");


        dp.setTokenizerFactory(tf1);

        String[] buffer = new String[300];
        String[] Ngram = new String[4];
        buffer[0] = "SS";

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
            buffer[bL] = "/SS";        }
        }
}
