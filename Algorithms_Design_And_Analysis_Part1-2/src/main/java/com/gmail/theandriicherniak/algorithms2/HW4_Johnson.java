package com.gmail.theandriicherniak.algorithms2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by andriicherniak on 7/16/16.
 */
public class HW4_Johnson {
    public static void main(String[] args){
        String dataFile = "/Users/andriicherniak/Desktop/g3.txt";
        HashMap<Integer, HashMap<Integer, Integer>> graph_inbound = new HashMap<Integer, HashMap<Integer, Integer>>();

        File file = new File(dataFile);
        BufferedReader reader;
        int V = 0, E = 0;

        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            String[] ar = line.trim().split(" ");
            V = Integer.parseInt(ar[0]);
            E = Integer.parseInt(ar[1]);
            int v1, v2, w;

            for (int i = 1; i <= E; i++){
                line = reader.readLine();
                ar = line.trim().split(" ");
                v1 = Integer.parseInt(ar[0]);
                v2 = Integer.parseInt(ar[1]);
                w = Integer.parseInt(ar[2]);
                if (!graph_inbound.containsKey(v2)) graph_inbound.put(v2, new HashMap<Integer, Integer>());
                graph_inbound.get(v2).put(v1, w);
            }

            for (int i = 1; i <= V; i++) graph_inbound.get(1).put(0, 0);
        }catch (IOException e){
            System.out.println(e.getLocalizedMessage());
        }

        int [] distance = new int[V + 1];
        int [] new_distance = new int[V + 1];
        int [] tmp;

        for (int i = 1; i <= V; i++) {
            HW4_BellmanFord.iteration(V, distance, new_distance, graph_inbound);
            tmp = distance;
            distance = new_distance;
            new_distance = tmp;
        }

        HW4_BellmanFord.iteration(V, distance, new_distance, graph_inbound);

        boolean negative_cycle = false;

        for (int v = 1; v <= V; v++){
            if (new_distance[v] < distance[v]) negative_cycle = true;
        }

        System.out.println("negative cycle " + negative_cycle);

        if (!negative_cycle){
            HashMap<Integer, HashMap<Integer, Integer>> graph_outbound = new HashMap<Integer, HashMap<Integer, Integer>>();
            for (int to : graph_inbound.keySet()){
                for (int from : graph_inbound.get(to).keySet()){
                    int ww = graph_inbound.get(to).get(from) + distance[from] - distance[to];
                    if (!graph_outbound.containsKey(from)) graph_outbound.put(from, new HashMap<Integer, Integer>());
                    graph_outbound.get(from).put(to, ww);
                }
            }

            int minD = Integer.MAX_VALUE;

            for (int v = 1; v <= V; v++) {
                HW4_Dijkstra dijkstra = new HW4_Dijkstra(graph_outbound);
                dijkstra.run(v);
                for (int vv : dijkstra.nodeDistance.keySet()) {
                    int ww = dijkstra.nodeDistance.get(vv) + distance[vv] - distance[v];
                    minD = Math.min(minD, ww);
                }
            }

            System.out.println("Min distance " + minD);
        }

    }
}
