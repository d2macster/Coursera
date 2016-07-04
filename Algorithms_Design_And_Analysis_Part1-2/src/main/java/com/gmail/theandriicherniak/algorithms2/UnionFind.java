package com.gmail.theandriicherniak.algorithms2;

/**
 * Created by andriicherniak on 7/3/16.
 */
class UnionFind{
    private int [][] UF;
    private int C;
    public UnionFind(int N){
        C = N;
        UF = new int[N][2];
        for (int i = 0; i < N; i++){
            UF[i][0] = i;
        }
    }
    public int find(int v){
        if (UF[v][0] == v) return v;
        else return find(UF[v][0]);
    }
    public void union(int v1, int v2){
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 != p2){
            C--;
            int l1 = UF[p1][1];
            int l2 = UF[p2][1];
            if (l1 > l2) UF[p2][0] = p1;
            else if (l1 < l2) UF[p1][0] = p2;
            else {
                UF[p2][0] = p1;
                UF[p1][1] ++;
            }
        }
    }
    public int Nclusters(){
        return C;
    }
}
