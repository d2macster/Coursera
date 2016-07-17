package com.gmail.theandriicherniak.algorithms2;

import java.util.HashMap;

/**
 * Created by andriicherniak on 7/16/16.
 */
public class HW4_BellmanFord {
    public static void iteration(int V, int [] distance, int [] new_distance, HashMap<Integer, HashMap<Integer, Integer>> graph){
        for (int v = 1; v <= V; v++){
            new_distance[v] = distance[v];
            if (graph.containsKey(v)){
                for (int vv : graph.get(v).keySet()){
                    new_distance[v] = Math.min(new_distance[v], distance[vv] + graph.get(v).get(vv));
                }
            }
        }
    }
}
