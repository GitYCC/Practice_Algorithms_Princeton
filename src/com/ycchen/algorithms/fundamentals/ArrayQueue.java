package com.ycchen.algorithms.fundamentals;

import com.ycchen.algorithms.fundamentals.I_Queue;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;

import java.util.Iterator;

import com.ycchen.algorithms.test.TestHelper;

@SuppressWarnings("unchecked")
public class ArrayQueue<E> implements I_Queue<E>{
	private int cap;
	private int first;
	private int num;
	private E[] store;
	
	public ArrayQueue() {
		num = 0;
		cap = 2;
		first = 0;
		store = (E[]) new Object[cap];
	}
	
	
	@Override
	public void enqueue(E e) {
		if (num==cap) resize(cap*2);
		int avail = (first+num)%cap; // @@ use modular arithmetic
		store[avail] = e;
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
	public E dequeue() throws NoSuchElementException {
		if (isEmpty()) throw new NoSuchElementException("This queue is empty.");
		E output = store[first];
		store[first] = null;
		num--;
		first = (first+1)%cap;
		if ( num == (cap/4) && num>0) resize(cap/2);
		return output;
	}
	
	@Override
	public E peek() throws NoSuchElementException {
		if (isEmpty()) throw new NoSuchElementException("This queue is empty.");
		return store[first];
	}
	
	@Override
	public boolean isEmpty() { return num==0; }
	
	@Override
	public int size() { return num; }
	
	@Override
	public Iterator<E> iterator() {return new QueueIterator();}
	
	private class QueueIterator implements Iterator<E> {
		private E[] iterArray;
		private int pointer;
		
		public QueueIterator() { 
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
		output += "Queue [ ";
		for (E e:this)
			output += e.toString()+" ";
		output += "]";
		return output;
	}
	
	public static void test() {
		ArrayQueue<Integer> queue = new ArrayQueue<>();
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
		queue.enqueue(4);
		queue.enqueue(5);
		queue.enqueue(6);
		for (int i: queue)
			System.out.println(i);
		System.out.println(queue.toString());
		TestHelper.verify(queue.peek()==1,"error 1");
		TestHelper.verify(queue.dequeue()==1,"error 2");
		TestHelper.verify(queue.dequeue()==2,"error 3");
		TestHelper.verify(queue.isEmpty()==false,"error 4");
		TestHelper.verify(queue.size()==4,"error 5");
		TestHelper.verify(queue.dequeue()==3,"error 6");
		TestHelper.verify(queue.dequeue()==4,"error 7");
		TestHelper.verify(queue.dequeue()==5,"error 8");
		queue.enqueue(10);
		TestHelper.verify(queue.dequeue()==6,"error 9");
		TestHelper.verify(queue.dequeue()==10,"error 10");
		TestHelper.verify(queue.isEmpty()==true,"error 11");
		TestHelper.verify(queue.size()==0,"error 12");		
	}
	public static void main(String[] args) throws Exception {
		test();
	}
}
