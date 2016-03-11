package com.gmail.theandriicherniak.algorithms1;

import java.util.*;

/**
 * Created by andriicherniak on 3/10/16.
 */
public class MinPriorityQueue<T> {
    private ArrayList<T> storage;
    private int maxL;
    private int curL;
    private Comparator comparator;

    public MinPriorityQueue(int L, Comparator c){
        comparator = c;
        maxL = L;
        curL = 0;
        storage = new ArrayList<T>();
        for (int i = 1; i <= L; i++) storage.add(null);
    }

    private int elementCompare(int elementId1, int elementId2){
        T e1 = storage.get(elementId1 - 1);
        T e2 = storage.get(elementId2 - 1);
        return comparator.compare(e1, e2);
    }

    private void bubbleUP(int elementId){
        if (elementId == 1) return;
        int parentId = elementId / 2;

        if (elementCompare(parentId, elementId) > 0){
            swap(parentId, elementId);

            bubbleUP(parentId);
        }
    }

    private void swap(int elementId1, int elementId2){
        T tmp = storage.get(elementId1 - 1);
        storage.set(elementId1 - 1, storage.get(elementId2 - 1));
        storage.set(elementId2 - 1, tmp);
    }

    private void bubbleDown(int elementId){
        int child1 = 2 * elementId;
        int child2 = 2 * elementId + 1;

        if (child1 <= curL && child2 > curL){
            if (elementCompare(child1, elementId) < 0) {
                swap(child1, elementId);
                bubbleDown(child1);
            }
        }

        if (child2 <= curL) {

            if (elementCompare(child1, elementId) < 0 && elementCompare(child1, child2) <= 0) {
                swap(child1, elementId);
                bubbleDown(child1);
            }

            if (elementCompare(child2, elementId) < 0 && elementCompare(child2, child1) <= 0) {
                swap(child2, elementId);
                bubbleDown(child2);
            }
        }
    }

    public void add(T v){
        if (curL < maxL){
            storage.set(curL, v);
        }else {
            storage.add(v);
            maxL ++;
        }
        curL ++;

        if (curL > 1) bubbleUP(curL);
    }

    public boolean isEmpty(){
        return (curL <= 0);
    }

    public T remove(){
        if (curL >= 1){
            T result = storage.get(0);
            if (curL > 1) {
                storage.set(0, storage.get(curL - 1));
                storage.set(curL - 1, null);
            }
            curL --;
            bubbleDown(1);

            return result;
        }else {
            return null;
        }
    }

}
