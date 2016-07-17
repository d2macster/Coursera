package com.gmail.theandriicherniak.algorithms2;
import java.util.*;

/**
 * Created by andriicherniak on 7/16/16.
 */
public class HW4_Dijkstra {
    private class BoundaryEdge{
        int destination;
        int distance;
        public BoundaryEdge(int v1, int v2){
            destination = v1;
            distance = v2;
        }
    }

    private class BoundaryEdgeComparator implements Comparator<BoundaryEdge> {
        public int compare(BoundaryEdge e1, BoundaryEdge e2){
            if (e1.distance > e2.distance) return 1;
            if (e1.distance < e2.distance) return -1;
            return 0;
        }
    }

    private HashMap<Integer, HashMap<Integer, Integer>> graph_weights;
    HashMap<Integer, Integer> nodeDistance = new HashMap<Integer, Integer>();


    public HW4_Dijkstra(HashMap<Integer, HashMap<Integer, Integer>> gw){
        graph_weights = gw;
    }

    public void run(int V){
        PriorityQueue<BoundaryEdge> q = new PriorityQueue<BoundaryEdge>(200, new BoundaryEdgeComparator());

        nodeDistance.put(V, 0);
        for (int dest : graph_weights.get(V).keySet()){
            q.add(new BoundaryEdge(dest, graph_weights.get(V).get(dest)));
        }

        while (!q.isEmpty()){
            BoundaryEdge e = q.remove();
            if (!nodeDistance.containsKey(e.destination)) {
                nodeDistance.put(e.destination, e.distance);

                if (graph_weights.containsKey(e.destination)) {
                    for (int dest : graph_weights.get(e.destination).keySet()) {
                        q.add(new BoundaryEdge(dest, e.distance + graph_weights.get(e.destination).get(dest)));
                    }
                }
            }

        }
    }
}
