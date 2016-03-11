package com.gmail.theandriicherniak.algorithms1;

/**
 * Created by andriicherniak on 3/2/16.
 */

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String dataFile = "/Users/andriicherniak/Desktop/dijkstraData.txt";
        File file = new File(dataFile);
        BufferedReader reader;

        HashMap<Integer, HashMap<Integer, Integer>> graph_weights = new HashMap<Integer, HashMap<Integer, Integer>>();

        try {
            reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                String [] v_ar = line.split("\\s+");
                int from = Integer.parseInt(v_ar[0]);

                graph_weights.put(from, new HashMap<Integer, Integer>());

                for (int i = 1; i < v_ar.length; i++){
                    String [] link_data = v_ar[i].split(",");
                    int to = Integer.parseInt(link_data[0]);
                    int weight = Integer.parseInt(link_data[1]);

                    graph_weights.get(from).put(to, weight);
                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("extracted data");

        HW4_Dijkstra d = new HW4_Dijkstra(graph_weights);
        d.run();

    }
}