package com.gmail.theandriicherniak.algorithms1;

import java.util.*;

/**
 * Created by andriicherniak on 3/6/16.
 */
public class HW3_MinCut {

    private HashMap<Integer, ArrayList<Integer>> graph;
    Random rand;

    public HW3_MinCut(HashMap<Integer, ArrayList<Integer>> graph_copy, Random rnd){
        graph = new HashMap<Integer, ArrayList<Integer>>(graph_copy);
        rand = rnd;
    }

    private int [] vToMerge(){
        int [] result = new int[]{-1, -1};
        ArrayList<Integer> v1 = new ArrayList<Integer>(graph.keySet());

        while (result[0] == -1 || result[1] == -1 || result[0] == result[1]) {
            int pos1 = (int) Math.round(rand.nextDouble() * (v1.size() - 1));

            result[0] = v1.get(pos1);

            ArrayList<Integer> v2 = new ArrayList<Integer>(graph.get(result[0]));
            int pos2 = (int) Math.round(rand.nextDouble() * (v2.size() - 1));

            result[1] = v2.get(pos2);
        }


        return result;
    }

    public int[] contraction(){
        int V = graph.keySet().size();

        for (int i = V; i >= 3; i--){

            int [] vv = vToMerge();
            int v1 = vv[0];
            int v2 = vv[1];
            int v3 = Math.min(v1, v2);

            for (int v : graph.keySet()){
                ArrayList<Integer> connections = graph.get(v);
                for (int j = 0; j < connections.size(); j++){
                    if (connections.get(j) == v1) connections.set(j, v3);
                    if (connections.get(j) == v2) connections.set(j, v3);
                }

            }

            if (v1 != v3) {
                for (int v : graph.get(v1)){
                    if (v != v3) graph.get(v3).add(v);
                }
                graph.remove(v1);
            }
            if (v2 != v3) {
                for (int v : graph.get(v2)){
                    if (v != v3) graph.get(v3).add(v);
                }
                graph.remove(v2);
            }

            ArrayList<Integer> filtered_connections = new ArrayList<Integer>();
            for (int v : graph.get(v3)){
                if (v != v3) filtered_connections.add(v);
            }
            graph.put(v3, filtered_connections);
        }

        int resultL = graph.keySet().size();
        int [] result = new int[resultL];

        int id = 0;
        for (int v : graph.keySet()){
            result[id] = graph.get(v).size();
            id ++;
        }

        return result;
    }
}
