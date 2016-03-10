package com.gmail.theandriicherniak.algorithms1;

import java.util.*;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by andriicherniak on 3/10/16.
 */
public class HW3_SCC {
    private HashMap<Integer, ArrayList<Integer>> graph_original;
    private HashMap<Integer, ArrayList<Integer>> graph_rev;

    private void DFS_order(HashMap<Integer, ArrayList<Integer>> graph, int curV, boolean [] visited, Stack<Integer> order){
        if (!visited[curV]){
            visited[curV] = true;

            if (graph.containsKey(curV)){
                for (int v : graph.get(curV)){
                    if (!visited[v]) DFS_order(graph, v, visited, order);
                }
            }
            order.push(curV);
        }

    }

    public HW3_SCC(HashMap<Integer, ArrayList<Integer>> g1, HashMap<Integer, ArrayList<Integer>> g2){
        graph_original = g1;
        graph_rev = g2;
    }

    public void computeSCC(){
        int maxV = 1;
        for (int v : graph_original.keySet()) {
            maxV = Math.max(maxV, v);
            for (int vv : graph_original.get(v)) maxV = Math.max(maxV, vv);
        }
        System.out.println("Got maxV " + maxV);

        boolean [] visited = new boolean[maxV + 1];
        Stack<Integer> order = new Stack<Integer>();
        for (int v = 1; v <= maxV; v ++ ) {
            if (!visited[v]) DFS_order(graph_original, v, visited, order);
        }

        System.out.println(order);

        System.out.println("Completed DFS reversed graph");

        for (int i = 0; i <= maxV; i++) visited[i] = false;

        ArrayList<Integer> cc = new ArrayList<Integer>();

        while (!order.empty()){
            int v = order.pop();
            Stack<Integer> ccElements = new Stack<Integer>();
            DFS_order(graph_rev, v, visited, ccElements);
            if (ccElements.size() > 0) {
                cc.add(ccElements.size());
            }
        }

        Collections.sort(cc);
//        System.out.println(cc);

        int ccL = cc.size();
        for (int i = ccL - 1; i >= ccL - 5; i--) {
            System.out.println(cc.get(i));
        }
    }
}