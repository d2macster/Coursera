package com.gmail.theandriicherniak.algorithms1;

import java.io.*;
import java.util.*;

/**
 * Created by andriicherniak on 6/30/16.
 */
public class SuggestFreinds {
    private static class link {
        private String friend;
        private int W;

        public link(String f, int w) {
            friend = f;
            W = w;
        }
    }

    private static class linkComparator implements Comparator<link> {
        public int compare(link l1, link l2) {
            if (l1.W < l2.W) return -1;
            else if (l1.W > l2.W) return 1;
            else return 0;
        }
    }

    public static void main(String[] args) {
        HashMap<String, HashMap<String, Integer>> friendship = new HashMap<String, HashMap<String, Integer>>();
        String data = "/Users/andriicherniak/Desktop/friendship.txt";

        // format : user1,user2,{f,bf} where f - friend, bf - best friend

        try {
            FileReader reader = new FileReader(new File(data));
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                String[] ar = line.trim().split(",");
                int w = (ar[2].equals("f")) ? 3 : 1;
                if (!friendship.containsKey(ar[0])) friendship.put(ar[0], new HashMap<String, Integer>());
                if (!friendship.containsKey(ar[1])) friendship.put(ar[1], new HashMap<String, Integer>());

                friendship.get(ar[0]).put(ar[1], w);
                friendship.get(ar[1]).put(ar[0], w);
            }
            br.close();
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }

        System.out.println(suggestFriends("andrii", friendship));
    }

    public static List<String> suggestFriends(String user, HashMap<String, HashMap<String, Integer>> friendship) {
        ArrayList<String> names = new ArrayList<String>();
        PriorityQueue<link> q = new PriorityQueue<link>(10, new linkComparator());

        HashSet<String> visited = new HashSet<String>();

        HashSet<String> alreadyFriends = new HashSet<String>();
        alreadyFriends.add(user);

        for (String f : friendship.get(user).keySet()) {
            q.add(new link(f, friendship.get(user).get(f)));
            alreadyFriends.add(f);
        }


        while (!q.isEmpty()) {
            link l = q.remove();
            String friend = l.friend;
            int w = l.W;
            if (!visited.contains(friend)) {

                visited.add(friend);
                if (!alreadyFriends.contains(friend)) names.add(friend);

                for (String ff : friendship.get(friend).keySet()) {
                    if (!visited.contains(ff))
                    q.add(new link(ff, w + friendship.get(friend).get(ff)));
                }
            }
        }

        return names;
    }
}