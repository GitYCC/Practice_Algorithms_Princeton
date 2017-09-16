package com.ycchen.algorithms.graphs;

import com.ycchen.algorithms.fundamentals.ArrayQueue;

public class PrimMST {
	private Edge[] edgeTo;
	private double[] distTo; // distance to tree on vertices that are not in tree
	private boolean[] marked;
	private IndexMinPQ<Double> pq;
	
	public PrimMST(EdgeWeightedGraph G) {
		edgeTo = new Edge[G.V()];
		distTo = new double[G.V()];
		for (int v=0;v<G.V();v++) {
			distTo[v]=Double.POSITIVE_INFINITY;
		}
		marked = new boolean[G.V()];
		pq = new IndexMinPQ<Double>(G.V());
		
		for (int v=0;v<G.V();v++) {
			if (!marked[v]) prim(G,v);
		}
	}
	
	private void prim(EdgeWeightedGraph G, int s) {
		distTo[s] = 0.0;
		pq.insert(s, distTo[s]);
		while (!pq.isEmpty()) {
			int v = pq.delMin();
			scan(G,v);
		}
	}
	
	private void scan(EdgeWeightedGraph G, int v){
		marked[v] = true;
		for (Edge e: G.adj(v)) {
			int w = e.other(v);
			if (marked[w]) continue;
			if (e.weight()<distTo[w]) {
				distTo[w] = e.weight();
				edgeTo[w] = e;
				if (pq.contains(w)) pq.changeKey(w, distTo[w]);
				else                pq.insert(w, distTo[w]);
			}
		}
	}
	
	public Iterable<Edge> edges() {
		ArrayQueue<Edge> mst= new ArrayQueue<Edge>();
		for (int v=0; v<edgeTo.length;v++) {
			Edge e = edgeTo[v];
			if (e != null) mst.enqueue(e);
		}
		return mst;
	}
	
	public double weight() {
		double weight = 0.0;
		for (Edge e: edges()) {
			weight += e.weight();
		}
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
