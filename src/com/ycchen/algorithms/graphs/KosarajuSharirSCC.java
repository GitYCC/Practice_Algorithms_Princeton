package com.ycchen.algorithms.graphs;

public class KosarajuSharirSCC {
	private boolean[] marked;
	private int[] id;
	private int count;
	
	public KosarajuSharirSCC(Digraph G) {
		DepthFirstOrder dfs = new DepthFirstOrder(G.reverse());
		
		marked = new boolean[G.V()];
		id = new int[G.V()];
		for (int v: dfs.reversePost()) {
			if (!marked[v]) {
				dfs(G,v);
				count++;
			}
		}
	}
	
	private void dfs(Digraph G, int v) {
		marked[v] = true;
		id[v] = count;
		for (int w: G.adj(v)){
			if (!marked[w]) dfs(G,w);
		}
	}
	
	public int count() { return count; }
	
	public boolean stronglyConnected(int v,int w) {
		return id[v]==id[w];
	}
	
	public int id(int v) { return id[v]; }
	
	public static void main(String[] args) {
		Digraph G = new Digraph(13);
		G.addEdge(7, 6); G.addEdge(7, 9); G.addEdge(6, 4); G.addEdge(6, 0); G.addEdge(6, 9);
		G.addEdge(6, 8); G.addEdge(11, 4); G.addEdge(11, 12); G.addEdge(12, 9);
		G.addEdge(9, 10); G.addEdge(0, 5); G.addEdge(0, 1);
		G.addEdge(5, 4); G.addEdge(4, 3); G.addEdge(4, 2);  G.addEdge(3, 2);
		

		System.out.println(G);
		
		KosarajuSharirSCC scc = new KosarajuSharirSCC(G);
		for (int v=0; v<G.V(); v++) {
			System.out.print(v+": G "+scc.id(v)+", ");
		}
		System.out.print("\n\n");
		
		
		G.addEdge(2, 3); G.addEdge(3, 5); G.addEdge(2, 0);  G.addEdge(10, 12);		
		G.addEdge(9, 11); G.addEdge(8, 6);
		
		System.out.println(G);
		
		KosarajuSharirSCC scc2 = new KosarajuSharirSCC(G);
		for (int v=0; v<G.V(); v++) {
			System.out.print(v+": G"+scc2.id(v)+", ");
		}
		System.out.print("\n");
	}

}
