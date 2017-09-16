package com.ycchen.algorithms.fundamentals;

import java.lang.Iterable;
import java.util.Iterator;

import java.util.NoSuchElementException;

public interface I_Queue<E> extends Iterable<E>{
	void enqueue(E e);
	E dequeue() throws NoSuchElementException;
	E peek() throws NoSuchElementException;
	boolean isEmpty();
	int size();
	
	@Override
	Iterator<E> iterator();
}
