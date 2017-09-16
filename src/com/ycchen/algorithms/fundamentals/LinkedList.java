package com.ycchen.algorithms.fundamentals;

import com.ycchen.algorithms.fundamentals.I_List;
import com.ycchen.algorithms.fundamentals.Node;

import java.util.NoSuchElementException;
import java.lang.IllegalStateException;
import java.lang.IndexOutOfBoundsException;

import java.util.Iterator;

import com.ycchen.algorithms.test.TestHelper;

@SuppressWarnings("unchecked")
public class LinkedList<E> implements I_List<E>{
	
	private Node<E> sentinel;
	private int num;
	
	public LinkedList() {
		num = 0;
		sentinel = new Node<E>(null,null,null);
		sentinel.setNext(sentinel);
		sentinel.setPrev(sentinel);
	}
	
	
	@Override
	public void append(E e) {
		Node<E> newLastNode = new Node<E>(e,sentinel.getPrev(),sentinel);
		newLastNode.getNext().setPrev(newLastNode);
		newLastNode.getPrev().setNext(newLastNode);
		num++;
	}
	
	@Override
	public void add(int index,E e) throws IndexOutOfBoundsException {
		Node<E> addPoint = findPosition(index);
		Node<E> newInsertNode = new Node<E>(e,addPoint.getPrev(),addPoint);

		newInsertNode.getNext().setPrev(newInsertNode);
		newInsertNode.getPrev().setNext(newInsertNode);
		num++;
	}
	
	private void validate(Node<E> position) {
		if (position.getNext()==null) {
			throw new IllegalStateException("List has a invaild node");
		}
	}
	
	private Node<E> findPosition (int index) throws IndexOutOfBoundsException{
		if (index<0 || index>=num) throw new IndexOutOfBoundsException("Index is out of bounds");
		Node<E> node = sentinel;
		for (int i=0;i<=index;i++) {
			node = node.getNext();
			validate(node);
		}
		return node;
	}
	
	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
		Node<E> node = findPosition(index);
		E output = node.getElement();
		
		node.getPrev().setNext(node.getNext());
		node.getNext().setPrev(node.getPrev());
		
		node.setPrev(null);
		node.setNext(null);
		num--;
		return output;
	}
	
	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		Node<E> node = findPosition(index);
		return node.getElement();
	}
	
	@Override
	public void set(int index,E e) throws IndexOutOfBoundsException {
		Node<E> node = findPosition(index);
		node.setElement(e);
	}
	
	@Override
	public boolean contains(E e) {
		Node<E> node = sentinel;
		for (int i=0;i<num;i++) {
			node = node.getNext();
			if (e==node.getElement()) return true;
		}
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
		output += "List [ ";
		for (E e:this)
			output += e.toString()+" ";
		output += "]";
		return output;
	}
	
	public static void test() {
		LinkedList<Integer> list = new LinkedList<>();
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
