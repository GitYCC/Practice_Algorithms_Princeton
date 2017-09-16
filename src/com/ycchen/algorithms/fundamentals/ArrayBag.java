package com.ycchen.algorithms.fundamentals;

import com.ycchen.algorithms.fundamentals.I_Bag;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;

import java.util.Iterator;

import com.ycchen.algorithms.test.TestHelper;

@SuppressWarnings("unchecked")
public class ArrayBag<E> implements I_Bag<E>{
	private int cap;
	private int num;
	private E[] store;
	
	public ArrayBag() {
		num = 0;
		cap = 2;
		store = (E[]) new Object[cap];
	}
	
	
	@Override
	public void add(E e) {
		if (num==cap) resize(cap*2);
		store[num] = e;
		num++;
	}
	
	private void resize(int capacity){
		assert capacity>=num;
		
		E[] newStore = (E[]) new Object[capacity];
		
		
		for (int i=0;i<num;i++)
			newStore[i]=store[i];
		cap=capacity;
		store=newStore;
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
			for (int i=0;i<num;i++)
				iterArray[i] = store[i];
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
		ArrayBag<Integer> bag = new ArrayBag<>();
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
