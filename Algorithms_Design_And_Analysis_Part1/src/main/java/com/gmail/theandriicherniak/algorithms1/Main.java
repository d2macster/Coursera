package com.gmail.theandriicherniak.algorithms1;

/**
 * Created by andriicherniak on 3/2/16.
 */

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String dataFile = "/Users/andriicherniak/Desktop/kargerMinCut.txt";
        File file = new File(dataFile);
        BufferedReader reader = null;

        Random rand = new Random(System.currentTimeMillis());


        HashMap<Integer, ArrayList<Integer>> graph_connectivity = new HashMap<Integer, ArrayList<Integer>>();

        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;

            while ((line = reader.readLine()) != null) {
                ArrayList<Integer> v_set = new ArrayList<Integer>();
                String[] v_ar = line.split("\\s+");

                for (int i = 1; i < v_ar.length; i++) {
                    v_set.add(Integer.parseInt(v_ar[i]));
                }

                graph_connectivity.put(Integer.parseInt(v_ar[0]), v_set);
            }

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        HashMap<Integer, ArrayList<Integer>> graph_copy = new HashMap<Integer, ArrayList<Integer>>();
        int minCutValue = graph_connectivity.keySet().size();

        for (int j = 1; j <= 1000; j++) {

            for (int v : graph_connectivity.keySet()){
                ArrayList<Integer> vv = new ArrayList<Integer>();
                for (int to : graph_connectivity.get(v)){
                    vv.add(to);
                }
                graph_copy.put(v, vv);
            }

            HW3_MinCut minCut = new HW3_MinCut(graph_copy, rand);
            int [] result = minCut.contraction();

            if (result.length != 2) System.out.println("error");
            if (result[0] != result[1]) System.out.println("error");

            minCutValue = Math.min(minCutValue, result[0]);
        }

        System.out.println(minCutValue);
    }
}
