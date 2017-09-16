package com.ycchen.algorithms.graphs;

import com.ycchen.algorithms.fundamentals.ArrayBag;

public class EdgeWeightedDigraph {
	private static final String NEWLINE = System.getProperty("line.separator");
	
	private final int V;
	private int E;
	private ArrayBag<DirectedEdge>[] adj;
	private int[] indegree;
	
	public EdgeWeightedDigraph(int V) {
		if ( V < 0 ) throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
		this.V = V;
		this.E = 0;
		this.indegree = new int[V];
		adj = (ArrayBag<DirectedEdge>[]) new ArrayBag[V];
		for (int v=0; v < V;v++) {
			adj[v] = new ArrayBag<DirectedEdge>();
		}
	}
	
	public EdgeWeightedDigraph(EdgeWeightedDigraph G) {
		this(G.V());
		this.E = G.E();
		for (int v=0; v<G.V(); v++) {
			this.indegree[v] = G.indegree(v);
		}
		for (int v=0; v<G.V(); v++) {
			for (DirectedEdge e: G.adj(v)) {
				adj[v].add(e);
			}
		}
	}
	
	public int V() { return V; }
	
	public int E() { return E; }
	
	public void addEdge(DirectedEdge e) {
		int v = e.from();
		int w = e.to();
		validateVertex(v);
		validateVertex(w);
		adj[v].add(e);
		indegree[w]++;
		E++;
	}
	
	public Iterable<DirectedEdge> adj(int v) {
		validateVertex(v);
		return adj[v];
	}
	
	public int outdegree(int v) {
		validateVertex(v);
		return adj[v].size();
	}
	
	public int indegree(int v) {
		validateVertex(v);
		return indegree[v];
	}
	
	public Iterable<DirectedEdge> edges() {
		ArrayBag<DirectedEdge> list = new ArrayBag<DirectedEdge>();
		for (int v=0; v<V; v++) {
			for (DirectedEdge e: adj(v)) {
				list.add(e);
			}
		}
		return list;
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(V+" vertices, "+E+" edges"+NEWLINE);
		for (int v=0; v<V; v++) {
			s.append(v+": ");
			for (DirectedEdge e: adj[v]) {
				s.append(e+"  ");
			}
			s.append(NEWLINE);
		}
		return s.toString();
	}
	
	private void validateVertex(int v) {
		if (v<0 || v>=V)
			throw new IllegalArgumentException("vertex "+v+" is not between 0 and "+(V-1));
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
	}
}
