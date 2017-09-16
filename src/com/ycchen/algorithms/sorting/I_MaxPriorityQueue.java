package com.ycchen.algorithms.sorting;

import java.lang.Iterable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public interface I_MaxPriorityQueue<Key extends Comparable<Key>> 
		extends Iterable<Key> {
	
	void insert(Key key);
	Key max() throws NoSuchElementException;
	Key delMax() throws NoSuchElementException;
	boolean isEmpty();
	int size();
	Iterator<Key> iterator();
}
