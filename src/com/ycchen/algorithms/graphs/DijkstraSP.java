package com.ycchen.algorithms.graphs;

import com.ycchen.algorithms.fundamentals.ArrayStack;

public class DijkstraSP {
	private double[] distTo;
	private DirectedEdge[] edgeTo;
	private IndexMinPQ<Double> pq;
	
	public DijkstraSP(EdgeWeightedDigraph G, int s) {
		for (DirectedEdge e: G.edges()) {
			if (e.weight()<0) throw new IllegalArgumentException("edge "+e+" has negative weight");
		}
		
		distTo = new double[G.V()];
		for (int v=0; v<distTo.length; v++) {
			distTo[v] = Double.POSITIVE_INFINITY;
		}
		edgeTo = new DirectedEdge[G.V()];
		pq = new IndexMinPQ<Double>(G.V());
		
		validateVertex(s);
		
		distTo[s] = 0.0;
		pq.insert(s, distTo[s]);
		while (!pq.isEmpty()) {
			int v = pq.delMin();
			for (DirectedEdge e: G.adj(v))	relax(e);
		}
	}
	
	private void relax(DirectedEdge e) {
		int v=e.from(), w=e.to();
		if (distTo[w]>distTo[v]+e.weight()) {
			distTo[w] = distTo[v]+e.weight();
			edgeTo[w] = e;
			if (pq.contains(w)) pq.changeKey(w, distTo[w]);
			else                pq.insert(w, distTo[w]);
		}
	}
	
	private void validateVertex(int v) {
		int V = edgeTo.length;
		if (v<0 || v>=V)
			throw new IllegalArgumentException("vertex "+v+" is not between 0 and "+(V-1));
	}
	
	public double distTo(int v) {
		validateVertex(v);
		return distTo[v];
	}
	
	public boolean hasPathTo(int v) {
		validateVertex(v);
		return distTo[v]<Double.POSITIVE_INFINITY;
	}
	
	public Iterable<DirectedEdge> pathTo(int v) {
		validateVertex(v);
		if (!hasPathTo(v)) return null;
		ArrayStack<DirectedEdge> path = new ArrayStack<>();
		for (DirectedEdge e=edgeTo[v]; e!=null; e=edgeTo[e.from()]) {
			path.push(e);
		}
		return path;
	}
	
	public static void main(String[] args) {
		EdgeWeightedDigraph G = new EdgeWeightedDigraph(8);
		int[] v_list = {4, 5, 4, 5, 7, 5, 0, 0, 7, 1, 2, 6, 3, 6, 6};
		int[] w_list = {5, 4, 7, 7, 5, 1, 4, 2, 3, 3, 7, 2, 6, 0, 4};
		double[] weight_list = {0.35,0.35,0.37,0.28,0.28,0.32,0.38,0.26,0.39,0.29,0.34,0.4,0.52,0.58,0.93};
		for (int i=0; i<v_list.length; i++) {
			G.addEdge(new DirectedEdge(v_list[i],w_list[i],weight_list[i]));	
		}
		System.out.println(G);
		
		int source = 0;
		DijkstraSP sp = new DijkstraSP(G,source);
		for (int v=0;v<G.V();v++) {
			System.out.print(String.format("%d to %d (%.2f): ",source,v,sp.distTo(v)));
			for (DirectedEdge e: sp.pathTo(v)){
				System.out.print(e+"  ");
			}
			System.out.print("\n");
		}
	}

}
