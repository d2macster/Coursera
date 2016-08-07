package com.gmail.theandriicherniak.algorithms2;

import java.io.*;
import java.util.*;

/**
 * Created by andriicherniak on 8/5/16.
 */
public class HW6_2SAT {
    private static void DFS_order(HashMap<Integer, HashSet<Integer>> graph, int curV, HashMap<Integer, Boolean> visited, Stack<Integer> order){
        if (!visited.get(curV)){
            visited.put(curV, true);

            if (graph.containsKey(curV)){
                for (int v : graph.get(curV)){
                    DFS_order(graph, v, visited, order);
                }
            }
            order.push(curV);
        }
    }
    public static void main(String[] args){
        String dataFile = "/Users/andriicherniak/Desktop/2sat6.txt";

        File file = new File(dataFile);
        BufferedReader reader;
        String[] ar;
        String line;

        HashMap<Integer, HashSet<Integer>> graph = new HashMap<Integer, HashSet<Integer>>();
        HashMap<Integer, HashSet<Integer>> graph_rev = new HashMap<Integer, HashSet<Integer>>();
        HashMap<Integer, Boolean> visited = new HashMap<Integer, Boolean>();

        try {
            reader = new BufferedReader(new FileReader(file));
            line = reader.readLine().trim();
            int N = Integer.parseInt(line);

            for (int i = 0; i < N; i++){
                line = reader.readLine().trim();
                ar = line.split(" ");

                int c1 = Integer.parseInt(ar[0]);
                int c2 = Integer.parseInt(ar[1]);

                if (!graph.containsKey(-1*c1)) graph.put(-1*c1, new HashSet<Integer>());
                if (!graph.containsKey(-1*c2)) graph.put(-1*c2, new HashSet<Integer>());

                graph.get(-1*c1).add(c2);
                graph.get(-1*c2).add(c1);

                visited.put(c1, false);
                visited.put(c2, false);
                visited.put(-1*c1, false);
                visited.put(-1*c2, false);
            }

        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }

        for (int v1 : graph.keySet()){
            for (int v2 : graph.get(v1)){
                if (!graph_rev.containsKey(v2)) graph_rev.put(v2, new HashSet<Integer>());
                graph_rev.get(v2).add(v1);
            }
        }

        Stack<Integer> order = new Stack<Integer>();

        for (int v : visited.keySet()){
            if (!visited.get(v)) DFS_order(graph, v, visited, order);
        }

        for (int v : visited.keySet()) visited.put(v, false);

        int scc_id = 0;
        HashMap<Integer, HashSet<Integer>> vertexSCC = new HashMap<Integer, HashSet<Integer>>();

        while (!order.isEmpty()){
            int v = order.pop();

            Stack<Integer> ccElements = new Stack<Integer>();
            DFS_order(graph_rev, v, visited, ccElements);

            if (ccElements.size() > 0) {
                for (int el : ccElements){
                    if (!vertexSCC.containsKey(el)) vertexSCC.put(el, new HashSet<Integer>());
                    vertexSCC.get(el).add(scc_id);
                }

                scc_id ++;
            }
        }

        for (int v : visited.keySet()){
            HashSet<Integer> s1 = vertexSCC.get(v);
            HashSet<Integer> s2 = vertexSCC.get(-v);
            for (int ss : s1){
                if (s2.contains(ss)) System.out.println("conflict");
            }
        }


    }
}
