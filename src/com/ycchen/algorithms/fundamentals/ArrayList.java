package com.ycchen.algorithms.fundamentals;

import com.ycchen.algorithms.fundamentals.I_List;
import java.util.NoSuchElementException;

import java.lang.IndexOutOfBoundsException;

import java.util.Iterator;

import com.ycchen.algorithms.test.TestHelper;

@SuppressWarnings("unchecked")
public class ArrayList<E> implements I_List<E>{
	private int cap;
	private int num;
	private E[] store;
	
	public ArrayList() {
		num = 0;
		cap = 2;
		store = (E[]) new Object[cap];
	}
	
	
	@Override
	public void append(E e) {
		if (num==cap) resize(cap*2);
		int avail = num%cap; // @@ use modular arithmetic
		store[avail] = e;
		num++;
	}
	
	@Override
	public void add(int index,E e) throws IndexOutOfBoundsException {
		validate(index);
		if (num==cap) resize(cap*2);
		
		for (int i=num;i>index;i--)
			store[i]=store[i-1];
		store[index]=e;
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
	
	private void validate(int index) throws IndexOutOfBoundsException {
		if (!( (index>=0)&&(index<num) ))
			throw new IndexOutOfBoundsException("Index is out of bounds");
	}
	
	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
		validate(index);
		E output = store[index];
		for (int i=index;i<num-1;i++)
			store[i]=store[i+1];
		store[num-1]=null;
		num--;
		
		if ( num == (cap/4) && num>0) resize(cap/2);
		return output;
	}
	
	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		validate(index);
		return store[index];
	}
	
	@Override
	public void set(int index,E e) throws IndexOutOfBoundsException {
		validate(index);
		store[index]=e;
	}
	
	@Override
	public boolean contains(E e) {
		for (E i:this)
			if (i==e)
				return true;
		return false;
	}
	
	@Override
	public boolean isEmpty() { return num==0; }
	
	@Override
	public int size() { return num; }
	
	@Override
	public Iterator<E> iterator() {return new ListIterator();}
	
	private class ListIterator implements Iterator<E> {
		private E[] iterArray;
		private int pointer;
		
		public ListIterator() { 
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
		output += "List [ ";
		for (E e:this)
			output += e.toString()+" ";
		output += "]";
		return output;
	}
	
	public static void test() {
		ArrayList<Integer> list = new ArrayList<>();
		list.append(0);
		list.append(1);
		list.append(5);
		list.add(2,3);
		list.add(3,4);
		list.add(2,2);
		for (int i: list)
			System.out.println(i);
		System.out.println(list.toString());
		TestHelper.verify(list.contains(4)==true,"contains test1");
		TestHelper.verify(list.contains(6)==false,"contains test2");
		
		TestHelper.verify(list.get(4)==4,"error 1");
		TestHelper.verify(list.remove(5)==5,"error 2");
		TestHelper.verify(list.remove(2)==2,"error 3");
		TestHelper.verify(list.isEmpty()==false,"error 4");
		System.out.println(list.toString());
	
		TestHelper.verify(list.size()==4,"error 5");
		TestHelper.verify(list.remove(2)==3,"error 6");
		TestHelper.verify(list.remove(2)==4,"error 7");
		TestHelper.verify(list.remove(0)==0,"error 8");
		list.append(10);
		System.out.println(list.toString());	
		
		TestHelper.verify(list.remove(0)==1,"error 9");
		TestHelper.verify(list.remove(0)==10,"error 10");
		TestHelper.verify(list.isEmpty()==true,"error 11");
		TestHelper.verify(list.size()==0,"error 12");
		System.out.println(list.toString());
	}
	public static void main(String[] args) throws Exception {
		test();
	}
}
