package com.gmail.theandriicherniak.algorithms1;

/**
 * Created by andriicherniak on 3/11/16.
 */

import java.util.*;

public class BalancedBST<T extends Comparable<T>> {
    private class Node {
        Node left = null;
        Node right = null;
        T value;
        int size;
        int height;

        public Node(T v) {
            value = v;
            size = 1;
            height = 1;
        }
    }

    Node root = null;

    private int height(Node x){
        if (x == null) return 0;
        else return x.height;
    }

    public int height(){
        return height(root);
    }

    private boolean is_balanced(Node x){
        if (x == null) return true;
        int hl = height(x.left);
        int hr = height(x.right);
        if ((hl > hr + 1) || (hr > hl + 1)) return false;
        if (x.height != Math.max(hl, hr) + 1) return false;
        return true;
    }

    public boolean is_balanced(){
        return is_balanced(root);
    }

    private Node searchHelper(Node x, T v) {
        if (x == null) return null;
        int cmp = v.compareTo(x.value);

        if (cmp == 0) return x;
        if (cmp < 0) {
            if (x.left != null) return searchHelper(x.left, v);
            else return null;
        } else {
            if (x.right != null) return searchHelper(x.right, v);
            else return null;
        }
    }

    public boolean contains(T v) {
        Node n = searchHelper(root, v);
        if (n != null) return true;
        return false;
    }

    private Node insert(T v, Node x) {
        Node n = new Node(v);
        if (x == null) return n;
        else {
            int cmp = v.compareTo(x.value);
            if (cmp < 0) x.left = insert(v, x.left);
            else if (cmp > 0) x.right = insert(v, x.right);
            x.size = size(x.left) + size(x.right) + 1;
            x.height = Math.max(height(x.left), height(x.right)) + 1;

            if (height(x.left) > height(x.right) + 1) x = RR(x);
            if (height(x.left) + 1 < height(x.right)) x = LR(x);
            return x;
        }
    }

    public void insert(T v) {
        root = insert(v, root);
    }

    public int size(Node x) {
        if (x == null) return 0;
        else return root.size;
    }

    public void show() {
        List<Node> buffer = new ArrayList<Node>();
        buffer.add(root);
        show(buffer);
    }

    private void show(List<Node> xList) {
        List<Node> buffer = new ArrayList<Node>();
        for (Node x : xList) {
            if (x != null) {
                System.out.print("(" + x.value + " : " + x.height + ") ");

                buffer.add(x.left);
                buffer.add(x.right);
            }
        }
        System.out.println();
        if (buffer.size() > 0) show(buffer);
    }

    private Node LR(Node x){
        Node y = x.right;
        Node B = y.left;
        y.left = x;
        x.right = B;

        x.size = size(x.left) + size(x.right) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        y.size = size(y.left) + size(y.right) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    private Node RR(Node y){
        Node x = y.left;
        Node B = x.right;
        y.left = B;
        x.right = y;

        y.size = size(y.left) + size(y.right) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        x.size = size(x.left) + size(x.right) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    public T min(){
        return min(root).value;
    }
    private Node min(Node x){
        if (x.left == null) return x;
        else return min(x.left);
    }

    public T max(){
        return max(root).value;
    }
    private Node max(Node x){
        if (x.right == null) return x;
        else return max(x.right);
    }

    public T succ(T v){
        return succ(v, root, new Stack<Node>());
    }

    private T succ(T v, Node x, Stack<Node> parents){
        parents.push(x);
        int cmp = v.compareTo(x.value);

        if (cmp == 0){
            if (x.right != null) return min(x.right).value;
            else{
                while (!parents.empty()){
                    Node xx = parents.pop();
                    if (v.compareTo(xx.value) < 0) return xx.value;
                }
            }
        }

        if (cmp < 0) return succ(v, x.left, parents);
        else return succ(v, x.right, parents);
    }

    public T pred(T v){
        return pred(v, root, new Stack<Node>());
    }

    private T pred(T v, Node x, Stack<Node> parents){
        parents.push(x);

        int cmp = v.compareTo(x.value);
        if (cmp == 0) {
            if (x.left != null) return max(x.left).value;
            else {
                while (!parents.empty()){
                    Node xx = parents.pop();
                    if (v.compareTo(xx.value) > 0) return xx.value;
                }
            }
        }

        if (cmp < 0) return pred(v, x.left, parents);
        else return pred(v, x.right, parents);

    }
}
