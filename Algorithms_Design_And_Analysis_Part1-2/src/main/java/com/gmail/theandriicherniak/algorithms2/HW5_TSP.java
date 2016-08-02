package com.gmail.theandriicherniak.algorithms2;

import java.io.*;
import java.util.*;

/**
 * Created by andriicherniak on 7/28/16.
 */
public class HW5_TSP {
    public static void main(String[] args) {

        String dataFile = "/Users/andriicherniak/Desktop/tsp.txt";

        File file = new File(dataFile);
        BufferedReader reader;
        double[][] cities = null;
        double[][] distance = null;
        int N = 0;

        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine().trim();
            String[] ar = line.split(" ");
            N = Integer.parseInt(ar[0]);

            cities = new double[N][2];
            distance = new double[N][N];

            for (int i = 0; i < N; i++) {
                line = reader.readLine().trim();
                ar = line.split(" ");
                cities[i][0] = Double.parseDouble(ar[0]);
                cities[i][1] = Double.parseDouble(ar[1]);
            }

        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i != j) {
                    double deltaX = cities[i][0] - cities[j][0];
                    double deltaY = cities[i][1] - cities[j][1];

                    distance[i][j] = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                }
            }
        }

        HashMap<Integer, HashSet<Integer>> combinations = new HashMap<Integer, HashSet<Integer>>();
        combinations.put(0, new HashSet<Integer>());
        combinations.get(0).add(1);

        for (int iteration = 1; iteration < N; iteration++) {
            combinations.put(iteration, new HashSet<Integer>());
            for (int path : combinations.get(iteration - 1)) {
                for (int to = 1; to < N; to++) {
                    if ((path & 1 << to) == 0) {
                        int new_path = path | 1 << to;
                        combinations.get(iteration).add(new_path);
                    }
                }
            }
            System.out.println(iteration);
            System.out.println(combinations.get(iteration).size());
        }

        System.out.println("generated path combinations");
        int L = (1 << N);
        System.out.println(Integer.toBinaryString(L) + " " + Integer.toBinaryString(L - 1));

        double[][] TSP = new double[N][L];
        System.out.println("Memory allocated");
        for (int v = 0; v < N; v++) {
            System.out.println(v);
            for (int i = 0; i < L; i++) TSP[v][i] = Double.MAX_VALUE;
        }

        TSP[0][1] = 0.0;

        for (int iteration = 1; iteration < N; iteration++) {
            System.out.println("iteration " + iteration);

            for (int path : combinations.get(iteration)) {
                for (int v = 1; v < N; v++) {
                    int bitmap = 1 << v;
                    if ((path & bitmap) > 0) {
                        int path1 = path & (~bitmap);
                        for (int vv = 0; vv < N; vv++){
                            int bitmap1 = 1 << vv;
                            if (((path1 & bitmap1) > 0) && (TSP[vv][path1] < Double.MAX_VALUE)){
                                TSP[v][path] = Math.min(TSP[v][path], TSP[vv][path1] + distance[vv][v]);
                            }
                        }
                    }
                }
            }
        }

        double TSP_min = Double.MAX_VALUE;

        for (int v = 1; v < N; v++) {
            TSP_min = Math.min(TSP_min, TSP[v][L-1] + distance[v][0]);
        }

        System.out.println("TSP_min " + TSP_min);


    }
}
