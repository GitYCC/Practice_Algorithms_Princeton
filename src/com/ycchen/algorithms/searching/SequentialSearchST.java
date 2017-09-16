package com.ycchen.algorithms.searching;

import com.ycchen.algorithms.fundamentals.ArrayQueue;

public class SequentialSearchST<K,V> implements I_SymbolTable<K,V> {
	private int n; // num of pairs
	private Node head;
	
	protected class Node {
		protected K key;
		protected V val;
		protected Node next;
		public Node(K key,V val,Node next) {
			this.key = key;
			this.val = val;
			this.next = next;
		}
	}
	
	public SequentialSearchST() {
		n=0;
		head=new Node(null,null,null);
	}
	
	@Override
	public void put(K key, V val){
		if (key==null) throw new IllegalArgumentException("first argument to put() is null");
		if (val==null) {
			delete(key);
			return;
		}
		// searching node has same key
		Node pointer = head;
		while (pointer.next!=null) {
			pointer = pointer.next;			
			if (pointer.key.equals(key)) {
				pointer.val = val;
				return;
			}
		}
		// if not, add node
		pointer.next=new Node(key,val,null);
		n++;
	}
	@Override
	public V get(K key){
		if (key==null) throw new IllegalArgumentException("first argument is null");
		// searching node has same key
		Node pointer = head;
		while (pointer.next!=null) {
			pointer = pointer.next;			
			if (pointer.key.equals(key)) {
				return pointer.val;
			}
		}
		// if not, return null
		return null;
	}
	@Override
	public boolean contains(K key){
		if (key==null) throw new IllegalArgumentException("first argument is null");
		return get(key)!=null;
	}
	@Override
	public void delete(K key){
		if (key==null) throw new IllegalArgumentException("first argument is null");
		// searching node has same key
		Node pointer = head;
		while (pointer.next!=null) {
			if (pointer.next.key.equals(key)) {
				Node node = pointer.next;
				pointer.next = node.next;
				node.next=null;
				n--;
				return;
			}
			pointer = pointer.next;
		}
		// if not, close
		return;		
	}
	@Override
	public Iterable<K> keys(){
		ArrayQueue<K> queue = new ArrayQueue<K>();
		Node pointer = head;
		while (pointer.next!=null) {
			pointer = pointer.next;			
			queue.enqueue(pointer.key);
		}
		return queue;
	}
	
	@Override
	public int size() {
		return n;
	}
	
	@Override
	public boolean isEmpty() {
		return size()==0;
	}
	
	public static void main(String[] args) {
		SequentialSearchST<String,Integer> st = new SequentialSearchST<>();
		String[] keys = {"S","A","M","P","L","E"};
		for (int i=0;i<keys.length;i++){
			st.put(keys[i],i);
		}
		for (String s:st.keys())
			System.out.println(s+" "+st.get(s));
		
		System.out.println("-----");
		st.delete("P");
		st.put("E", 3);
		st.delete("L");
		for (String s:st.keys())
			System.out.println(s+" "+st.get(s));
	}
}
