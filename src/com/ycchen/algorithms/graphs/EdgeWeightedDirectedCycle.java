package com.ycchen.algorithms.graphs;

import com.ycchen.algorithms.fundamentals.ArrayStack;

public class EdgeWeightedDirectedCycle {
	private ArrayStack<DirectedEdge> cycle;
	private DirectedEdge[] edgeTo;
	private boolean[] marked;
	private boolean[] onStack;
	
	public EdgeWeightedDirectedCycle(EdgeWeightedDigraph G) {
		edgeTo = new DirectedEdge[G.V()];
		marked = new boolean[G.V()];
		onStack = new boolean[G.V()];
		for (int v=0; v<G.V(); v++) {
			if (!marked[v]) {
				dfs(G,v);
			}
		}
		
	}
	
	private void dfs(EdgeWeightedDigraph G, int v) {
		// at no directed, different branches can make a circle
		// but at directed, different branches can not
		// so we need to make sure the circle is at same branch
		onStack[v] = true;
		marked[v] = true;
		for (DirectedEdge e: G.adj(v)) {
			int w = e.to();
			if (cycle!=null) return;
 			
			if (!marked[w]) {
				edgeTo[w] = e;
				dfs(G,w);
			} else if (onStack[w]) {
				cycle  = new ArrayStack<DirectedEdge>();
				for (DirectedEdge x=e; x.from()!=w; x=edgeTo[x.from()]) {
					cycle.push(x);
				}
				cycle.push(e);
				return;
			}
		}
		onStack[v] = false;
	}
	
	
	public Iterable<DirectedEdge> cycle() {
		return cycle;
	}
	
	public boolean hasCycle() {
		return cycle !=null;
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
		
		EdgeWeightedDirectedCycle c = new EdgeWeightedDirectedCycle(G);
		System.out.println("hasCycle: "+c.hasCycle());
		
		StringBuilder s = new StringBuilder();
		for (DirectedEdge e: c.cycle()){
			s.append(e+" ");
		}
		System.out.println(s.toString());

	}

}
