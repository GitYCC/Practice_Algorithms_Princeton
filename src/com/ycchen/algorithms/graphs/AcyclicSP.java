package com.ycchen.algorithms.graphs;

import com.ycchen.algorithms.fundamentals.ArrayStack;

public class AcyclicSP {
	private double[] distTo;
	private DirectedEdge[] edgeTo;
	
	public AcyclicSP(EdgeWeightedDigraph G, int s) {
		distTo = new double[G.V()];
		for (int v=0; v<distTo.length; v++) {
			distTo[v] = Double.POSITIVE_INFINITY;
		}
		distTo[s] = 0.0;
		edgeTo = new DirectedEdge[G.V()];
		
		validateVertex(s);
		
		Topological  topo = new Topological(G);
		if (!topo.hasOrder()) {
			throw new IllegalArgumentException("Digraph is not acyclic.");
		} 
		for (int v: topo.order()) {
			for (DirectedEdge e: G.adj(v)) {
				relax(e);
			}
		}

	}
	
	private void relax(DirectedEdge e) {
		int v=e.from(), w=e.to();
		if (distTo[w]>distTo[v]+e.weight()) {
			distTo[w] = distTo[v]+e.weight();
			edgeTo[w] = e;
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
		int[] v_list = {5, 4, 5, 5, 4, 0, 3, 1, 7, 6, 3, 6, 6};
		int[] w_list = {4, 7, 7, 1, 0, 2, 7, 3, 2, 2, 6, 0, 4};
		double[] weight_list = {5,7,-2,2,8,-4,9,-1,4,10,22,28,63};
		for (int i=0; i<v_list.length; i++) {
			G.addEdge(new DirectedEdge(v_list[i],w_list[i],weight_list[i]));	
		}
		System.out.println(G);
		
		int source = 5;
		AcyclicSP sp = new AcyclicSP(G,source);
		for (int v=0;v<G.V();v++) {
			System.out.print(String.format("%d to %d (%.2f): ",source,v,sp.distTo(v)));
			for (DirectedEdge e: sp.pathTo(v)){
				System.out.print(e+"  ");
			}
			System.out.print("\n");
		}
	}

}
