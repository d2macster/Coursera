package com.theandriicherniak.yewno;

import java.util.*;

/**
 * Created by andriicherniak on 4/2/16.
 */
public class DictionaryLearner implements NgramConsumer {
    private HashMap<String, Integer> wordCount = new HashMap<String, Integer>();

    public void consumeNgram(String[] Ngram, int NgramL){
        if (NgramL == 1){
            if (!wordCount.containsKey(Ngram[0])) wordCount.put(Ngram[0], 0);
            wordCount.put(Ngram[0], wordCount.get(Ngram[0]) + 1);
        }
    }

    public HashSet<String> getVocabulary(int minN){
        HashSet<String> ss = new HashSet<String>();
        for (String key : wordCount.keySet()){
            if (wordCount.get(key) >= minN) ss.add(key);
        }

        return ss;
    }
}
