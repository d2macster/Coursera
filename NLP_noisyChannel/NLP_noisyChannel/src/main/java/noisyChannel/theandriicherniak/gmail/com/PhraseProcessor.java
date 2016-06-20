package noisyChannel.theandriicherniak.gmail.com;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by andriicherniak on 6/17/16.
 */

public class PhraseProcessor implements DataProcessor {
    private int alterations;
    private int patternL;
    private NosyChannelModel noisyModel;

    public PhraseProcessor(int N, int L, NosyChannelModel model){
        alterations = N;
        noisyModel = model;
        patternL = Math.max(1, L);
    }

    public void processLine(String line){
        String ss = line.trim().replaceAll("\\s+", " ");
        int L = ss.length();

        StringBuilder sb = new StringBuilder();

        int from, to, cnt = 0, pL = patternL;

        while (cnt < alterations && pL >= 1){
            from = 0;
            to = from + pL;
            while (cnt < alterations && to <=L){
                sb.setLength(0);
                for (int i = from; i < to; i++){
                    sb.append(ss.charAt(i));
                }
                String sP = sb.toString();
                if (noisyModel.patternExists(sP)){
                    Iterator<String> sR = noisyModel.patternReplacement(sP).iterator();

                    while (cnt < alterations && sR.hasNext()){
                        String R = sR.next();
                        cnt++;

                        System.out.println("Typo " + cnt + ": " + ss.replaceAll(sP, R));
                    }
                }
                from ++;
                to ++;
            }
            pL--;
        }


    }
}
