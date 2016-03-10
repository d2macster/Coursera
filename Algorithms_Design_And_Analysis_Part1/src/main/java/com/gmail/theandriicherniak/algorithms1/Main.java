package com.gmail.theandriicherniak.algorithms1;

/**
 * Created by andriicherniak on 3/2/16.
 */

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String dataFile = "/Users/andriicherniak/Desktop/SCC.txt";
        File file = new File(dataFile);
        BufferedReader reader;

        HashMap<Integer, ArrayList<Integer>> graph_original = new HashMap<Integer, ArrayList<Integer>>();
        HashMap<Integer, ArrayList<Integer>> graph_rev = new HashMap<Integer, ArrayList<Integer>>();

        int from, to;

        try {
            reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                String [] v_ar = line.split("\\s+");

                from = Integer.parseInt(v_ar[0]);
                to = Integer.parseInt(v_ar[1]);

                if (!graph_original.containsKey(from)) graph_original.put(from, new ArrayList<Integer>());
                graph_original.get(from).add(to);

                if (!graph_rev.containsKey(to)) graph_rev.put(to, new ArrayList<Integer>());
                graph_rev.get(to).add(from);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("extracted data");


        HW3_SCC scc = new HW3_SCC(graph_original, graph_rev);
        scc.computeSCC();
    }
}