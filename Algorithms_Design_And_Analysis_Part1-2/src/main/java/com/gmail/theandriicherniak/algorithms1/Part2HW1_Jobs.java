package com.gmail.theandriicherniak.algorithms1;

import java.io.*;
import java.util.*;

/**
 * Created by andriicherniak on 6/19/16.
 */
public class Part2HW1_Jobs {
    public static void main(String [] args){
        String data = "/Users/andriicherniak/Desktop/data/jobs.txt";
        File f = new File(data);

        TreeMap<Double, TreeMap<Integer, ArrayList<Integer>>> storage = new TreeMap<Double, TreeMap<Integer, ArrayList<Integer>>>(Collections.reverseOrder());

        try{

            BufferedReader br = new BufferedReader(new FileReader(f));
            int N = Integer.parseInt(br.readLine().trim());

            for (int i = 0; i < N; i++){
                String[] ar = br.readLine().trim().replaceAll("\\s+", " ").split(" ");
                int w = Integer.parseInt(ar[0]);
                int l = Integer.parseInt(ar[1]);
                double key = (1.0*w)/(1.0*l);
                if (!storage.containsKey(key)) storage.put(key, new TreeMap<Integer, ArrayList<Integer>>(Collections.reverseOrder()));
                if (!storage.get(key).containsKey(w)) storage.get(key).put(w, new ArrayList<Integer>());
                storage.get(key).get(w).add(l);
            }

            br.close();
        }catch (IOException e){
            System.out.println(e.getLocalizedMessage());
        }

        long t = 0;
        long cost = 0;

        for (double key : storage.keySet()){
            for (int w : storage.get(key).keySet()) {
                for (int l : storage.get(key).get(w)) {
                    t += l;
                    cost += t * w;
                }
            }
        }

        System.out.println("COST " + cost);
    }
}
