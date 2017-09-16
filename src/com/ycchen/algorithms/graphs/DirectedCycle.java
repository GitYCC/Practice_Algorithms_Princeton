package com.ycchen.algorithms.graphs;

import com.ycchen.algorithms.fundamentals.ArrayStack;

public class DirectedCycle {
	private ArrayStack<Integer> cycle;
	private int[] edgeTo;
	private boolean[] marked;
	private boolean[] onStack;
	
	public DirectedCycle(Digraph G) {
		edgeTo = new int[G.V()];
		marked = new boolean[G.V()];
		onStack = new boolean[G.V()];
		for (int v=0; v<G.V(); v++) {
			if (!marked[v]) {
				dfs(G,v);
			}
		}
		
	}
	
	private void dfs(Digraph G, int v) {
		// at no directed, different branches can make a circle
		// but at directed, different branches can not
		// so we need to make sure the circle is at same branch
		onStack[v] = true;
		marked[v] = true;
		for (int w: G.adj(v)) {
			if (cycle!=null) return;
 			
			if (!marked[w]) {
				edgeTo[w] = v;
				dfs(G,w);
			} else if (onStack[w]) {
				cycle  = new ArrayStack<Integer>();
				for (int x=v;x!=w;x=edgeTo[x]) {
					cycle.push(x);
				}
				cycle.push(w);
				cycle.push(v);
			}
		}
		onStack[v] = false;
	}
	
	private boolean hasSelfLoop(Digraph G) {
		for (int v=0; v<G.V();v++) {
			for (int w: G.adj(v)){
				if (w==v) {
					cycle = new ArrayStack<Integer>();
					cycle.push(v);
					cycle.push(v);
					return true;
				}
			}
		}
		return false;		
	}

	
	public Iterable<Integer> cycle() {
		return cycle;
	}
	
	public boolean hasCycle() {
		return cycle !=null;
	}
	
	public static void main(String[] args) {
		Digraph G = new Digraph(13);
		G.addEdge(7, 6); G.addEdge(7, 9); G.addEdge(6, 4); G.addEdge(6, 0); G.addEdge(6, 9);
		G.addEdge(6, 8); G.addEdge(8, 6); G.addEdge(11, 4); G.addEdge(11, 12); G.addEdge(12, 9);
		G.addEdge(9, 10); G.addEdge(9, 11); G.addEdge(10, 12); G.addEdge(0, 5); G.addEdge(0, 1);
		G.addEdge(5, 4); G.addEdge(4, 3); G.addEdge(4, 2); G.addEdge(3, 5); G.addEdge(3, 2);
		G.addEdge(2, 3); G.addEdge(2, 0); 
		
		DirectedCycle c = new DirectedCycle(G);
		System.out.println("hasCycle: "+c.hasCycle());
		
		StringBuilder s = new StringBuilder();
		for (int w: c.cycle()){
			s.append(w + " -> ");
		}
		s.append("...");
		System.out.println(s.toString());

	}

}
