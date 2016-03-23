package com.thenadriicherniak.yourmechanic;

import java.util.*;

/**
 * Created by andriicherniak on 3/21/16.
 */
public class DisjointIntervals {

    private class Node{
        Node left;
        Node right;
        int from;
        int to;

        public Node(int fromV, int toV){
            from = fromV;
            to = toV;
        }
    }

    Node root = null;

    private Node max(Node x){
        if (x.right == null) return x;
        else return max(x.right);
    }

    private Node min(Node x){
        if (x.left == null) return x;
        return min(x.left);
    }

    private Node deleteMin(Node x){
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        return x;
    }

    private Node floor(int v, Node x){
        if (x == null) return null;
        if (x.from == v) return x;
        if (v < x.from) return floor(v, x.left);
        Node t = floor(v, x.right);
        if (t != null) return t;
        else return x;
    }

    private Node add(Node x, Node n){
        if (x == null) return n;
        else {
            if (n.from < x.from) x.left = add(x.left, n);
            else if (n.from > x.from) x.right = add(x.right, n);
        }
        return x;
    }

    public void add(int from, int to){
        ArrayList<Node> ss = new ArrayList<Node>();
        search(root, from, to, ss);
        for (Node x : ss){
            to = Math.max(to, x.to);
        }
        for (Node x : ss){
            root = remove(root, x.from);
        }
        Node f = floor(from, root);
        if (f == null || f.to < from) {
            Node n = new Node(from, to);
            root = add(root, n);
        }else {
            f.to = Math.max(f.to, to);
        }
    }

    private void search(Node x, int from, int to, ArrayList<Node> buffer){
        if (x == null) return;
        if (x.from >= from && x.from <= to) buffer.add(x);

        if (from <= x.from) search(x.left, from, to, buffer);
        if (to >= x.from) search(x.right, from, to, buffer);
    }

    private Node remove(Node x, int from){
        if (x == null) return null;
        if (from < x.from) x.left = remove(x.left, from);
        else if (from > x.from) x.right = remove(x.right, from);
        else {
            if (x.right == null ) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        return x;
    }

    public void remove(int from, int to){
        ArrayList<Node> ss = new ArrayList<Node>();
        search(root, from, to, ss);
        int new_to = from;

        Node f = floor(from, root);
        if (f != null && f.to >= from){
            new_to = Math.max(new_to, f.to);
            f.to = from;
        }

        for (Node x : ss){
            new_to = Math.max(new_to, x.to);
            root = remove(root, x.from);
        }
        if (new_to > to){
            Node xx = new Node(to, new_to);
            root = add(root, xx);
        }
    }

    private void show(Node x){
        if (x != null){
            show(x.left);
            System.out.print("[" + x.from + "-" + x.to +"]");
            show(x.right);
        }

    }
    public void show(){
        show(root);
        System.out.println();
    }
}
