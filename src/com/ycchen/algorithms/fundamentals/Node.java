package com.ycchen.algorithms.fundamentals;

import com.ycchen.algorithms.fundamentals.I_Position;

import java.lang.IllegalStateException;

public class Node<E> implements I_Position<E>{
	private E element;
	private Node<E> prev;
	private Node<E> next;
	public Node(E e,Node<E> p,Node<E> n) {
		element = e;
		prev = p;
		next = n;
	}
	
	@Override
	public E getElement() throws IllegalStateException {
		if (next==null)  // convention for defunct node
			throw new IllegalStateException("Position no longer valid");
		return element;
	}
	
	public Node<E> getPrev() { return prev; }
	public Node<E> getNext() { return next; }
	public void setElement(E e) {element=e;}
	public void setPrev(Node<E> p) { prev=p; }
	public void setNext(Node<E> n) { next=n; }

}
