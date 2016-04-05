package com.gmail.theandriicherniak.algorithms1;

import java.util.*;

/**
 * Created by andriicherniak on 4/5/16.
 */
public class MedianMaintenance {
    public static class minC implements Comparator<Integer>{
        public int compare(Integer v1, Integer v2){
            if (v1 < v2) return -1;
            else if (v1 > v2) return 1;
            else return 0;
        }
    }

    public static class maxC implements Comparator<Integer>{
        public int compare(Integer v1, Integer v2){
            if (v1 < v2) return 1;
            else if (v1 > v2) return -1;
            else return 0;
        }
    }
    PriorityQueue<Integer> leftQ;
    PriorityQueue<Integer> rightQ;

    public MedianMaintenance(int N){
        leftQ = new PriorityQueue<Integer>(N, new maxC());
        rightQ = new PriorityQueue<Integer>(N, new minC());
    }

    public int addValue(int v){
        leftQ.add(v);

        if (leftQ.size() > 0 && rightQ.size() > 0){
            int lVal = leftQ.peek();
            int rVal = rightQ.peek();

            while (leftQ.size() > 0 && lVal > rVal){
                rightQ.add(leftQ.remove());
                if (leftQ.size() > 0) lVal = leftQ.peek();
                rVal = rightQ.peek();
            }
        }

        while (leftQ.size() > rightQ.size()) rightQ.add(leftQ.remove());
        if (rightQ.size() > leftQ.size()) {
            leftQ.add(rightQ.remove());
        }

        return leftQ.peek();
    }


}
