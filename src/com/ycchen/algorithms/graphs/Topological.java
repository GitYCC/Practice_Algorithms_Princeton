package com.ycchen.algorithms.graphs;

public class Topological {
	private Iterable<Integer> order;
	private int[] rank;
	
	public Topological(Digraph G) {
		DirectedCycle finder = new DirectedCycle(G);
		if (!finder.hasCycle()){
			DepthFirstOrder dfs = new DepthFirstOrder(G);
			order = dfs.reversePost();
			rank = new int[G.V()];
			int i = 0;
			for (int v: order)
				rank[v] = i++;			
		}
	}

	public Topological(EdgeWeightedDigraph G) {
		Digraph G2 = new Digraph(G.V());
		for (DirectedEdge e: G.edges()) {
			int v = e.from(), w = e.to();
			G2.addEdge(v, w);
		}
		DirectedCycle finder = new DirectedCycle(G2);
		if (!finder.hasCycle()){
			DepthFirstOrder dfs = new DepthFirstOrder(G2);
			order = dfs.reversePost();
			rank = new int[G.V()];
			int i = 0;
			for (int v: order)
				rank[v] = i++;			
		}		
	    
	}
	
	public Iterable<Integer> order() {
		return order;
	}
	
	public boolean hasOrder() {
		return order!=null;
	}
	
	public int rank(int v) {
		if (hasOrder()) return rank[v];
		else            return -1;
	}
	
	public static void main(String[] args) {
		Digraph G = new Digraph(13);
		G.addEdge(7, 6); G.addEdge(7, 9); G.addEdge(6, 4); G.addEdge(6, 0); G.addEdge(6, 9);
		G.addEdge(6, 8); G.addEdge(11, 4); G.addEdge(11, 12); G.addEdge(12, 9);
		G.addEdge(9, 10); G.addEdge(0, 5); G.addEdge(0, 1);
		G.addEdge(5, 4); G.addEdge(4, 3); G.addEdge(4, 2);  G.addEdge(3, 2);

		System.out.println(G);
		
		Topological topo = new Topological(G);
		
		System.out.println("hasOrder: "+topo.hasOrder());
		
		StringBuilder s = new StringBuilder();
		for (int v: topo.order()) {
			s.append(v + " -> ");
		}
		System.out.println(s.toString());
	}

}
