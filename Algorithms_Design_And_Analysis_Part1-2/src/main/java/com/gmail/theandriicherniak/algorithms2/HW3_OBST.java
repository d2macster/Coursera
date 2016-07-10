package com.gmail.theandriicherniak.algorithms2;

/**
 * Created by andriicherniak on 7/8/16.
 */
public class HW3_OBST {
    private static double C(int i, int j, double[][] dp){
        if (i <= j) return dp[i][j];
        else return 0.0;
    }
    public static void main(String[] args){
        double [] w = new double[]{0.05, 0.4, 0.08, 0.04, 0.1, 0.1, 0.23};
        int L = w.length;
        double[][] dp = new double[L][L];
        for (int l = 0; l < L; l++)
        for (int i = 0; i < L; i++){
            int j = i + l;

            if (j < L) {
                dp[i][j] = Double.MAX_VALUE;
                double p = 0.0;
                for (int k = i; k <= j; k++) p += w[k];
                for (int r = i; r <= j; r++){
                    dp[i][j] = Math.min(dp[i][j], p + C(i, r-1, dp) + C(r+1,j, dp));
                }
                
            }
        }

        System.out.println(dp[0][L-1]);
    }
}
