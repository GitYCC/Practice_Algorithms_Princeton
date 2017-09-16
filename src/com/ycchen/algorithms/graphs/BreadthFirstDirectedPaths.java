package com.ycchen.algorithms.graphs;

import com.ycchen.algorithms.fundamentals.ArrayStack;
import com.ycchen.algorithms.fundamentals.ArrayQueue;

public class BreadthFirstDirectedPaths {
	private final int s;
	private int[] edgeTo;
	private boolean[] marked;
	private int[] distTo;
	
	public BreadthFirstDirectedPaths(Digraph G,int source) {
		s = source;
		edgeTo = new int[G.V()];
		marked = new boolean[G.V()];
		distTo = new int[G.V()];
		validateVertex(s);
		bfs(G,s);
	}
	
	private void bfs(Digraph G,int s) {
		ArrayQueue<Integer> queue = new ArrayQueue<Integer>();
		distTo[s] = 0;
		queue.enqueue(s);
		while (!queue.isEmpty()) {
			int i = queue.dequeue();
			for (int w: G.adj(i)) {
				if (!marked[w]) {
					marked[w] = true;
					edgeTo[w] = i;
					distTo[w] = distTo[i]+1;
					queue.enqueue(w);
				}
			}
		}
	}
	
	public boolean hasPathTo(int v) {
		validateVertex(v);
		return marked[v];
	}
	
	public Iterable<Integer> pathTo(int v) {
		ArrayStack<Integer> stack = new ArrayStack<Integer>();
		if (!hasPathTo(v)) return null;
		for (int i=v; i!=s; i=edgeTo[i]) {
			stack.push(i);
		}
		stack.push(s);
		return stack;
	}
	
	public int distTo(int v) {
		validateVertex(v);
		return distTo[v];
	}
	
	private void validateVertex(int v) {
		if (v<0 || v>=marked.length) {
			throw new IllegalArgumentException("vertex "+v+" is not between 0 and "+(marked.length-1));
		}
	}
	
	public static void main(String[] args) {

		Digraph G = new Digraph(13);
		G.addEdge(7, 6); G.addEdge(7, 9); G.addEdge(6, 4); G.addEdge(6, 0); G.addEdge(6, 9);
		G.addEdge(6, 8); G.addEdge(8, 6); G.addEdge(11, 4); G.addEdge(11, 12); G.addEdge(12, 9);
		G.addEdge(9, 10); G.addEdge(9, 11); G.addEdge(10, 12); G.addEdge(0, 5); G.addEdge(0, 1);
		G.addEdge(5, 4); G.addEdge(4, 3); G.addEdge(4, 2); G.addEdge(3, 5); G.addEdge(3, 2);
		G.addEdge(2, 3); G.addEdge(2, 0); 
		
		BreadthFirstDirectedPaths paths = new BreadthFirstDirectedPaths(G,0);
		
		int target = 2;
		StringBuilder s = new StringBuilder();
		for (int w: paths.pathTo(target)){
			if (w!=target)	s.append(w + " -> ");
		}
		s.append(target);
		System.out.println(s.toString());

		target = 4;
		s = new StringBuilder();
		for (int w: paths.pathTo(target)){
			if (w!=target)	s.append(w + " -> ");
		}
		s.append(target);
		System.out.println(s.toString());
	}

}
