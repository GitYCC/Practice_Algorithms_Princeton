package com.ycchen.algorithms.fundamentals;

import java.lang.Iterable;
import java.util.Iterator;

import java.util.NoSuchElementException;

public interface I_Deque<E> extends Iterable<E>{
	void pushFirst(E e);
	void pushLast(E e);
	E popFirst() throws NoSuchElementException;
	E popLast() throws NoSuchElementException;
	E first() throws NoSuchElementException;
	E last() throws NoSuchElementException;
	boolean isEmpty();
	int size();
	
	@Override
	Iterator<E> iterator();
}
