package com.gmail.theandriicherniak.algorithms1;

/**
 * Created by andriicherniak on 3/2/16.
 */

import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String dataFile = "/Users/andriicherniak/Desktop/Median.txt";
        File file = new File(dataFile);
        BufferedReader reader;

        HW6_MedianMaintenance mm = new HW6_MedianMaintenance(10000);
        ArrayList<Integer> median = new ArrayList<Integer>();

        try {
            reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                int v = Integer.parseInt(line);
                median.add(mm.addValue(v));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        int result = 0;
        for (int m : median){
            result = (result + m) % 10000;
        }

        System.out.println(result);



    }
}