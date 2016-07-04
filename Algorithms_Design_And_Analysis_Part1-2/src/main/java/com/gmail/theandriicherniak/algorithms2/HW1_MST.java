package com.gmail.theandriicherniak.algorithms2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by andriicherniak on 6/19/16.
 */
public class HW1_MST {

    public static class Edge{
        int from;
        int to;
        int weight;

        public Edge(int v1, int v2, int w){
            from = v1;
            to = v2;
            weight = w;
        }
    }

    public static class EdgeComparator implements Comparator<Edge>{
        public int compare(Edge e1, Edge e2){
            if (e1.weight < e2.weight) return -1;
            else if (e1.weight > e2.weight) return 1;
            else return 0;
        }
    }
    public static void main(String [] args){
        String data = "/Users/andriicherniak/Desktop/data/edges.txt";
        File f = new File(data);

        HashMap<Integer, HashMap<Integer, Integer>> graph = new HashMap<Integer, HashMap<Integer, Integer>>();
        HashSet<Integer> MSTv = new HashSet<Integer>();

        PriorityQueue<Edge> q = new PriorityQueue<Edge>(100, new EdgeComparator());

        long cost = 0;

        try{

            BufferedReader br = new BufferedReader(new FileReader(f));
            String [] ar;
            ar = br.readLine().trim().replaceAll("\\s+", " ").split(" ");
            int E = Integer.parseInt(ar[1]);

            for (int i = 0; i < E; i++){
                ar = br.readLine().trim().replaceAll("\\s+", " ").split(" ");

                int from = Integer.parseInt(ar[0]);
                int to = Integer.parseInt(ar[1]);
                int w = Integer.parseInt(ar[2]);

                if (!graph.containsKey(from)) graph.put(from, new HashMap<Integer, Integer>());
                if (!graph.containsKey(to)) graph.put(to, new HashMap<Integer, Integer>());

                graph.get(from).put(to, w);
                graph.get(to).put(from, w);

//                System.out.println(from + " " + to + " " + w);

            }

            br.close();
        }catch (IOException e){
            System.out.println(e.getLocalizedMessage());
        }

        MSTv.add(1);
        for (int v : graph.get(1).keySet()){
            q.add(new Edge(1, v, graph.get(1).get(v)));
        }

        while (!q.isEmpty()){
            Edge e = q.remove();

//            if (!MSTv.contains(e.from) || !MSTv.contains(e.to)) {
//                System.out.println("edge " + e.from + " " + e.to + " w " + e.weight);
//            }

            if (!MSTv.contains(e.from)){
                cost += e.weight;

                MSTv.add(e.from);

                for (int v : graph.get(e.from).keySet()){
                    q.add(new Edge(e.from, v, graph.get(e.from).get(v)));
                }
            }
            if (!MSTv.contains(e.to)){
                MSTv.add(e.to);
                cost += e.weight;
                for (int v : graph.get(e.to).keySet()){
                    q.add(new Edge(e.to, v, graph.get(e.to).get(v)));
                }
            }
        }

        System.out.println("COST " + cost);

    }
}
