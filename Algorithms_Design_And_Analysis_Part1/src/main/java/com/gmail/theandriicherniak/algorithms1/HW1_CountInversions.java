package com.gmail.theandriicherniak.algorithms1;

/**
 * Created by andriicherniak on 3/2/16.
 */
public class HW1_CountInversions {
    private static long mergeSortAndCount(int [] data, int [] buffer, int from, int to){

        if (to - from == 0) return 0L;

        if (to - from == 1){
            if (data[from] > data[from + 1]){
                int tmp = data[from];
                data[from] = data[from + 1];
                data[from + 1] = tmp;
                return 1L;
            }
            return 0L;
        }

        int midPoint = (from + to)/2;
        long inv1 = mergeSortAndCount(data, buffer, from, midPoint);
        long inv2 = mergeSortAndCount(data, buffer, midPoint + 1, to);
        long inv3 = 0L;

        int id1 = from;
        int id2 = midPoint + 1;
        int id = from;

        while (id <= to){
            while (id1 <= midPoint && data[id1] < data[id2]){
                buffer[id] = data[id1];
                id ++;
                id1++;
            }
            while (id2 <= to && data[id2] < data[id1]){
                inv3 += (midPoint + 1 - id1);
                buffer[id] = data[id2];
                id ++;
                id2++;
            }
            while (id2 > to && id1 <= midPoint){
                buffer[id] = data[id1];
                id ++;
                id1++;
            }
            while (id1 > midPoint && id2 <= to){
                buffer[id] = data[id2];
                id ++;
                id2++;
            }
        }

        for (int i = from; i <= to; i++){
            data[i] = buffer[i];
        }

        return inv1 + inv2 + inv3;
    }
    public static int countInversions(int [] data){
        int L = data.length;
        int [] buffer = new int[L];
        System.out.println(mergeSortAndCount(data, buffer, 0, L-1));


        return 0;
    }
}
