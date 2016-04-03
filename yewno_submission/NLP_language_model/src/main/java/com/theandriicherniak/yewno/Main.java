package com.theandriicherniak.yewno;

import java.io.*;
import java.util.*;


public class Main {

    public static boolean isValidName(String s) {
        if (s.equals(".DS_Store")) return false;
        return true;
    }

    public static void main(String[] args) {
        System.out.println("running project gutenberg ananysis");

        String inputDir = "/Users/andriicherniak/Desktop/gutenberg/";

        String query = "The witnesses of the crime are of all nations, and there is no possibility\n" +
                "of error concerning facts. There are British consuls like Casement,\n" +
                "Thesiger, Mitchell and Armstrong, all writing in their official capacity\n" +
                "with every detail of fact and date. There are Frenchmen like Pierre Mille\n" +
                "and Félicien Challaye, both of whom have written books upon the subject.\n" +
                "There are missionaries of many races--Harris, Weeks and Stannard\n" +
                "(British); Morrison, Clarke and Shepherd (American); Sjoblom (Swedish) and\n" +
                "Father Vermeersch, the Jesuit. There is the eloquent action of the Italian\n" +
                "Government, who refused to allow Italian officers to be employed any\n" +
                "longer in such hangman's work, and there is the report of the Belgian\n" +
                "commission, the evidence before which was suppressed because it was too\n" +
                "dreadful for publication; finally, there is the incorruptible evidence of\n" +
                "the kodak. Any American citizen who will glance at Mark Twain's \"King\n" +
                "Leopold's Soliloquy\" will see some samples of that. A perusal of all of\n" +
                "these sources of information will show that there is not a grotesque,\n" +
                "obscene or ferocious torture which human ingenuity could invent which has\n" +
                "not been used against these harmless and helpless people.\n" +
                "\n" +
                "This would, to my mind, warrant our intervention in any case. Turkey has\n" +
                "several times been interfered with simply on the general ground of\n" +
                "humanity. There is in this instance a very special reason why America and\n" +
                "England should not stand by and see these people done to death. They are,\n" +
                "in a sense, their wards. America was the first to give official\n" +
                "recognition to King Leopold's enterprise in 1884, and so has the\n" +
                "responsibility of having actually put him into that position which he has\n" +
                "so dreadfully abused. She has been the indirect and innocent cause of the\n" +
                "whole tragedy. Surely some reparation is due. On the other hand England\n" +
                "has, with the other European Powers, signed the treaty of 1885, by which\n" +
                "each and all of them make it responsible for the condition of the native\n" +
                "races. The other Powers have so far shown no desire to live up to this\n" +
                "pledge. But the conscience of England is uneasy and she is slowly rousing\n" +
                "herself to act. Will America be behind?";

        File fd_author = new File(inputDir);

        String bestAuthorMatch = "";
        String bestNovelMatch = "";
        double authorScore = -Double.MAX_VALUE;
        double novelScore = -Double.MAX_VALUE;

        double score;

        HashMap<String, ArrayList<String>> author_novel = new HashMap<String, ArrayList<String>>();

        for (File author : fd_author.listFiles()) {
            String authorName = author.getName();
            if (isValidName(authorName)) {

                author_novel.put(authorName, new ArrayList<String>());

                String authorPath = inputDir + authorName + "/";

                File fd_novels = new File(authorPath);

                for (File novel : fd_novels.listFiles()) {

                    if (isValidName(novel.getName())) {
                        author_novel.get(authorName).add(novel.getName());
                    }
                }
            }
        }

        for (String authorName : author_novel.keySet()) {
            for (String novelName : author_novel.get(authorName)) {
                String novelPath = inputDir + authorName + "/" + novelName;

                NgramGenerator nggDictionary = new NgramGenerator(novelPath);
                DictionaryLearner dl = new DictionaryLearner();
                nggDictionary.generateUnigram(dl);
                HashSet<String> vocabulary = dl.getVocabulary(2);


                NgramGenerator nggNovel = new NgramGenerator(novelPath);
                NgramLearning nglNovel = new NgramLearning(vocabulary);
                nggNovel.generateUnigram(nglNovel);

                NgramGenerator nggQ = new NgramGenerator(new StringReader(query));
                PhraseAnalysis pa = new PhraseAnalysis(nglNovel);
                nggQ.generateUnigram(pa);

                score = pa.getScore();

//                System.out.println(novelPath + " " + score);

                if (score > novelScore && score != Double.POSITIVE_INFINITY && score != Double.NEGATIVE_INFINITY) {
                    novelScore = score;
                    bestNovelMatch = authorName + " : " + novelName;
                }
            }
        }

        for (String authorName : author_novel.keySet()) {
            DictionaryLearner dl = new DictionaryLearner();

            for (String novelName : author_novel.get(authorName)) {
                String novelPath = inputDir + authorName + "/" + novelName;

                NgramGenerator nggNovel = new NgramGenerator(novelPath);
                nggNovel.generateUnigram(dl);
            }

            HashSet<String> vocabulary = dl.getVocabulary(2);
            NgramLearning nglAuthor = new NgramLearning(vocabulary);

            for (String novelName : author_novel.get(authorName)) {
                String novelPath = inputDir + authorName + "/" + novelName;

                NgramGenerator nggNovel = new NgramGenerator(novelPath);
                nggNovel.generateUnigram(nglAuthor);
            }

            NgramGenerator nggQ = new NgramGenerator(new StringReader(query));
            PhraseAnalysis pa = new PhraseAnalysis(nglAuthor);
            nggQ.generateUnigram(pa);

            score = pa.getScore();

            if (score > authorScore && score != Double.POSITIVE_INFINITY && score != Double.NEGATIVE_INFINITY) {
                authorScore = score;
                bestAuthorMatch = authorName;
            }
        }

        System.out.println("Best novel match : " + bestNovelMatch);
        System.out.println("In case we have not seen this passage before, it most probably comes from :" + bestAuthorMatch);

    }
}
