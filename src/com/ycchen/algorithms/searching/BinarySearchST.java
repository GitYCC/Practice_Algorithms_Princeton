package com.ycchen.algorithms.searching;

import com.ycchen.algorithms.fundamentals.ArrayQueue;

@SuppressWarnings("unchecked")
public class BinarySearchST <K extends Comparable<K>,V>
			implements I_OrderedSymbolTable<K,V>{
	private static final int INIT_CAPACITY = 2;
	private K[] keys;
	private V[] vals;
	private int n=0;
	
	public BinarySearchST(int capacity) {
		keys = (K[]) new Comparable[capacity];
		vals = (V[]) new Object[capacity];
	}
	public BinarySearchST() {
		this(INIT_CAPACITY);
	}
	
	// keys: ["A","B","C","D"]
	// rank:   0   1   2   3
	
	@Override
	public int rank(K key){
		if (key==null) throw new IllegalArgumentException("first argument to rank() is null");
		int lo=0,hi=n-1;
		while (lo<=hi) { //@@
			int mid = lo + (hi-lo)/2;
			int cmp = key.compareTo(keys[mid]);
			if (cmp<0) {
				hi = mid-1;
			} else if (cmp>0) {
				lo = mid+1;
			} else {
				return mid;
			}
		}
		// if we can't find the key, return rank form a key higher than the key
		return lo;
	}
	@Override
	public K select(int rank){
		if (rank<0 || rank>=n) return null; 
		return keys[rank];
	}
	
	@Override
	public void put(K key, V val){
		if (key==null) throw new IllegalArgumentException("first argument to put() is null");
		if (val==null) {
			delete(key);
			return;
		}
		
		int i = rank(key);
		
		if ( i < n && keys[i].compareTo(key)==0) {
			vals[i]=val;
			return;
		}
					
		insert(i,key,val);
	}
	@Override
	public V get(K key){
		if (key==null) throw new IllegalArgumentException("first argument to get() is null");
		if (isEmpty()) return null;
		int i = rank(key);
		if (i<n && keys[i].compareTo(key)==0) return vals[i];
		else return null;
	}
	@Override
	public boolean contains(K key){
		if (key==null) throw new IllegalArgumentException("first argument to contain() is null");
		return get(key)!=null;
	}
	@Override
	public void delete(K key){
		if (key==null) throw new IllegalArgumentException("first argument to delete() is null");
		if (isEmpty()) return;
		
		int i = rank(key);
		if ( i<n && keys[i].compareTo(key)==0 ) {
			delete(i);
		}
	}

	@Override
	public K min(){
		if (isEmpty()) return null;
		return keys[0];
	}
	@Override
	public K max(){
		if (isEmpty()) return null;
		return keys[n-1];
	}
	@Override
	public K floor(K key){
		if (key==null) throw new IllegalArgumentException("first argument to floor() is null");
		if (isEmpty()) return null;
		int i = rank(key);
		if ( i<n && keys[i].compareTo(key)==0 ) return keys[i];
		if (i==0) return null;
		return keys[i-1];
	}
	@Override
	public K ceiling(K key){
		if (key==null) throw new IllegalArgumentException("first argument to ceiling() is null");
		if (isEmpty()) return null;
		int i = rank(key);
		if (i==n) return null;
		return keys[i];
	}

	@Override
	public void deleteMin(){
		if (isEmpty()) return;
		delete(0);
	}
	@Override
	public void deleteMax(){
		if (isEmpty()) return;
		delete(n-1);
	}
	@Override
	public Iterable<K> keys(){
		ArrayQueue<K> queue = new ArrayQueue<K>();
		int i = 0;
		int j = n-1;
		for (int k=i;k<=j;k++) {
			queue.enqueue(keys[k]);
		}
		return queue;
		
	}
	@Override
	public Iterable<K> keys(K lo,K hi){
		if (lo==null) throw new IllegalArgumentException("first argument to keys() is null");
		if (hi==null) throw new IllegalArgumentException("second argument to keys() is null");
		
		ArrayQueue<K> queue = new ArrayQueue<K>();
		if (lo.compareTo(hi)>0) return null;
		int i = rank(lo);
		int j = rank(hi);
		for (int k=i;k<=j;k++) {
			queue.enqueue(keys[k]);
		}
		return queue;
	}
	@Override
	public int size(){
		return n;
	}
	@Override
	public boolean isEmpty(){
		return size()==0;
	}
	
	/*
	 * array operation
	 */
	private void insert(int index,K key,V val) {
		if (n==keys.length) resize(keys.length*2);
		
		n++;
		int i = n-1;
		while(i>index) {
			keys[i] = keys[i-1];
			vals[i] = vals[i-1];
			i--;
		}
		keys[index]=key;
		vals[index]=val;
		
	}
	private void delete(int index) {
		n--;
		int i = index;
		while(i<n) {
			keys[i] = keys[i+1];
			vals[i] = vals[i+1];
			i++;
		}
		keys[i+1]=null;
		vals[i+1]=null;
		if (n<=keys.length/4){
			resize(keys.length/2);
		}
	}	
	private void resize(int cap){
		K[] newKeys = (K[]) new Comparable[cap];
		V[] newVals = (V[]) new Object[cap];
		for (int i=0;i<n;i++) {
			newKeys[i] = keys[i];
			newVals[i] = vals[i];
		}
		keys = newKeys;
		vals = newVals;
	}
	
	public static void main(String[] args) {
		BinarySearchST<String,Integer> st = new BinarySearchST<>();
		String[] keys = {"S","A","M","P","L","E"};
		for (int i=0;i<keys.length;i++){
			st.put(keys[i],i);
		}
		for (String s:st.keys())
			System.out.println(s+" "+st.get(s));
		System.out.println("-----");
		
		for (String s:st.keys("E","P"))
			System.out.println(s+" "+st.get(s));
		System.out.println("-----");
		
		st.delete("P");
		st.put("E", 3);
		st.delete("L");
		for (String s:st.keys())
			System.out.println(s+" "+st.get(s));
		System.out.println("-----");
		
		System.out.println("Min Key: "+st.min());
		System.out.println("Max Key: "+st.max());
		System.out.println("Floor of D: "+st.floor("D"));
		System.out.println("Ceiling of D: "+st.ceiling("D"));
		System.out.println("Rank of E: "+st.rank("E"));
		System.out.println("Key of rank 2: "+st.select(2));
		System.out.println("-----");
		
		st.deleteMin();
		st.deleteMax();
		st.put("E", 0);
		st.put("M", 1);
		st.put("Y", 3);
		st.put("X", 2);
		st.put("Z", 4);
		st.put("A", 1);
		st.put("B", 2);
		st.put("C", 3);
		for (String s:st.keys())
			System.out.println(s+" "+st.get(s));
		
	}	

}
