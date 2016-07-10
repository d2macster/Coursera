package com.gmail.theandriicherniak.algorithms2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by andriicherniak on 7/8/16.
 */
public class HW3_Knapsack {
    public static void main(String[] args) {
        String dataFile = "/Users/andriicherniak/Desktop/knapsack1.txt";

        File file = new File(dataFile);
        BufferedReader reader;
        int[][] items = null; // value, weight
        int W = 0, N = 0;

        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine().trim();
            String[] ar = line.split(" ");
            W = Integer.parseInt(ar[0]);
            N = Integer.parseInt(ar[1]);
            items = new int[N][2];

            for (int i = 0; i < N; i++) {
                line = reader.readLine().trim();
                ar = line.split(" ");
                items[i][0] = Integer.parseInt(ar[0]);
                items[i][1] = Integer.parseInt(ar[1]);
            }

        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }

        HashMap<Integer, Integer> knapsack = new HashMap<Integer, Integer>(); // weight, value
        HashMap<Integer, Integer> knapsack_next;
        knapsack.put(0, 0);

        for (int i = 0; i < N; i++) {
            knapsack_next = new HashMap<Integer, Integer>();
            int w = items[i][1];
            int v = items[i][0];

            for (int old_w : knapsack.keySet()) {
                int old_v = knapsack.get(old_w);
                int new_w = old_w + w;
                int new_v = old_v + v;

                if (!knapsack_next.containsKey(old_w)) knapsack_next.put(old_w, old_v);
                else knapsack_next.put(old_w, Math.max(knapsack_next.get(old_w), old_v));

                if (new_w <= W) {
                    if (!knapsack_next.containsKey(new_w)) knapsack_next.put(new_w, new_v);
                    else knapsack_next.put(new_w, Math.max(knapsack_next.get(new_w), new_v));
                }
            }

            knapsack = knapsack_next;
        }

        int maxV = 0;

        for (int key : knapsack.keySet()) {
            maxV = Math.max(maxV, knapsack.get(key));
        }

        System.out.println(maxV);


    }
}
