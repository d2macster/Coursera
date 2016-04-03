package com.theandriicherniak.yewno;

import java.util.*;

import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.process.PTBTokenizer.PTBTokenizerFactory;
import edu.stanford.nlp.util.SystemUtils;

/**
 * Created by andriicherniak on 3/31/16.
 */
public class NgramLearning {
    private static class DataNode{
        double counter;
        DataNode parent;
        HashMap<String, DataNode> NgramHolder;

        public DataNode(DataNode parentV){
            parent = parentV;
            counter = 0.0;
            NgramHolder = new HashMap<String, DataNode>();
        }
    }

    DataNode root;

    public double getNgramProbability(String[] Ngram, int NgramL){
        DataNode ptr = root;
        for (int i = 0; i < NgramL; i++){
            String key = Ngram[i];
            if (!ptr.NgramHolder.containsKey(key)) return 0.0;
            ptr = ptr.NgramHolder.get(key);
        }
        return ptr.counter;
    }

    private void NgramHelper(String[] Ngram, int NgramL){
        DataNode ptr = root;
        for (int i = 0; i < NgramL; i++){
            String key = Ngram[i];

            if (!ptr.NgramHolder.containsKey(key)) ptr.NgramHolder.put(key, new DataNode(ptr));
            ptr = ptr.NgramHolder.get(key);
        }
        ptr.counter += 1;
    }

    private void DFSNorm(DataNode x, double parentCnt, String prefix){
        for (String key : x.NgramHolder.keySet()) DFSNorm(x.NgramHolder.get(key), x.counter, prefix + "_" + key);
        if (parentCnt > 0.0) x.counter /= parentCnt;
    }

    private boolean isWord(String s){
        char[] ar = s.toCharArray();
        for (int i = 0; i < ar.length; i++) {
            if (!Character.isLetter(ar[i])) return false;
        }
        return true;
    }

    public NgramLearning(String file, int KK) {
        root = new DataNode(null);

        DocumentPreprocessor dp = new DocumentPreprocessor(file);

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
            buffer[bL] = "/SS";

            for (int i = 0; i <= bL; i++) {
                if (i > 0 && i < bL) root.counter += 1;
                Ngram[0] = buffer[i];
                NgramHelper(Ngram, 1);
            }

            for (int i = 0; i <= bL - 1; i++) {
                for (int j = i + 1; j <= Math.min(i + 1 + KK, bL); j++) {
                    Ngram[0] = buffer[i];
                    Ngram[1] = buffer[j];

                    NgramHelper(Ngram, 2);
                }
            }

            for (int i = 0; i <= bL - 2; i++) {
                for (int j = i + 1; j <= Math.min(i + 1 + KK, bL - 1); j++) {
                    for (int k = j + 1; k <= Math.min(j + 1 + k, bL); k++) {
                        Ngram[0] = buffer[i];
                        Ngram[1] = buffer[j];
                        Ngram[2] = buffer[k];

                        NgramHelper(Ngram, 3);
                    }
                }
            }

        }


        System.out.println("normalising");

        DFSNorm(root, 0.0, "");

    }

}
