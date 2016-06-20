package noisyChannel.theandriicherniak.gmail.com;

/**
 * Created by andriicherniak on 6/16/16.
 */

import java.util.*;

public class NosyChannelModel {
    private int patternL;
    private HashMap<String, HashSet<String>> patternReplacement = new HashMap<String, HashSet<String>>();

    public boolean patternExists(String pattern){
        return patternReplacement.containsKey(pattern);
    }

    public HashSet<String> patternReplacement(String pattern){
        return patternReplacement.get(pattern);
    }

    public NosyChannelModel(int N){
        patternL = Math.min(1, N);
    }

    private String[] editDistance(String s1, String s2){
        char [] ar1 = s1.toCharArray();
        char [] ar2 = s2.toCharArray();

        int L1 = ar1.length;
        int L2 = ar2.length;

        int [][] dp = new int[L1 + 1][L2 + 1];
        int [][] path = new int[L1 + 1][L2 + 1];

        for (int i = 0; i <= L1; i++) {
            dp[i][0] = i;
            path[i][0] = 1;
        }
        for (int j = 0; j <= L2; j++) {
            dp[0][j] = j;
            path[0][j] = -1;
        }

        for (int i = 1; i <= L1; i++){
            for (int j = 1; j <= L2; j++){

                int cost = ar1[i-1] == ar2[j-1] ? 0 : 1;
                int d1 = cost + dp[i-1][j-1];
                int d2 = 1 + dp[i][j-1];
                int d3 = 1 + dp[i-1][j];

                dp[i][j] = d1;
                if (d2 < dp[i][j]){
                    dp[i][j] = d2;
                    path[i][j] = 1;
                }
                if (d3 < dp[i][j]){
                    dp[i][j] = d3;
                    path[i][j] = -1;
                }
            }
        }

        int L = L1 + L2;
        char [] match1 = new char[L];
        char [] match2 = new char[L];

        int i = L1, j = L2, k = 0;

        while (i > 0 || j > 0){
            if (i > 0 && j > 0 && ar1[i-1] == ar2[j-1]){
                match1[k] = ar1[i-1];
                match2[k] = ar2[j-1];
                i--;
                j--;
            }else if (i > 0 && j > 0 && dp[i][j] == dp[i-1][j-1] + 1){
                match1[k] = ar1[i-1];
                match2[k] = ar2[j-1];
                i--;
                j--;
            }else if (i > 0 && dp[i][j] == dp[i-1][j] + 1){
                match1[k] = ar1[i-1];
                match2[k] = '-';
                i--;
            }else if (j > 0 && dp[i][j] == dp[i][j-1] + 1){
                match1[k] = '-';
                match2[k] = ar2[j-1];
                j--;
            }else {
                throw new IllegalArgumentException("Some wrong with given data");
            }

            k ++;
        }

        StringBuilder sb = new StringBuilder();
        for (i = k-1; i >= 0; i--) sb.append(match1[i]);
        String ss1 = sb.toString();

        sb.setLength(0);
        for (i = k-1; i >= 0; i--) sb.append(match2[i]);
        String ss2 = sb.toString();

        return new String[]{ss1, ss2, ""+dp[L1][L2]};
    }
    private void insertPattern(String pattern, String replacement){
        if (!pattern.equals(replacement)){

            if (!patternReplacement.containsKey(pattern)) patternReplacement.put(pattern, new HashSet<String>());
            patternReplacement.get(pattern).add(replacement);
//            System.out.println("p: " + pattern + " r: " + replacement);
        }
    }
    public void processInput(String word, String spelling){
        String[] match = editDistance(word, spelling);
//        System.out.println(word + " " + spelling + " " + match[0] + " : " + match[1] + " : " + match[2]);

        StringBuilder sb_w = new StringBuilder();
        StringBuilder sb_s = new StringBuilder();
        int wL, j;

        int LL = match[0].length();

        for (int i = 0; i < LL; i++){
            sb_w.setLength(0);
            sb_s.setLength(0);

            wL = 0;
            j = i;

            while (j < LL && wL <= patternL){
                if (match[1].charAt(j) != '-') sb_s.append(match[1].charAt(j));

                if (match[0].charAt(j) != '-'){
                    sb_w.append(match[0].charAt(j));
                    wL ++;

                    insertPattern(sb_w.toString(), sb_s.toString());
                }
                j++;
            }

        }
    }
}