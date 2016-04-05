package com.gmail.theandriicherniak.algorithms1;

import java.util.*;

/**
 * Created by andriicherniak on 4/4/16.
 */
public class TwoSum {
    int counter = 0;
    int dataL;
    long[] data;

    public TwoSum(long[] dataInput) {
        data = dataInput;
        Arrays.sort(data);
        dataL = data.length;
    }

    public int computeN() {
        for (int t = -10000; t <= 10000; t++) {
            if (canFindSum1(t)) counter++;
        }

        return counter;
    }

    private boolean canFindSum1(int target) {
        int start = 0;
        int end = dataL - 1;
        while (start < end) {
            if (data[start] + data[end] == target) return true;
            else if (data[start] + data[end] < target) start++;
            else end--;
        }
        return false;
    }

}
