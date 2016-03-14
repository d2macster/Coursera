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


//        try {
//            reader = new BufferedReader(new FileReader(file));
//            String line;
//
//            while ((line = reader.readLine()) != null) {
//                String [] v_ar = line.split("\\s+");
//                int from = Integer.parseInt(v_ar[0]);
//
//                graph_weights.put(from, new HashMap<Integer, Integer>());
//
//                for (int i = 1; i < v_ar.length; i++){
//                    String [] link_data = v_ar[i].split(",");
//                    int to = Integer.parseInt(link_data[0]);
//                    int weight = Integer.parseInt(link_data[1]);
//
//                    graph_weights.get(from).put(to, weight);
//                }
//
//            }
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//        System.out.println("extracted data");


        Random rnd = new Random();
        BalancedBST bst = new BalancedBST();
        for (int i = 0; i <= 100; i ++) bst.insert(i);

        System.out.println("max " + bst.max() + " pred " + bst.pred(bst.max()));
        System.out.println("min " + bst.min() + " succ " + bst.succ(bst.min()));
        System.out.println(" is balanced : " + bst.is_balanced());


        bst.show();


    }
}