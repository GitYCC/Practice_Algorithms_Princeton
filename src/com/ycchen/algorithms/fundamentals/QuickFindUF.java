package com.ycchen.algorithms.fundamentals;

import com.ycchen.algorithms.fundamentals.AbstractUF;
import java.lang.IllegalArgumentException;

import edu.princeton.cs.introcs.StdIn; 
import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.Stopwatch;

public class QuickFindUF extends AbstractUF{
	private int[] id;
	
	public QuickFindUF(int N) throws IllegalArgumentException{
		if (N<0) throw new IllegalArgumentException();
		count = N;
		id = new int[N];
		for (int i=0;i<N;i++) id[i]=i;
	}
	
	@Override
	public void union(int p,int q) {
		if (connected(p,q)) return;
		int id_p = id[p];
		int id_q = id[q];
		for (int i=0;i<id.length;i++) {
			if (id[i]==id_p) id[i]=id_q;
		}
		count--;
	}
	
	@Override
	public int find(int p) {
		validate(p);
		return id[p];
	}
	
	@Override
	protected void validate(int p) throws IndexOutOfBoundsException {
		int n = id.length;
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
