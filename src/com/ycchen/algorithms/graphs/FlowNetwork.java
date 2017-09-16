package com.ycchen.algorithms.graphs;

import com.ycchen.algorithms.fundamentals.ArrayBag;

public class FlowNetwork {
	private static final String NEWLINE = System.getProperty("line.separator");
	
	private final int V;
	private int E;
	private ArrayBag<FlowEdge>[] adj;
	
	@SuppressWarnings("unchecked")
	public FlowNetwork(int V) {
		this.V = V;
		adj = (ArrayBag<FlowEdge>[]) new ArrayBag[V];
		for (int i=0; i<V; i++) {
			adj[i] = new ArrayBag<FlowEdge>();
		}
	}
	
	public int V() {
		return V;
	}
	
	public int E() {
		return E;
	}
	
	private void vaildateVertex(int v) {
		if (v < 0 || v >= V) 
			throw new IllegalArgumentException("vertex "+v+" is not between 0 and "+(V-1));
		
	}
	
	public void addEdge(FlowEdge e) {
		int v=e.from(), w=e.to();
		vaildateVertex(v);
		vaildateVertex(w);
		adj[v].add(e);
		adj[w].add(e);
		E++;
	}
	
	public Iterable<FlowEdge> adj (int v) {
		vaildateVertex(v);
		return adj[v];
	}
	
	public Iterable<FlowEdge> edges() {
		ArrayBag<FlowEdge> list = new ArrayBag<FlowEdge>();
		for (int v = 0; v < V; v++) {
			for (FlowEdge e: adj[v]) {
				if (e.to()!=v) list.add(e);
			}
		}
		return list;
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(V+" "+E+NEWLINE);
		for (int v=0; v < V; v++) {
			s.append(v+": ");
			for (FlowEdge e: adj[v]) {
				if (e.to()!=v) s.append(e+" ");
			}
			s.append(NEWLINE);
		}
		return s.toString();
	}
	
	public static void main (String[] args) {
		FlowNetwork G = new FlowNetwork(8);
		int[] vs = {0,0,0,1,1,1,2,2,3,4,4,5,5,6,6};
		int[] ws = {1,2,3,2,4,5,3,5,6,5,7,6,7,2,7};
		double[] caps = {0.10,0.05,0.15,0.04,0.09,0.15,0.04,0.08,0.16,0.15,0.10,0.15,0.10,0.06,0.10};
		for (int i=0; i<vs.length; i++) {
			int v = vs[i];
			int w = ws[i];
			double cap = caps[i];
			G.addEdge(new FlowEdge(v,w,cap));
		}
		System.out.print(G.toString());
	}
}
