package com.ycchen.algorithms.sorting;

import java.lang.Iterable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public interface I_MinPriorityQueue<Key extends Comparable<Key>> 
		extends Iterable<Key> {
	
	void insert(Key key);
	Key min() throws NoSuchElementException;
	Key delMin() throws NoSuchElementException;
	boolean isEmpty();
	int size();
	Iterator<Key> iterator();
}
