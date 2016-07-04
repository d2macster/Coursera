package com.gmail.theandriicherniak.algorithms2;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by andriicherniak on 7/3/16.
 */
public class HW2_Clustering2 {
    private static int flipBit(int v){
        return (v == 1) ? 0 : 1;
    }
    private static ArrayList<Integer> permuteOnce(ArrayList<Integer> key, int Bits, HashMap<ArrayList<Integer>, ArrayList<Integer>> points){
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < Bits; i++){
            key.set(i, flipBit(key.get(i)));
            if (points.containsKey(key)){
                for (int v : points.get(key)) result.add(v);
            }
            key.set(i, flipBit(key.get(i)));
        }
        return result;
    }
    private static ArrayList<Integer> permuteTwice(ArrayList<Integer> key, int Bits, HashMap<ArrayList<Integer>, ArrayList<Integer>> points){
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < Bits - 1; i++){
            key.set(i, flipBit(key.get(i)));
            for (int j = i + 1; j < Bits; j++) {
                key.set(j, flipBit(key.get(j)));
                if (points.containsKey(key)) {
                    for (int v : points.get(key)) result.add(v);
                }
                key.set(j, flipBit(key.get(j)));
            }
            key.set(i, flipBit(key.get(i)));
        }
        return result;
    }

    public static void main(String[] args) {
        String dataFile = "/Users/andriicherniak/Desktop/clustering_big.txt";
        File file = new File(dataFile);
        BufferedReader reader;

        int N = 0;
        int Bits = 0;
        UnionFind uf = null;

        HashMap<ArrayList<Integer>, ArrayList<Integer>> points = new HashMap<ArrayList<Integer>, ArrayList<Integer>>();

        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            line = reader.readLine();
            String[] ar;
            ar = line.trim().split(" ");

            N = Integer.parseInt(ar[0]);
            Bits = Integer.parseInt(ar[1]);

            int id = 0;

            while ((line = reader.readLine()) != null) {
                ar = line.trim().split(" ");
                ArrayList<Integer> key = new ArrayList<Integer>(Bits);
                for (String s : ar) key.add(Integer.parseInt(s));
                if (!points.containsKey(key)) points.put(key, new ArrayList<Integer>());
                points.get(key).add(id);
                id++;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        uf = new UnionFind(N);

        for (ArrayList<Integer> key : points.keySet()){
            ArrayList<Integer> vv = points.get(key);
            for (int v1 : vv){
                for (int v2: vv) uf.union(v1, v2);
            }
            for (int  p : permuteOnce(key, Bits, points)){
                for (int v1 : vv){
                    uf.union(v1, p);
                }
            }
            for (int  p : permuteTwice(key, Bits, points)){
                for (int v1 : vv){
                    uf.union(v1, p);
                }
            }

        }

        System.out.println("Clusters " + uf.Nclusters());



    }
}
