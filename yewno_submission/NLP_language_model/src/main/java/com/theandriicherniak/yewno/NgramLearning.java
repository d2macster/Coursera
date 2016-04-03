package com.theandriicherniak.yewno;

import java.util.*;


/**
 * Created by andriicherniak on 3/31/16.
 */
public class NgramLearning implements NgramConsumer{
    private static class DataNode{
        int counter;
        DataNode parent;
        HashMap<String, DataNode> NgramHolder;

        public DataNode(DataNode parentV){
            parent = parentV;
            counter = 0;
            NgramHolder = new HashMap<String, DataNode>();
        }
    }

    private DataNode root;
    private HashSet<String> vocabulary;
    private int corpusSize;
    private Random rnd;

    public HashSet<String> getVocabulary() {
        return vocabulary;
    }

    public int getNgramCount(String[] Ngram, int NgramL){
        DataNode ptr = root;
        for (int i = 0; i < NgramL; i++){
            String key = Ngram[i];
            if (!ptr.NgramHolder.containsKey(key)) return 0;
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

        ptr.counter ++;
    }

    public NgramLearning(HashSet<String> voc) {
        root = new DataNode(null);
        corpusSize = 0;
        rnd = new Random();
        vocabulary = voc;
    }

    public int getCorpusSize(){
        return corpusSize;
    }

    public void consumeNgram(String[] Ngram, int NgramL){
        //preparing for unknown words
        for (int i = 0; i < NgramL; i++){
            if (!vocabulary.contains(Ngram[i])) Ngram[i] = "//UNK";
            vocabulary.add(Ngram[i]);
        }

        NgramHelper(Ngram, NgramL);
        if (NgramL == 1 && !Ngram[0].equals("//BOS") && !Ngram[0].equals("//EOS")) corpusSize ++;

    }

}
