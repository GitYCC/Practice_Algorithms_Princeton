package com.ycchen.algorithms.searching;

import com.ycchen.algorithms.fundamentals.ArrayQueue;
import com.ycchen.algorithms.searching.SequentialSearchST.Node;

@SuppressWarnings("unchecked")
public class RedBlackBST <K extends Comparable<K>,V>
			implements I_OrderedSymbolTable<K,V>{
	
	private static final boolean RED = true;
	private static final boolean BLACK = false;
	private Node root;

	private class Node {
		private K key;
		private V val;
		private Node left,right;
		private boolean color;
		protected int size;
		public Node(K key,V val,boolean color,int size) {
			this.key = key;
			this.val = val;
			this.color=color;
			this.size = size;
		}
	}
	
	public RedBlackBST() {	}
	
	/*
	 * Node helper methods
	 */
	private boolean isRed(Node x) {
		if (x==null) return false;
		return x.color==RED;
	}
	private int size(Node x) {
		if (x==null) return 0;
		return x.size;
	}
	@Override
	public int size(){
		return size(root);
	}
	@Override
	public boolean isEmpty(){
		return size()==0;
	}
	
	/*
	 * Standard BST search
	 */
	@Override
	public V get(K key){
		if (key==null) throw new IllegalArgumentException("first argument to get() is null");
		return get(root,key);
	}
	private V get(Node x,K key) {
		while (x!=null) {
			int cmp = x.key.compareTo(key);
			if      (cmp>0) x = x.left;
			else if (cmp<0) x = x.right;
			else            return x.val;
		}	
		return null;
	}
	@Override
	public boolean contains(K key){
		return get(key)!=null;
	}
	
	/*
	 * Red-black tree helper functions
	 */
	private Node rotateRight(Node x){
		//     x              b
		//  _b   e    --->  a   _x
		//  a c d f            c   e
		//                        d f
		assert (x!=null) && isRed(x.left);
		Node b = x.left;
		x.left = b.right;
		b.right = x;
		b.color = x.color;
		x.color = RED;
		x.size = 1+size(x.left)+size(x.right);
		b.size = 1+size(b.left)+size(b.right);
		return b;
	}
	private Node rotateLeft(Node x){
		//	   x                  e
		//   b  _e    --->    _x    f
		//  a c d f          b   d
		//                  a c   
		assert (x!=null) && isRed(x.right);
		Node e = x.right;
		x.right = e.left;
		e.left = x;
		e.color = x.color;
		x.color = RED;
		x.size = 1+size(x.left)+size(x.right);
		e.size = 1+size(e.left)+size(e.right);
		return e;
	}
	private void flipColors(Node x){
		assert (x!=null) && (x.left!=null) && (x.right!=null);
		x.left.color = !x.left.color;
		x.right.color= !x.right.color;
		x.color      = !x.color;
	}
	private Node moveRedLeft(Node x){
		//	  _x         x           x              _d
		//   b    e -> _b   _e  -> _b   _d    ->    x   e
		//  a c _d f   a c _d f    a c p  _e     _b  p q f
	    //                 p q            q f    a c
		
		assert (x!=null);
		assert isRed(x) && !isRed(x.left) && !isRed(x.left.left);
		flipColors(x);
		if (isRed(x.right.left)){
			x.right=rotateRight(x.right);
			x = rotateLeft(x);
			flipColors(x);
		}
		return x;
	}
	private Node moveRedRight(Node x){
		//	  _x         x           b      
		//   b   e -> _b   _e  ->  a   x    
		// _a c d f  _a c  d f        c _e    
		//                              d f
		assert (x!=null);
		assert isRed(x) && !isRed(x.right) && !isRed(x.right.left);
		flipColors(x);
		if (isRed(x.left.left)){
			x = rotateRight(x);
			flipColors(x);
		}
		return x;
	}
	private Node balance(Node x){
		assert(x!=null);
		if (isRed(x.right)) x=rotateLeft(x);
		if (isRed(x.left) && isRed(x.left.left)) x=rotateRight(x);
		if (isRed(x.left) && isRed(x.right)) flipColors(x);
		return x;
	}
	
	/*
	 *  Red-black tree insertion 
	 */
	@Override
	public void put(K key, V val){
		if (key==null) throw new IllegalArgumentException("first argument to put() is null");
		if (val==null) {
			delete(key);
			return;
		}
		root = put(root,key,val);
		root.color = BLACK; //root is always black 
	}
	private Node put(Node x,K key,V val) {
		if (x==null) return new Node(key,val,RED,1);
		int cmp = x.key.compareTo(key);
		if      (cmp>0) x.left=put(x.left,key,val);
		else if (cmp<0) x.right=put(x.right,key,val);
		else            x.val=val;
		x.size = 1 + size(x.left) + size(x.right);
		x = balance(x);
		return x;
	}
	
	/*
	 *  Red-black tree deletion 
	 */
	@Override
	public void deleteMin(){
		if (isEmpty()) return;
		
		// if both children of root are black,
		// set root to red (temperate)
		if (!isRed(root.left) && !isRed(root.right)) root.color=RED;
		
		root = deleteMin(root);
		
		if (!isEmpty()) root.color=BLACK; //root is always black
	}
	private Node deleteMin(Node x){
		if (x.left==null) return null; // Min. is found , kill it
		
		if (!isRed(x.left) && !isRed(x.left.left)) {
			x = moveRedLeft(x);
		}
		
		x.left = deleteMin(x.left);
		x = balance(x);
		return x;
	}
	@Override
	public void deleteMax(){
		if (isEmpty()) return;
		
		// if both children of root are black,
		// set root to red (temperate)
		if (!isRed(root.left) && !isRed(root.right)) root.color=RED;		
		
		root = deleteMax(root);
		
		if (!isEmpty()) root.color=BLACK; //root is always black
	}
	private Node deleteMax(Node x){
		if (isRed(x.left)) return rotateRight(x);
		
		if (x.right==null) return null;
		
		if (!isRed(x.right) && !isRed(x.right.left)) {
			x = moveRedRight(x);
		}
		
		x.right = deleteMin(x.right);
		x = balance(x);
		return x;
	}
	@Override
	public void delete(K key){
		if (key==null) throw new IllegalArgumentException("first argument to delete() is null");
		if (!contains(key)) return;
		
		// if both children of root are black,
		// set root to red (temperate)
		if (!isRed(root.left) && !isRed(root.right)) root.color=RED;		
		
		root = delete(root,key);
		if (!isEmpty()) root.color=BLACK; //root is always black
	}
	private Node delete(Node x,K key) { // @@ @@ @@ 
		
		int cmp = x.key.compareTo(key);
		
		if      (cmp>0) {
			if (!isRed(x.left) && !isRed(x.left.left)) x=moveRedLeft(x);
			x.left = delete(x.left,key);
		}
		else {
			if (isRed(x.left)) x=rotateRight(x);
			if (x.key.compareTo(key)==0 && (x.right==null)) return null;
			if (!isRed(x.right) && !isRed(x.right.left)) {
				x=moveRedRight(x);
			}
			if (x.key.compareTo(key)==0) {
				Node m = min(x.right);
				x.key = m.key;
				x.val = m.val;
				x.right = deleteMin(x.right);
			} else {
				x.right = delete(x.right,key);
			}
		}
		x = balance(x);
		return x;
	}

	/*
	 *  Ordered symbol table methods
	 */
	
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

	/*
	 *  Utility functions
	 */
	
	public int height() {
		return height(root);
	}
	// a 1-node tree has height 0
	private int height(Node x) { //@@
		if (x==null) return -1;
		return 1+Math.max(height(x.left),height(x.left));
	}
	
	/*
	 *  Range count and range search
	 */
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
	
	
	public static void main(String[] args) {
		BinarySearchTree<String,Integer> st = new BinarySearchTree<>();
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
