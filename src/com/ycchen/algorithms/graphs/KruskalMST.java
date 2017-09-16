package com.ycchen.algorithms.graphs;

import com.ycchen.algorithms.fundamentals.ArrayQueue;
import com.ycchen.algorithms.fundamentals.WeightedQuickUnionUF;
import com.ycchen.algorithms.sorting.HeapMinPQ;

public class KruskalMST {
	private double weight;
	private ArrayQueue<Edge> mst = new ArrayQueue<Edge>();
	
	public KruskalMST(EdgeWeightedGraph G) {
		HeapMinPQ<Edge> pq = new HeapMinPQ<Edge>();
		for (Edge e: G.edges()) pq.insert(e);
		
		WeightedQuickUnionUF uf = new WeightedQuickUnionUF(G.V());
		while (!pq.isEmpty() && mst.size()<G.V()-1) {
			Edge e = pq.delMin();
			int v = e.eihter();
			int w = e.other(v);
			if (!uf.connected(v, w)) {
				uf.union(v, w);
				mst.enqueue(e);
				weight += e.weight();
			}
		}
	}
	
	public Iterable<Edge> edges() {
		return mst;
	}
	
	public double weight() {
		return weight;
	}
	
	public static void main(String[] args) {
		EdgeWeightedGraph G = new EdgeWeightedGraph(8);
		int[] v_list = {0, 2, 1, 0, 5, 1, 1, 2, 4, 1, 4, 0, 6, 3, 6, 6};
		int[] w_list = {7, 3, 7, 2, 7, 3, 5, 7, 5, 2, 7, 4, 2, 6, 0, 4};
		double[] weight_list = {0.16,0.17,0.19,0.26,0.28,0.29,0.32,0.34,0.35,0.36,0.37,0.38,0.4,0.52,0.58,0.93};
		for (int i=0; i<v_list.length; i++) {
			G.addEdge(new Edge(v_list[i],w_list[i],weight_list[i]));	
		}
		System.out.println(G);
		
		KruskalMST mst = new KruskalMST(G);
		for (Edge e: mst.edges()) {
			System.out.println(e);
		}
		System.out.println(String.format("%.5f", mst.weight()));
	}

}
