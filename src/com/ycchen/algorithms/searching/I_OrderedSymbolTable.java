package com.ycchen.algorithms.searching;

public interface I_OrderedSymbolTable<K extends Comparable<K>,V> {
	void put(K key, V val);
	V get(K key);
	boolean contains(K key);
	void delete(K key);
	K min();
	K max();
	K floor(K key);
	K ceiling(K key);
	int rank(K key);
	K select(int rank);
	void deleteMin();
	void deleteMax();
	Iterable<K> keys();
	Iterable<K> keys(K lo,K hi);
	int size();
	boolean isEmpty();
}
