package com.gmail.theandriicherniak.algorithms2;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by andriicherniak on 7/3/16.
 */
public class HW2_Clustering1 {
    private static class VC implements Comparator<Edge> {
        public int compare(Edge e1, Edge e2) {
            return e1.vw.compareTo(e2.vw);
        }
    }

    private static class Edge {
        Integer vv1, vv2, vw;

        public Edge(Integer v1, Integer v2, Integer w) {
            vv1 = v1;
            vv2 = v2;
            vw = w;
        }
    }

    public static void main(String[] args) {
        String dataFile = "/Users/andriicherniak/Desktop/clustering1.txt";
        File file = new File(dataFile);
        BufferedReader reader;

        int V = 0;
        int clusters = 4;
        PriorityQueue<Edge> q = new PriorityQueue<Edge>(1000, new VC());
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            V = Integer.parseInt(reader.readLine().trim());

            while ((line = reader.readLine()) != null){
                String[] ar = line.trim().split(" ");
                q.add(new Edge(Integer.parseInt(ar[0]) - 1, Integer.parseInt(ar[1]) - 1, Integer.parseInt(ar[2])));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        UnionFind uf = new UnionFind(V);
        int d = 0;

        while (!q.isEmpty() && uf.Nclusters() >= clusters) {
            Edge e = q.remove();
            d = Math.max(d, e.vw);
            uf.union(e.vv1, e.vv2);
        }

        System.out.println("D cluster spacing " + d);
    }
}
