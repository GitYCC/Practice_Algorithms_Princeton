package com.ycchen.algorithms.searching;

import com.ycchen.algorithms.fundamentals.ArrayQueue;

@SuppressWarnings("unchecked")
public class SeparateChainingHashST<K,V> implements I_SymbolTable<K,V> {
	private static final int INIT_CAPACITY = 4;
	
	private int n; // num of pairs
	private int m; // hash table size
	private SequentialSearchST<K,V>[] st;
	

	public SeparateChainingHashST() {
		this(INIT_CAPACITY);
	}
	
	public SeparateChainingHashST(int m) {
		this.m = m;
		n = 0;
		st = (SequentialSearchST<K,V>[]) new SequentialSearchST[m];
		for (int i=0;i<m;i++) {
			st[i] = new SequentialSearchST<K,V>();
		}
	}
	
	private int hash(K key){
		return (key.hashCode() & 0x7fffffff ) % m;
	}
	
	private void resize(int cap) {
		SeparateChainingHashST<K,V> temp = new SeparateChainingHashST<K,V>(cap);
		for (int i =0;i<m;i++) {
			for (K key: st[i].keys()){
				temp.put(key,st[i].get(key));
			}
		}
		this.m = temp.m;
		this.n = temp.n;
		this.st = temp.st;
	}
	
	@Override
	public void put(K key, V val){
		if (key==null) throw new IllegalArgumentException("first argument to put() is null");
		if (val==null) {
			delete(key);
			return;
		}
		
		if (n>=10*m) resize(2*m);
		
		int i = hash(key);
		if (!st[i].contains(key)) n++;
		st[i].put(key, val);
	}
	@Override
	public V get(K key){
		if (key==null) throw new IllegalArgumentException("first argument is null");

		int i = hash(key);
		return st[i].get(key);
	}
	@Override
	public boolean contains(K key){
		if (key==null) throw new IllegalArgumentException("first argument is null");
		return get(key)!=null;
	}
	@Override
	public void delete(K key){
		if (key==null) throw new IllegalArgumentException("first argument is null");
		int i = hash(key);
		if (!st[i].contains(key)) n--;
		st[i].delete(key);
		
		if ( m > INIT_CAPACITY && n <= 2*m) resize(m/2);
	}
	@Override
	public Iterable<K> keys(){
		ArrayQueue<K> queue = new ArrayQueue<K>();
		for (int i=0; i<m; i++){
			for (K key: st[i].keys()){
				queue.enqueue(key);
			}
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
		SeparateChainingHashST<String,Integer> st = new SeparateChainingHashST<>();
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
