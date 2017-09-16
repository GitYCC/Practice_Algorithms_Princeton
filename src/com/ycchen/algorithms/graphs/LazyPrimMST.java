package com.ycchen.algorithms.graphs;

import com.ycchen.algorithms.fundamentals.ArrayQueue;
import com.ycchen.algorithms.sorting.HeapMinPQ;

public class LazyPrimMST {
	private double weight;
	private ArrayQueue<Edge> mst;
	private boolean[] marked;
	private HeapMinPQ<Edge> pq;
	
	public LazyPrimMST(EdgeWeightedGraph G) {
		mst = new ArrayQueue<Edge>();
		pq = new HeapMinPQ<Edge>();
		marked = new boolean[G.V()];
		for (int v=0; v<G.V(); v++) {
			if (!marked[v]) prim(G,v);
		}
	}
	
	private void prim(EdgeWeightedGraph G, int s) {
		scan(G,s);
		while (!pq.isEmpty()) {
			Edge e = pq.delMin();
			int v=e.eihter(), w=e.other(v);
			if (marked[v] && marked[w]) continue;
			mst.enqueue(e);
			weight += e.weight();
			if (!marked[v]) scan(G,v);
			if (!marked[w]) scan(G,w);
		}
	}
	
	private void scan(EdgeWeightedGraph G, int v) {
		marked[v] = true;
		for (Edge e: G.adj(v)) {
			if (!marked[e.other(v)]) pq.insert(e);
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
		
		LazyPrimMST mst = new LazyPrimMST(G);
		for (Edge e: mst.edges()) {
			System.out.println(e);
		}
		System.out.println(String.format("%.5f", mst.weight()));

	}

}
