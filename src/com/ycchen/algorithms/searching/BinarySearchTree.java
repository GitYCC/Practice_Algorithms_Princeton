package com.ycchen.algorithms.searching;

import com.ycchen.algorithms.fundamentals.ArrayQueue;
import com.ycchen.algorithms.searching.SequentialSearchST.Node;

@SuppressWarnings("unchecked")
public class BinarySearchTree <K extends Comparable<K>,V>
			implements I_OrderedSymbolTable<K,V>{
	
	private Node root;

	private class Node {
		private K key;
		private V val;
		private Node left=null;
		private Node right=null;
		private int size;
		public Node(K key,V val,int size) {
			this.key = key;
			this.val = val;
			this.size = size;
		}
	}
	
	public BinarySearchTree() {	}
	
	@Override
	public void put(K key, V val){
		if (key==null) throw new IllegalArgumentException("first argument to put() is null");
		if (val==null) {
			delete(key);
			return;
		}
		
		root = put(root,key,val);
	}
	private Node put(Node x,K key,V val) {
		if (x==null) return new Node(key,val,1);
		int cmp = x.key.compareTo(key);
		if      (cmp>0) x.left=put(x.left,key,val);
		else if (cmp<0) x.right=put(x.right,key,val);
		else            x.val=val;
		x.size = 1 + size(x.left) + size(x.right);
		return x;
	}
	
	@Override
	public V get(K key){
		if (key==null) throw new IllegalArgumentException("first argument to get() is null");
		if (isEmpty()) return null;
		return get(root,key);
	}
	private V get(Node x,K key) {
		if (x==null) return null;
		int cmp = x.key.compareTo(key);
		if      (cmp>0) return get(x.left,key);
		else if (cmp<0) return get(x.right,key);
		else            return x.val;
	}
	
	@Override
	public int rank(K key){
		if (key==null) throw new IllegalArgumentException("first argument to rank() is null");
		return rank(key,root);
	}
	private int rank(K key,Node x) {
		if (x==null) return 0;
		int cmp = x.key.compareTo(key);
		if      (cmp>0) return rank(key,x.left);
		else if (cmp<0) return size(x.left)+1+rank(key,x.right);
		else            return size(x.left);
		// if we can't find the key, return rank form a key higher than the key
	}
	@Override
	public K select(int rank){
		if (rank<0 || rank>=size()) return null; 
		Node x = select(root,rank);
		return x.key;
	}
	private Node select(Node x,int rank) {
		if (x==null) return null;
		int t = size(x.left);
		if      (t>rank) return select(x.left,rank);
		else if (t<rank) return select(x.right,rank-(t+1)); //@@
		else                        return x;
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
		root = delete(root,key);
	}
	private Node delete(Node x,K key) {
		if (x==null) return null;
		int cmp = x.key.compareTo(key);
		if      (cmp>0) x.left=delete(x.left,key);
		else if (cmp<0) x.right=delete(x.right,key);
		else {
			if (x.right==null) return x.left; //@@
			if (x.left==null) return x.right; //@@
			Node t = x;
			x = min(t.right);
			x.right = deleteMin(t.right);
			x.left = t.left;
		}
		x.size = size(x.left)+size(x.right)+1;
		return x;
	}

	@Override
	public K min(){
		if (isEmpty()) return null;
		return min(root).key;
	}
	private Node min(Node x) {
		if (x.left==null) return x;
		return min(x.left);
	}
	@Override
	public K max(){
		if (isEmpty()) return null;
		return max(root).key;
	}
	private Node max(Node x) {
		if (x.right==null) return x;
		return max(x.right);
	}
	@Override
	public void deleteMin(){
		if (isEmpty()) return;
		root = deleteMin(root);
	}
	private Node deleteMin(Node x){
		if (x.left==null) return x.right; //@@
		x.left = deleteMin(x.left);
		x.size = size(x.left)+size(x.right)+1; //@@
		return x;
	}
	@Override
	public void deleteMax(){
		if (isEmpty()) return;
		root = deleteMax(root);
	}
	private Node deleteMax(Node x){
		if (x.right==null) return x.left;
		x.right = deleteMin(x.right);
		x.size = size(x.left)+size(x.right)+1;
		return x;
	}
	@Override
	public K floor(K key){
		if (key==null) throw new IllegalArgumentException("first argument to floor() is null");
		if (isEmpty()) return null;
		Node x = floor(root,key);
		if (x==null) return null;
		else return x.key;
	}
	private Node floor(Node x,K key) {  //@@
		if (x==null) return null;
		int cmp = x.key.compareTo(key);
		if      (cmp==0) return x;
		else if (cmp>0)  return floor(x.left,key);
		else {
			Node t=floor(x.right,key);
			if (t==null) return x;
			else         return t;
		}
	}
	@Override
	public K ceiling(K key){
		if (key==null) throw new IllegalArgumentException("first argument to ceiling() is null");
		if (isEmpty()) return null;
		Node x = ceiling(root,key);
		if (x==null) return null;
		else return x.key;
	}
	private Node ceiling(Node x,K key) { //@@
		if (x==null) return null;
		int cmp = x.key.compareTo(key);
		if      (cmp==0) return x;
		else if (cmp>0) {
			Node t = ceiling(x.left,key);
			if (t==null) return x;
			else         return t;
		} else 	return ceiling(x.right,key);
		
	}

	@Override
	public Iterable<K> keys(){
		return keys(min(),max());
	}
	@Override
	public Iterable<K> keys(K lo,K hi){
		if (lo==null) throw new IllegalArgumentException("first argument to keys() is null");
		if (hi==null) throw new IllegalArgumentException("second argument to keys() is null");
		if (lo.compareTo(hi)>0) return null;
		ArrayQueue<K> queue = new ArrayQueue<K>();
		keys(root,queue,lo,hi);
		return queue;
	}
	private void keys (Node x,ArrayQueue<K> queue,K lo,K hi){ //@@
		//*** use in-order traversal ***
		if (x==null) return;
		int cmp_lo = x.key.compareTo(lo);
		int cmp_hi = x.key.compareTo(hi);
		
		// left child
		if (cmp_lo>0) keys(x.left,queue,lo,hi);
		
		// visit this node
		if (cmp_lo>=0 && cmp_hi<=0) queue.enqueue(x.key);

		// right child
		if (cmp_hi<0) keys(x.right,queue,lo,hi); 
	}
	
	public int size(K lo,K hi){
		if (lo==null) throw new IllegalArgumentException("first argument to size() is null");
		if (hi==null) throw new IllegalArgumentException("second argument to size() is null");
		if (hi.compareTo(lo)<0) return 0;
		if (contains(hi)) return rank(hi)-rank(lo)+1;  //@@
		else              return rank(hi)-rank(lo);
	}
	
	@Override
	public int size(){
		return size(root);
	}
	private int size(Node x) {
		if (x==null) return 0;
		return x.size;
	}
	@Override
	public boolean isEmpty(){
		return size()==0;
	}
	
	public int height() {
		return height(root);
	}
	// a 1-node tree has height 0
	private int height(Node x) { //@@
		if (x==null) return -1;
		return 1+Math.max(height(x.left),height(x.left));
	}
	public Iterable<K> levelOrder() {
		ArrayQueue<K> keys = new ArrayQueue<K>();
		ArrayQueue<Node> nodes = new ArrayQueue<Node>();
		nodes.enqueue(root);
		while (!nodes.isEmpty()) {
			Node x = nodes.dequeue();
			if (x==null) continue;
			keys.enqueue(x.key);
			nodes.enqueue(x.left);
			nodes.enqueue(x.right);
		}
		return keys;
	}
	
	public static void main(String[] args) {
		BinarySearchTree<String,Integer> st = new BinarySearchTree<>();
		String[] keys = {"S","A","M","P","L","E"};
		for (int i=0;i<keys.length;i++){
			st.put(keys[i],i);
		}
		for (String s:st.keys())
			System.out.println(s+" "+st.get(s));
		System.out.println("-----");
		
		for (String s:st.levelOrder())
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
