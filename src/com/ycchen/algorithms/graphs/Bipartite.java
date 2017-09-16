package com.ycchen.algorithms.graphs;

import com.ycchen.algorithms.fundamentals.ArrayStack;

public class Bipartite {
	private ArrayStack<Integer> cycle;
	private int[] edgeTo;
	private boolean[] marked;
	
	private boolean[] color;
	private boolean isBipartite;
	
	public Bipartite(Graph G) {
		edgeTo = new int[G.V()];
		marked = new boolean[G.V()];
		color = new boolean[G.V()];
		isBipartite = true;
		for (int v=0; v<G.V();v++) {      // start from all vertex to find one cycle
			if (!marked[v]) dfs(G,v);  // skip vertex that is already checked
		}
	}
	
	private void dfs(Graph G,int v) {
		marked[v] = true;
		for (int w: G.adj(v)){
			if (cycle!=null) return;
			
			if (marked[w]==false) {
				edgeTo[w] = v;
				color[w] = !color[v];
				dfs(G,w);
				
			} else if (color[w]==color[v]) {  
				// in "depth" first search, once we find an end is not backward,
				// this end is only the start point.
				// if the color of the end is same as previous one,
				// this is a odd-length loop
				isBipartite = false;
				cycle = new ArrayStack<Integer>();
				for (int i=v; i!=w; i=edgeTo[i]) {
					cycle.push(i);
				}
				cycle.push(w);
				cycle.push(v);
			}
		}
	}
	
	public boolean isBipartite() {
		return isBipartite;
	}
	
	public Iterable<Integer> oddCycle() {
		return cycle;
	}
	
	public boolean color(int v) {
		return color[v];
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
		
		Bipartite bi = new Bipartite(G);
		System.out.println("isBipartite: "+bi.isBipartite());
		
		//      0 -- 5 
		//     /|\   | 
		//    | 1 2  3
		//     \    /   
		//      \--4
		G = new Graph(6);
		G.addEdge(0, 5); G.addEdge(0, 1); G.addEdge(0, 2); G.addEdge(0, 4);
		G.addEdge(5, 3); G.addEdge(4, 3);
		
		bi = new Bipartite(G);
		System.out.println("isBipartite: "+bi.isBipartite());
		
		StringBuilder s = new StringBuilder();
		for (int v=0;v<G.V();v++){
			s.append(v + ": " + bi.color(v)+"\n");
		}
		System.out.println(s.toString());

	}

}
