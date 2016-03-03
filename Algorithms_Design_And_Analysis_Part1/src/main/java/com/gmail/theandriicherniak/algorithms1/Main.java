package com.gmail.theandriicherniak.algorithms1;

/**
 * Created by andriicherniak on 3/2/16.
 */
import java.io.*;
public class Main {
    public static void main(String [] args){
        String dataFile = "/Users/andriicherniak/Desktop/IntegerArray.txt";
        File file = new File(dataFile);
        BufferedReader reader = null;

        int [] data = new int[100000];

        try {
            reader = new BufferedReader(new FileReader(file));
            String line  = null;

            int id = 0;

            while ((line = reader.readLine()) != null) {
                data[id] = Integer.parseInt(line);
                id ++;
            }

        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }

//        int [] data = new int[]{1,2,3,5,4,6,7,8,11,9,10};

        HW1_CountInversions.countInversions(data);



    }
}
