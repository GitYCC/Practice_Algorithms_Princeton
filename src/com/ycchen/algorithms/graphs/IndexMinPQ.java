package com.ycchen.algorithms.graphs;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IndexMinPQ<Key extends Comparable<Key>> implements Iterable<Integer> {
	private int maxN; // max. number of elements on PQ
	private int n;    // number of elements on PQ
	private int[] pq; // binary heap using 1-based indexing
	private int[] qp; // inverse of pq.  qp[pq[i]]=pq[qp[i]]=i
	private Key[] keys; // keys[i] = priority of i
	
	public IndexMinPQ(int maxN) {
		if (maxN < 0) throw new IllegalArgumentException();
		this.maxN = maxN;
		n = 0;
		keys = (Key[]) new Comparable[maxN+1];
		pq = new int[maxN+1];
		qp = new int[maxN+1];
		for (int i=0;i<=maxN;i++) {
			qp[i] = -1;
		}
	}
	
	public boolean isEmpty() {
		return n==0;
	}
	
	public boolean contains(int i) {
		if (i<0||i>=maxN) throw new IndexOutOfBoundsException();
		return qp[i]!=-1;
	}
	
	public int size(){ return n; }
	
	public void insert(int i,Key key) {
		if (i<0||i>=maxN) throw new IndexOutOfBoundsException();
		if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue");
		n++;
		qp[i] = n;
		keys[i] = key;
		pq[n] = i;
		swim(n);
	}
	
	public int minIndex() {
		if (n==0) throw new NoSuchElementException("Priority queue underflow");
		return pq[1];
	}
	
	public Key minKey() {
		if (n==0) throw new NoSuchElementException("Priority queue underflow");
		return keys[pq[1]];
	}
	
	public int delMin(){
		if (n==0) throw new NoSuchElementException("Priority queue underflow");
		int min = pq[1];
		exch(1,n--);
		sink(1);
		qp[min]=-1;
		keys[min]=null;
		pq[n+1]=-1;
		return min;
	}
	
	public Key keyOf(int i) {
		if (i<0||i>=maxN) throw new IndexOutOfBoundsException();
		if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue");
		return keys[i];
	}
	
	public void changeKey(int i,Key key) {
		if (i<0||i>=maxN) throw new IndexOutOfBoundsException();
		if (contains(i)) throw new NoSuchElementException("index is not in the priority queue");		
		keys[i] = key;
		swim(qp[i]); sink(qp[i]); // only one can work
	}
	
	public void delete(int i) {
		if (i<0||i>=maxN) throw new IndexOutOfBoundsException();
		if (contains(i)) throw new NoSuchElementException("index is not in the priority queue");
		int index = qp[i];
		exch(index,n--);
		swim(index); sink(index);
		keys[i]=null;
		qp[i]=-1;
	}

	public Iterator<Integer> iterator() {return new HeapIterator();}
	
	public class HeapIterator implements Iterator<Integer> {
		private IndexMinPQ<Key> copy;
		
		public HeapIterator(){
			copy = new IndexMinPQ<Key>(pq.length-1);
			for (int i=1;i<=n;i++) {
				copy.insert(pq[i], keys[pq[i]]);
			}
		}
		public boolean hasNext() {return !copy.isEmpty();}
		public void remove() {throw new UnsupportedOperationException();}
		public Integer next() {
			if (!hasNext()) throw new NoSuchElementException();
			return copy.delMin();
		}
	}
	
	private void swim(int k) {
		while (k>1 && greater(k/2,k)) {
			exch(k,k/2);
			k = k/2;
		}
	}
	
	private void sink(int k) {
		while (2*k<=n) {
			int j = 2*k;
			if (j<n && greater(j,j+1)) j++;
			if (!greater(k,j)) break;
			exch(k,j);
			k = j;
		}
	}
	
	private boolean greater(int i,int j) {
		return keys[pq[i]].compareTo(keys[pq[j]])>0;
	}
	
	private void exch(int i,int j) {
		int swap = pq[i];
		pq[i] = pq[j];
		pq[j] = swap;
		qp[pq[i]] = i;
		qp[pq[j]] = j;
	}

}
