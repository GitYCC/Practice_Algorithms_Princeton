package com.ycchen.algorithms.fundamentals;

import com.ycchen.algorithms.fundamentals.I_Queue;
import com.ycchen.algorithms.fundamentals.Node;

import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;

import java.util.Iterator;

import com.ycchen.algorithms.test.TestHelper;

@SuppressWarnings("unchecked")
public class LinkedQueue<E> implements I_Queue<E>{
	
	private Node<E> sentinel;
	private int num;
	
	public LinkedQueue() {
		num = 0;
		sentinel = new Node<E>(null,null,null);
		sentinel.setNext(sentinel);
		sentinel.setPrev(sentinel);
	}
	
	
	@Override
	public void enqueue(E e) {
		Node<E> newLastNode = new Node<E>(e,sentinel.getPrev(),sentinel);
		newLastNode.getNext().setPrev(newLastNode);
		newLastNode.getPrev().setNext(newLastNode);
		num++;
	}
	

	@Override
	public E dequeue() throws NoSuchElementException {
		if (isEmpty()) throw new NoSuchElementException("This queue is empty.");
		
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
	public E peek() throws NoSuchElementException {
		if (isEmpty()) throw new NoSuchElementException("This queue is empty.");
		Node<E> firstNode = sentinel.getNext();
		return firstNode.getElement();
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
		output += "Queue [ ";
		for (E e:this)
			output += e.toString()+" ";
		output += "]";
		return output;
	}
	
	public static void test() {
		LinkedQueue<Integer> queue = new LinkedQueue<>();
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
