package com.ycchen.algorithms.sorting;

import com.ycchen.algorithms.sorting.I_MinPriorityQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import java.util.Comparator;

@SuppressWarnings("unchecked")
public class HeapMinPQ<Key extends Comparable<Key>> 
		implements I_MinPriorityQueue<Key>{
	private Key[] pq;
	private int n;
	private Comparator<Key> comparator;
	
	public HeapMinPQ(int initCapacity) {
		pq = (Key[]) new Comparable[initCapacity+1]; // +1 for null index 0
		n = 0;
		comparator = null;
	}
	
	public HeapMinPQ() {
		this(1);
	}
	
	public HeapMinPQ(int initCapacity, Comparator<Key> comparator) {
		pq = (Key[]) new Comparable[initCapacity+1]; // +1 for null index 0
		n = 0;
		this.comparator = comparator;		
	}
	
	public HeapMinPQ(Comparator<Key> comparator) {
		this(1,comparator);
	}
	
	public HeapMinPQ(Key[] keys) {
		pq = (Key []) new Comparable[keys.length+1]; // +1 for null index 0
		for (int i=0;i<keys.length;i++) pq[i+1] = keys[i];
		n = keys.length;
		this.comparator=null;
		// restore the heap invariant
		for (int i=parent(n);i>=1;i--) sink(i);
		assert isMinHeap();
	}
	
	@Override
	public void insert(Key key) {
		if (n+1>pq.length-1) resize(pq.length*2);
		pq[++n] = key;
		swim(n);
		assert isMinHeap();
	}
	
	@Override
	public Key min() throws NoSuchElementException {
		if (isEmpty()) throw new NoSuchElementException("Priority queue is empty.");
		return pq[1];
	}
	
	@Override
	public Key delMin() throws NoSuchElementException {
		if (isEmpty()) throw new NoSuchElementException("Priority queue is empty.");
		Key min = pq[1];
		exch(1,n--);
		sink(1);
		pq[n+1] = null;
		if ((n>0) && (n<=(pq.length-1)/4)) resize(pq.length/2);
		return min;
	}
	
	@Override
	public boolean isEmpty() {
		return n==0;
	}
	
	@Override
	public int size() {
		return n;
	}
	
	@Override
	public Iterator<Key> iterator() {
		return new HeapIterator();
	}
	
	private class HeapIterator implements Iterator<Key> {
		//create a new pq
		private HeapMinPQ<Key> copy;
		
		//add all items to copy of heap
		//takes linear time since already in heap order so no keys move
		public HeapIterator() {
			if (comparator==null) copy=new HeapMinPQ<Key>(size());
			else copy=new HeapMinPQ<Key>(size(),comparator);
			for (int i=1;i<=n;i++) copy.insert(pq[i]);
		}
		@Override
		public boolean hasNext() {return !copy.isEmpty();}
		@Override
		public Key next() {return copy.delMin();}
		@Override
		public void remove() {throw new UnsupportedOperationException();}
	}

	private void resize(int capacity){
		assert capacity>=n+1;
		
		Key[] newPQ = (Key[]) new Comparable[capacity];
		
		for (int i=0;i<n+1;i++)
			newPQ[i]=pq[i];
		
		pq=newPQ;
	}
	/*
	 * Helper function to restore the heap invariant
	 */
	private void swim(int k) {
		while (true) {
			int p = parent(k);
			if (p==-1) return;
			if (!less(k,p)) return;
			exch(p,k);
			k = p;
		}
	}
	private void sink(int k) {
		while (true) {
			int l = left(k);
			int r = right(k);
			if ((l==-1)&&(r==-1)) break;
			
			int swap;
			if ((r!=-1)&&(!less(l,r))) swap=r;
			else swap=l;
		
			if (!less(swap,k)) break;
		
			exch(swap,k);
			k = swap;
		}
	}
	private int parent(int i) {
		int parent = i/2;
		if (isValidLeaf(parent)) {
			return parent;
		} else {
			return -1;
		}
	}
	private int left(int i) {
		int child = i*2;
		if (isValidLeaf(child)) {
			return child;
		} else {
			return -1;
		}
	}
	private int right(int i) {
		int child = i*2+1;
		if (isValidLeaf(child)) {
			return child;
		} else {
			return -1;
		}
	}
	private boolean isValidLeaf(int i) {
		return ((i>0)&&(i<=n));
	}
	/*
	 * Helper function for compares and exchange
	 */
	private boolean less(int i,int j) {
		if (comparator==null) {
			return ((Comparable<Key>)pq[i]).compareTo(pq[j])<0;
		} else {
			return comparator.compare(pq[i], pq[j])<0;
		}
	}
	private void exch(int i,int j) {
		Key temp = pq[i];
		pq[i] = pq[j];
		pq[j] = temp;
	}
	
	/*
	 * Test function
	 */
	public boolean isMinHeap() {
		return isMinHeap(1);
	}
	private boolean isMinHeap(int k) {
		if (k>n) return true; // for end of iteration
		int l = left(k);
		int r = right(k);
		if ((l!=-1)&&(!less(k,l))) return false;
		if ((r!=-1)&&(!less(k,r))) return false;
		return isMinHeap(l) && isMinHeap(r);
	}
	
	public static void main(String[] args) throws Exception {
		Integer[] keys = {4,5,6,7,8};
		HeapMinPQ<Integer> minPQ = new HeapMinPQ<>(keys);
		System.out.println(minPQ.min());
		minPQ.insert(3);
		System.out.println(minPQ.min());
		minPQ.insert(9);
		System.out.println(minPQ.min());
		for (int k: minPQ) {
			System.out.print(k);
			System.out.print(" ");
		}
		System.out.print("\n");
		
		System.out.println(minPQ.delMin());
		System.out.println(minPQ.delMin());
		System.out.println(minPQ.delMin());
		for (int k: minPQ) {
			System.out.print(k);
			System.out.print(" ");
		}
		System.out.print("\n");
		
		minPQ.insert(1);
		minPQ.insert(10);
		minPQ.insert(2);
		for (int k: minPQ) {
			System.out.print(k);
			System.out.print(" ");
		}	
		System.out.print("\n");
		
	}
	
}
