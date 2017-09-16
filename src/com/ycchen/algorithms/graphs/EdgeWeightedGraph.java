package com.ycchen.algorithms.graphs;

import com.ycchen.algorithms.fundamentals.ArrayBag;

@SuppressWarnings("unchecked")
public class EdgeWeightedGraph {
	private static final String NEWLINE = System.getProperty("line.separator");
	
	private final int V;
	private int E;
	private ArrayBag<Edge>[] adj;
	
	public EdgeWeightedGraph(int V) {
		if (V<0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
		this.V = V;
		this.E = 0;
		this.adj = (ArrayBag<Edge>[]) new ArrayBag[this.V];
		for (int i=0; i<this.V; i++) {
			this.adj[i] = new ArrayBag<Edge>();
		}
	}
	
	public int V() { return V; }
	
	public int E() { return E; }
	
	public void addEdge(Edge edge) {
		int v = edge.eihter();
		int w = edge.other(v);
		validateVertex(v);
		validateVertex(w);
		adj[v].add(edge);
		adj[w].add(edge);
		E++;
	}
	
	private void validateVertex(int v) {
		if (v<0 || v>=V)
			throw new IllegalArgumentException("vertex "+v+" is not between 0 and "+(V-1));
	}
	
	public Iterable<Edge> adj(int v) {
		validateVertex(v);
		return adj[v];
	}
	
	public Iterable<Edge> edges() {
		ArrayBag<Edge> list = new ArrayBag<Edge>();
		for (int v=0; v<V; v++) {
			int selfLoops = 0;
			for (Edge e: adj(v)) {
				if (e.other(v)>v) {
					list.add(e);
				} else if (e.other(v)==v) {
					// only add one copy of each self loop
					if (selfLoops %2 == 0) list.add(e);
					selfLoops++;
					
				}
			}
		}
		return list;
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(V+" vertices, "+E+" edges"+NEWLINE);
		for (int v=0; v<V; v++) {
			s.append(v+": ");
			for (Edge e: adj[v]) {
				s.append(e+", ");
			}
			s.append(NEWLINE);
		}
		return s.toString();
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
	}
	
}
