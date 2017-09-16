package com.ycchen.algorithms.fundamentals;

import com.ycchen.algorithms.fundamentals.I_Deque;
import com.ycchen.algorithms.fundamentals.Node;

import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;

import java.util.Iterator;

import com.ycchen.algorithms.test.TestHelper;

@SuppressWarnings("unchecked")
public class LinkedDeque<E> implements I_Deque<E>{
	
	private Node<E> sentinel;
	private int num;
	
	public LinkedDeque() {
		num = 0;
		sentinel = new Node<E>(null,null,null);
		sentinel.setNext(sentinel);
		sentinel.setPrev(sentinel);
	}
	
	
	@Override
	public void pushLast(E e) {
		Node<E> newLastNode = new Node<E>(e,sentinel.getPrev(),sentinel);
		newLastNode.getNext().setPrev(newLastNode);
		newLastNode.getPrev().setNext(newLastNode);
		num++;
	}
	@Override
	public void pushFirst(E e) {
		Node<E> newFirstNode = new Node<E>(e,sentinel,sentinel.getNext());
		newFirstNode.getNext().setPrev(newFirstNode);
		newFirstNode.getPrev().setNext(newFirstNode);
		num++;
	}	
	

	@Override
	public E popFirst() throws NoSuchElementException {
		if (isEmpty()) throw new NoSuchElementException("This deque is empty.");
		
		Node<E> firstNode = sentinel.getNext();
		E output = firstNode.getElement();
		
		firstNode.getNext().setPrev(sentinel);
		sentinel.setNext(firstNode.getNext());
		
		firstNode.setPrev(null);
		firstNode.setNext(null);
		num--;
		return output;
	}
	@Override
	public E popLast() throws NoSuchElementException {
		if (isEmpty()) throw new NoSuchElementException("This deque is empty.");
		
		Node<E> lastNode = sentinel.getPrev();
		E output = lastNode.getElement();
		
		lastNode.getPrev().setNext(sentinel);
		sentinel.setPrev(lastNode.getPrev());
		
		lastNode.setPrev(null);
		lastNode.setNext(null);
		num--;
		return output;
	}	
	@Override
	public E first() throws NoSuchElementException {
		if (isEmpty()) throw new NoSuchElementException("This deque is empty.");
		Node<E> firstNode = sentinel.getNext();
		return firstNode.getElement();
	}
	@Override
	public E last() throws NoSuchElementException {
		if (isEmpty()) throw new NoSuchElementException("This deque is empty.");
		Node<E> lastNode = sentinel.getPrev();
		return lastNode.getElement();
	}	
	@Override
	public boolean isEmpty() { return num==0; }
	
	@Override
	public int size() { return num; }
	
	@Override
	public Iterator<E> iterator() {return new DequeIterator();}
	
	private class DequeIterator implements Iterator<E> {
		private E[] iterArray;
		private int pointer;
		
		public DequeIterator() { 
			iterArray = (E[]) new Object[num];
			Node<E> node = sentinel;
			for (int i=0;i<num;i++) {
				node = node.getNext();
				iterArray[i] = node.getElement();
			}
			pointer = -1;  // pointer initially locate outside of array 
		}
		public boolean hasNext() { return (pointer+1)<iterArray.length; }
		public E next() {
			if (!hasNext()) throw new NoSuchElementException();
			pointer++;
			return iterArray[pointer];
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	@Override
	public String toString(){
		String output = "";
		output += "Deque [ ";
		for (E e:this)
			output += e.toString()+" ";
		output += "]";
		return output;
	}
	
	public static void test() {
		LinkedDeque<Integer> deque = new LinkedDeque<>();
		
		deque.pushLast(1);
		deque.pushLast(2);
		deque.pushFirst(3);
		deque.pushFirst(4);
		deque.pushLast(5);
		deque.pushLast(6);
		for (int i: deque)
			System.out.println(i);
		System.out.println(deque.toString());
		
		TestHelper.verify(deque.last()==6,"err1");
		TestHelper.verify(deque.first()==4,"err2");
		TestHelper.verify(deque.popLast()==6,"err3");
		TestHelper.verify(deque.popLast()==5,"err4");
		System.out.println(deque.toString());
		
		TestHelper.verify(deque.popLast()==2,"err5");
		TestHelper.verify(deque.popFirst()==4,"err6");
		TestHelper.verify(deque.isEmpty()==false,"err7");
		System.out.println(deque.toString());
		
		TestHelper.verify(deque.popFirst()==3,"err8");
		TestHelper.verify(deque.popFirst()==1,"err9");
		TestHelper.verify(deque.isEmpty()==true,"err10");
		System.out.println(deque.toString());
				
	}
	public static void main(String[] args) throws Exception {
		test();
	}
}
