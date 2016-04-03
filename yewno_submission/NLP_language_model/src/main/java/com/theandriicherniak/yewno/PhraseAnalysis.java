package com.theandriicherniak.yewno;

import java.util.*;

/**
 * Created by andriicherniak on 4/1/16.
 */
public class PhraseAnalysis implements NgramConsumer{
    private HashSet<String> vocabulary;
    private NgramLearning model;
    private double score;

    public PhraseAnalysis(NgramLearning modelInit){
        model = modelInit;
        vocabulary = model.getVocabulary();
        score = 0.0;
    }

    private double computeNgramP(String[] Ngram, int NgramL){
        if (NgramL == 1) return 1.0 * model.getNgramCount(Ngram, NgramL) / model.getCorpusSize();
        int cnt1 = model.getNgramCount(Ngram, NgramL);
        if (cnt1 > 0) return 1.0 * cnt1 / model.getNgramCount(Ngram, NgramL - 1);
        else {
            for (int i = 0; i < NgramL - 1; i++) Ngram[i] = Ngram[i + 1];
            return 0.4 * computeNgramP(Ngram, NgramL - 1);
        }
    }

    public void consumeNgram(String[] Ngram, int NgramL){
        if (NgramL == 3) {
            for (int i = 0; i < NgramL; i++) {
                if (!vocabulary.contains(Ngram[i])) Ngram[i] = "//UNK";
//                System.out.print(Ngram[i] + "_");
            }

            double p = computeNgramP(Ngram, NgramL);
//            System.out.println(" math log " + Math.log(p));
//            if (p == 0.0){
//                for (int i = 0; i < NgramL; i++) System.out.print(Ngram[i] + "_");
//                System.out.println();
//            }
            score += Math.log(p);
        }
    }

    public double getScore(){
        return score;
    }
}
