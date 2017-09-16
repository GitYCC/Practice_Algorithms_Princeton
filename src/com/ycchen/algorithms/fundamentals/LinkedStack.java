package com.ycchen.algorithms.fundamentals;

import com.ycchen.algorithms.fundamentals.I_Stack;


import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;

import java.util.Iterator;

import com.ycchen.algorithms.test.TestHelper;

@SuppressWarnings("unchecked")
public class LinkedStack<E> implements I_Stack<E>{
	
	private Node<E> sentinel;
	private int num;
	
	public LinkedStack() {
		num = 0;
		sentinel = new Node<E>(null,null,null);
		sentinel.setNext(sentinel);
		sentinel.setPrev(sentinel);
	}
		
	@Override
	public void push(E e) {
		Node<E> newLastNode = new Node<E>(e,sentinel.getPrev(),sentinel);
		newLastNode.getNext().setPrev(newLastNode);
		newLastNode.getPrev().setNext(newLastNode);
		num++;
	}
	
	@Override
	public E pop() throws NoSuchElementException {
		if (isEmpty()) throw new NoSuchElementException("This stack is empty.");
		
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
	public E peek() throws NoSuchElementException {
		if (isEmpty()) throw new NoSuchElementException("This stack is empty.");
		Node<E> lastNode = sentinel.getPrev();
		return lastNode.getElement();
	}
	
	@Override
	public boolean isEmpty() { return num==0; }
	
	@Override
	public int size() { return num; }
	
	@Override
	public Iterator<E> iterator() {return new StackIterator();}
	
	
	private class StackIterator implements Iterator<E> {
		private E[] iterArray;
		private int pointer;
		
		public StackIterator() { 
			iterArray = (E[]) new Object[num];
			Node<E> node = sentinel;
			for (int i=0;i<num;i++) {
				node = node.getPrev();
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
		output += "Stack [ ";
		for (E e:this)
			output += e.toString()+" ";
		output += "]";
		return output;
	}
	
	public static void test() {
		LinkedStack<Integer> stack = new LinkedStack<>();
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);
		stack.push(5);
		stack.push(6);
		for (int i: stack)
			System.out.println(i);
		System.out.println(stack.toString());
		TestHelper.verify(stack.peek()==6,"error 1");
		TestHelper.verify(stack.pop()==6,"error 2");
		TestHelper.verify(stack.pop()==5,"error 3");
		TestHelper.verify(stack.isEmpty()==false,"error 4");
		TestHelper.verify(stack.size()==4,"error 5");
		TestHelper.verify(stack.pop()==4,"error 6");
		TestHelper.verify(stack.pop()==3,"error 7");
		TestHelper.verify(stack.pop()==2,"error 8");
		stack.push(10);
		TestHelper.verify(stack.pop()==10,"error 9");
		TestHelper.verify(stack.pop()==1,"error 10");
		TestHelper.verify(stack.isEmpty()==true,"error 11");
		TestHelper.verify(stack.size()==0,"error 12");		
	}
	public static void main(String[] args) throws Exception {
		test();
	}
}
