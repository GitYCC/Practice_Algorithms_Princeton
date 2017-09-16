package com.ycchen.algorithms.fundamentals;

import com.ycchen.algorithms.fundamentals.I_Deque;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;

import java.util.Iterator;

import com.ycchen.algorithms.test.TestHelper;

@SuppressWarnings("unchecked")
public class ArrayDeque<E> implements I_Deque<E>{
	private int cap;
	private int first;
	private int num;
	private E[] store;
	
	public ArrayDeque() {
		num = 0;
		cap = 2;
		first = 0;
		store = (E[]) new Object[cap];
	}
	
	
	@Override
	public void pushLast(E e) {
		if (num==cap) resize(cap*2);
		int endAvail = (first+num)%cap; // use modular arithmetic
		store[endAvail] = e;
		num++;
	}
	@Override
	public void pushFirst(E e) {
		if (num==cap) resize(cap*2);
		int headAvail = (first-1+cap)%cap; // use modular arithmetic
		store[headAvail] = e;
		first = headAvail;
		num++;
	}	
	
	private void resize(int capacity){
		assert capacity>=num;
		
		E[] newStore = (E[]) new Object[capacity];
		
		for (int i=0;i<num;i++)
			newStore[i]=store[(first+i)%cap];
		first = 0;
		cap=capacity;
		store=newStore;
	}
	@Override
	public E popFirst() throws NoSuchElementException {
		if (isEmpty()) throw new NoSuchElementException("This deque is empty.");
		E output = store[first];
		store[first] = null;
		num--;
		first = (first+1)%cap;
		
		if ( num == (cap/4) && num>0) resize(cap/2);
		return output;
	}
	@Override
	public E popLast() throws NoSuchElementException {
		if (isEmpty()) throw new NoSuchElementException("This deque is empty.");
		int last = (first+num-1)%cap;
		E output = store[last];
		store[last] = null;
		num--;
		
		if ( num == (cap/4) && num>0) resize(cap/2);
		return output;
	}	
	@Override
	public E first() throws NoSuchElementException {
		if (isEmpty()) throw new NoSuchElementException("This deque is empty.");
		return store[first];
	}
	@Override
	public E last() throws NoSuchElementException {
		if (isEmpty()) throw new NoSuchElementException("This deque is empty.");
		int last = (first+num-1)%cap;
		return store[last];
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
			for (int i=0;i<num;i++)
				iterArray[i] = store[(first+i)%cap];
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
		ArrayDeque<Integer> deque = new ArrayDeque<>();
		
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
