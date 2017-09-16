package com.ycchen.algorithms.graphs;

import com.ycchen.algorithms.fundamentals.ArrayQueue;
import com.ycchen.algorithms.fundamentals.ArrayStack;

public class DepthFirstOrder {
	private boolean[] marked;
	private int[] pre;
	private int[] post;
	private ArrayQueue<Integer> preorder;
	private ArrayQueue<Integer> postorder;
	private int preCounter;
	private int postCounter;
	
	public DepthFirstOrder(Digraph G) {
		pre = new int[G.V()];
		post = new int[G.V()];
		postorder = new ArrayQueue<Integer>();
		preorder = new ArrayQueue<Integer>();
		marked = new boolean[G.V()];
		for (int v=0; v<G.V(); v++) 
			if (!marked[v]) dfs(G,v);
	}
	
	private void dfs(Digraph G, int v) {
		marked[v] = true;
		pre[v] = preCounter++;
		preorder.enqueue(v);
		for (int w: G.adj(v))
			if (!marked[w]) dfs(G,w);
		postorder.enqueue(v);
		post[v] = postCounter++;
	}
	
	public Iterable<Integer> post() {
		return postorder;
	}
	
	public Iterable<Integer> pre() {
		return preorder;
	}
	
	public Iterable<Integer> reversePost() {
		ArrayStack<Integer> reverse = new ArrayStack<Integer>();
		for (int v: postorder) 
			reverse.push(v);
		
		return reverse;
	}
	


}
