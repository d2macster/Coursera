package com.gmail.theandriicherniak.algorithms1;

/**
 * Created by andriicherniak on 3/2/16.
 */

import java.io.*;

public class Main {
    public static void main(String[] args) {
        String dataFile = "/Users/andriicherniak/Desktop/Median.txt";
        File file = new File(dataFile);
        BufferedReader reader;

        long [] data = new long[1000000];

        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            int id = 0;

            while ((line = reader.readLine()) != null) {
                data[id] = Long.parseLong(line);
                id ++;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }



    }
}