package com.ycchen.algorithms.fundamentals;

import com.ycchen.algorithms.fundamentals.AbstractUF;
import java.lang.IllegalArgumentException;

import edu.princeton.cs.introcs.StdIn; 
import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.Stopwatch;

public class WeightedQuickUnionUF extends AbstractUF{
	private int[] parent;
	private int[] size;
	
	public WeightedQuickUnionUF(int N) throws IllegalArgumentException{
		if (N<0) throw new IllegalArgumentException();
		count = N;
		parent = new int[N];
		size = new int[N];
		for (int i=0;i<N;i++) {
			parent[i]=i;
			size[i] = 1;
		}
	}
	
	@Override
	public void union(int p,int q) {
		int rootP = find(p);
		int rootQ = find(q);
		if (rootP==rootQ) return;
		if (size[rootP]<=size[rootQ]) {
			parent[rootP] = rootQ;
			size[rootQ] += size[rootP];
			size[rootP] = 0;
		}else{
			parent[rootQ] = rootP;
			size[rootP] += size[rootQ];
			size[rootQ] = 0;			
		}
		count--;
	}
	
	@Override
	public int find(int p) {
		validate(p);
		while (p!=parent[p])
			p = parent[p];
		return p;
	}
	
	@Override
	protected void validate(int p) throws IndexOutOfBoundsException {
		int n = parent.length;
		if (p<0 || p>=n) {
			throw new IndexOutOfBoundsException("Index "+p+" is not between 0 and "+(n-1));
		}
	}
	
	@Override
	public boolean connected(int p,int q) {
		return find(p)==find(q);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int N = StdIn.readInt();
		Stopwatch stopwatch = new Stopwatch();
		QuickFindUF uf = new QuickFindUF(N);
		while (!StdIn.isEmpty()) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			if (uf.connected(p, q)) continue;
			uf.union(p, q);
			StdOut.println(p+" "+q);

		}
		StdOut.println(uf.count()+" components");
		StdOut.println("Elapsed Time: " + stopwatch.elapsedTime() + " sec");
	}

}
