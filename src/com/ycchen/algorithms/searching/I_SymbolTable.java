package com.ycchen.algorithms.searching;

public interface I_SymbolTable<K,V> {
	void put(K key, V val);
	V get(K key);
	boolean contains(K key);
	void delete(K key);
	Iterable<K> keys();
	int size();
	boolean isEmpty();
}
