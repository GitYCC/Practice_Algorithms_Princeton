package com.ycchen.algorithms.fundamentals;

import java.lang.Iterable;
import java.util.Iterator;

import java.lang.IndexOutOfBoundsException;

public interface I_List<E> extends Iterable<E>{
	void append(E e);
	void add(int index,E e) throws IndexOutOfBoundsException;
	E get(int index) throws IndexOutOfBoundsException;
	void set(int index,E e) throws IndexOutOfBoundsException;
	E remove(int index) throws IndexOutOfBoundsException;
	boolean contains(E e);
	boolean isEmpty();
	int size();
	
	@Override
	Iterator<E> iterator();
}
