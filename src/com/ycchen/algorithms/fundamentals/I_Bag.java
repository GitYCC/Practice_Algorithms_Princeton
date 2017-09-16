package com.ycchen.algorithms.fundamentals;

import java.lang.Iterable;
import java.util.Iterator;

public interface I_Bag<E> extends Iterable<E>{
	void add(E e);
	boolean isEmpty();
	int size();
	
	@Override
	Iterator<E> iterator();
}
