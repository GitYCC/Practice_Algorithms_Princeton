package com.ycchen.algorithms.graphs;

import com.ycchen.algorithms.fundamentals.ArrayBag;


@SuppressWarnings("unchecked")
public class Digraph {
	private static final String NEWLINE = System.getProperty("line.separator");
	
	private final int v;
	private int e;
	private ArrayBag<Integer>[] adj_arry;
	private int[] indegree;
	
	public Digraph(int vertex) {
		if (vertex<0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
		
		v = vertex;
		e = 0;
		indegree = new int[v];
		adj_arry = (ArrayBag<Integer>[]) new ArrayBag[v];
		for (int i = 0; i < v; i++) {
			adj_arry[i] = new ArrayBag<Integer>();
		}
	}
	
	public Digraph(Digraph G) {
		this(G.V());
		e = G.E();
		for (int i=0; i<G.V(); i++) {
			this.indegree[i] = G.indegree(i);
		}
		for (int i = 0; i < G.V(); i++) {
			for (int v: G.adj(i)) {
				adj_arry[i].add(v);
			}
		}		
	}
	
	public Digraph reverse() {
		Digraph reverse = new Digraph(this.V());
		for (int i=0; i<this.V(); i++) {
			for (int j: this.adj(i)) {
				reverse.addEdge(j, i);
			}
		}
		return reverse;
	}
	
	public int V(){
		return v;
	}
	
	public int E(){
		return e;
	}
	
	public Iterable<Integer> adj(int v){
		validateVertex(v);
		return adj_arry[v];
	}
	
	public int outdegree(int v) {
		validateVertex(v);
		return adj_arry[v].size();
	}
	
	public int indegree(int v) {
		validateVertex(v);
		return indegree[v];
	}
	
	public void addEdge(int v, int w) {
		validateVertex(v);
		validateVertex(w);
		
		adj_arry[v].add(w); // one direction
		e++;
	}
	

	
	
	private void validateVertex(int v) {
		if (v<0 || v>=V()) {
			throw new IllegalArgumentException("vertex "+v+" is not between 0 and "+(V()-1));
		}
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(V()+" vertices, "+E()+" edges"+NEWLINE);
		for (int v=0;v<V();v++) {
			s.append(v+"-> ");
			for (int w: adj(v)) {
				s.append(w+", ");
			}
			s.append(NEWLINE);
		}
		return s.toString();
	}
	
	public static void main(String[] args) {
		//      0 -- 6 
		//     /|\   | 
		//    | 1 2  4
		//     \    / \  
		//      \--5---3
		Digraph G = new Digraph(7);
		G.addEdge(0, 6); G.addEdge(0, 1); G.addEdge(0, 2); G.addEdge(0, 5);
		G.addEdge(6, 4); G.addEdge(5, 3); G.addEdge(5, 4); G.addEdge(4, 3);
		System.out.println(G.toString());
		Digraph G2 = new Digraph(G);
		System.out.println(G2.toString());	
		Digraph G3 = G.reverse();
		System.out.println(G3.toString());	
	}

}
