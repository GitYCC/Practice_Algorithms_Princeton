package com.ycchen.algorithms.fundamentals;

import java.lang.Iterable;
import java.util.Iterator;

import java.util.NoSuchElementException;

public interface I_Stack<E> extends Iterable<E>{
	void push(E e);
	E pop() throws NoSuchElementException;
	E peek() throws NoSuchElementException;
	boolean isEmpty();
	int size();
	
	@Override
	Iterator<E> iterator();
}
