package com.ycchen.algorithms.fundamentals;

import com.ycchen.algorithms.fundamentals.I_Bag;
import com.ycchen.algorithms.fundamentals.Node;

import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;

import java.util.Iterator;

import com.ycchen.algorithms.test.TestHelper;

@SuppressWarnings("unchecked")
public class LinkedBag<E> implements I_Bag<E>{
	
	private Node<E> sentinel;
	private int num;
	
	public LinkedBag() {
		num = 0;
		sentinel = new Node<E>(null,null,null);
		sentinel.setNext(sentinel);
		sentinel.setPrev(sentinel);
	}
	
	
	@Override
	public void add(E e) {
		Node<E> newLastNode = new Node<E>(e,sentinel.getPrev(),sentinel);
		newLastNode.getNext().setPrev(newLastNode);
		newLastNode.getPrev().setNext(newLastNode);
		num++;
	}
	
	@Override
	public boolean isEmpty() { return num==0; }
	
	@Override
	public int size() { return num; }
	
	@Override
	public Iterator<E> iterator() {return new BagIterator();}
	
	
	private class BagIterator implements Iterator<E> {
		private E[] iterArray;
		private int pointer;
		
		public BagIterator() { 
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
		output += "Bag [ ";
		for (E e:this)
			output += e.toString()+" ";
		output += "]";
		return output;
	}
	
	public static void test() {
		LinkedBag<Integer> bag = new LinkedBag<>();
		bag.add(1);
		bag.add(2);
		bag.add(3);
		bag.add(4);

		for (int i: bag)
			System.out.println(i);
		System.out.println(bag.toString());
		
		TestHelper.verify(bag.isEmpty()==false,"error 1");
		TestHelper.verify(bag.size()==4,"error 2");
		
		bag.add(5);
		System.out.println(bag.toString());
		TestHelper.verify(bag.size()==5,"error 3");		
	}
	public static void main(String[] args) throws Exception {
		test();
	}
}
