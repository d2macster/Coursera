package com.gmail.theandriicherniak.algorithms1;

import java.util.Arrays;

/**
 * Created by andriicherniak on 3/4/16.
 */
public class HW2_QuickSort {
    private static int comparisonsCount;

    private static int pickPivotMedian3(int [] data, int from, int to){
        int [] median = new int[3];

        median[0] = data[from];
        median[1] = data[(from + to)/2];
        median[2] = data[to];

        Arrays.sort(median);
        int median_value = median[1];

        if (data[from] == median_value) return from;
        if (data[to] == median_value) return to;
        if (data[(from + to)/2] == median_value) return (from + to)/2;

        return (from + to)/2;
    }
    private static int pickPivotFirst(int [] data, int from, int to){
        return from;
    }
    private static int pickPivotLast(int [] data, int from, int to){
        return to;
    }
    private static void swapElements(int [] data, int index1, int index2){
        int tmp = data[index1];
        data[index1] = data[index2];
        data[index2] = tmp;
    }
    private static void sortHelper(int[] data, int L, int from, int to){
        if (from < 0) return;
        if (to >= L) return;

        comparisonsCount += Math.max(0, (to - from));

        if (to - from <= 0) return;
        if (to - from == 1){
            if (data[from] > data[from + 1]) swapElements(data, from, from + 1);
        }

        int pivotIndex = pickPivotLast(data, from, to);
        swapElements(data, from, pivotIndex);
        int le_index = from;

        for (int j = from + 1; j <= to; j ++){
            if (data[j] <= data[from]) swapElements(data, ++le_index, j);
        }

        swapElements(data, from, le_index);

        sortHelper(data, L, from, le_index - 1);
        sortHelper(data, L, le_index + 1, to);
    }
    public static void quickSort(int [] data){
        comparisonsCount = 0;

        int L = data.length;
        sortHelper(data, L, 0, L-1);

        System.out.println(comparisonsCount);
    }
}
