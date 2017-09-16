package com.ycchen.algorithms.graphs;

import com.ycchen.algorithms.fundamentals.ArrayStack;

public class Cycle {
	private ArrayStack<Integer> cycle;
	private int[] edgeTo;
	private boolean[] marked;
	
	public Cycle(Graph G) {
		if (hasSelfLoop(G)) return;
		if (hasParallelEdges(G)) return;
		edgeTo = new int[G.V()];
		marked = new boolean[G.V()];
		for (int v=0; v<G.V();v++) {      // start from all vertex to find one cycle
			if (!marked[v]) dfs(G,-1,v);  // skip vertex that is already checked
		}
	}
	
	private boolean hasSelfLoop(Graph G) {
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
	
	private boolean hasParallelEdges(Graph G) {
		boolean[] marked_test = new boolean[G.V()];
		
		for (int v=0; v<G.V();v++) {
			for (int w: G.adj(v)){
				if (marked_test[w]) {
					cycle = new ArrayStack<Integer>();
					cycle.push(v);
					cycle.push(w);
					cycle.push(v);
					return true;
				}
				marked_test[w] = true;
			}
			for (int w:G.adj(v)){
				marked_test[w]=false;
			}
		}
		return false;
	}
	
	private void dfs(Graph G,int prev,int v) {
		marked[v] = true;
		for (int w: G.adj(v)){
			if (cycle!=null) return;
			
			if (marked[w]==false) {
				edgeTo[w] = v;
				dfs(G,v,w);
			} else if (w!=prev) {  
				// in "depth" first search, once we find an end is not backward,
				// this end is only the start point.
				cycle = new ArrayStack<Integer>();
				for (int i=v; i!=w; i=edgeTo[i]) {
					cycle.push(i);
				}
				cycle.push(w);
				cycle.push(v);
			}
		}
	}
	
	public Iterable<Integer> cycle() {
		return cycle;
	}
	
	public boolean hasCycle() {
		return cycle !=null;
	}
	
	public static void main(String[] args) {
		//      0 -- 6 
		//     /|\   | 
		//    | 1 2  4
		//     \    / \  
		//      \--5---3
		Graph G = new Graph(7);
		G.addEdge(0, 6); G.addEdge(0, 1); G.addEdge(0, 2); G.addEdge(0, 5);
		G.addEdge(6, 4); G.addEdge(5, 3); G.addEdge(5, 4); G.addEdge(4, 3);
		
		Cycle c = new Cycle(G);
		StringBuilder s = new StringBuilder();
		for (int w: c.cycle()){
			s.append(w + " -> ");
		}
		s.append("...");
		System.out.println(s.toString());

	}

}
