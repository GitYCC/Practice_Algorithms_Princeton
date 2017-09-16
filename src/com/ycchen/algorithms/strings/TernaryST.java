package com.ycchen.algorithms.strings;

import com.ycchen.algorithms.fundamentals.ArrayQueue;

public class TernaryST<V> {
	private int n;  // size
	private Node<V> root;
	
	private static class Node<V> {
		private char c;
		private Node<V> left,mid,right;
		private V val;
	}
	
	public TernaryST() {}
	
	public int size() { return n; }
	
	public boolean contains(String key) {
		return get(key) != null;
	}
	
	public V get(String key) {
		if (key == null) throw new IllegalArgumentException("calls get() with null argument");
		if (key.length()==0) throw new IllegalArgumentException("key must have length >= 1");
		Node<V> x = get(root,key,0);
		if (x == null) return null;
		return x.val;
	}
	
	private Node<V> get(Node<V> x,String key,int d) {
		if (x==null) return null;
		char c = key.charAt(d);
		if      (c < x.c)             return get(x.left,key,d);
		else if (c > x.c)             return get(x.right,key,d);
		else if (d < key.length()-1)  return get(x.mid,key,d+1);
		else                          return x;
 	}
	
	public void put(String key,V val) {
		if (key == null) throw new IllegalArgumentException("calls get() with null argument");
		if (key.length()==0) throw new IllegalArgumentException("key must have length >= 1");
		if (!contains(key)) n++;
		root = put(root,key,val,0);
	}
	
	private Node<V> put(Node<V> x,String key,V val,int d) {
		char c = key.charAt(d);		
		if (x==null) {
			x = new Node<V>();
			x.c = c;
		}
		if      (c < x.c)             x.left = put(x.left,key,val,d);
		else if (c > x.c)             x.right= put(x.right,key,val,d);
		else if (d < key.length()-1)  x.mid  = put(x.mid,key,val,d+1);
		else                          x.val = val;
		return x;		
	}
	
	public Iterable<String> keys() {
		StringBuilder prefix = new StringBuilder();
		ArrayQueue<String> queue = new ArrayQueue<>();
		collect(root,prefix,queue);
		return queue;
	}
	
	public Iterable<String> keysWithPrefix(String prefix) {
		if (prefix==null) throw new IllegalArgumentException("calls keysWithPrefix() with null argument");
		Node<V> x = get(root,prefix,0);
		StringBuilder sb_prefix = new StringBuilder(prefix);
		ArrayQueue<String> queue = new ArrayQueue<>();
		if (x==null) return queue;
		if (x.val!=null) queue.enqueue(prefix);
		collect(x.mid,sb_prefix,queue); // go from next middle node
		return queue;
	}
	
	private void collect(Node<V> x,StringBuilder prefix,ArrayQueue<String> queue) {
		// the node only put the "character" into prefix to next middle node
		// next left and right node is process of searching
		if (x==null) return;
		collect(x.left,prefix,queue);
		if (x.val!=null) queue.enqueue(prefix.toString()+x.c);
		collect(x.mid,prefix.append(x.c),queue);
		prefix.deleteCharAt(prefix.length()-1);
		collect(x.right,prefix,queue);
	}
	
	public String longestPrefixOf (String query) {
		if (query==null) throw new IllegalArgumentException("calls longestPrefixOf() with null argument");
		if (query.length()==0) return null;
		int length = 0, i=0;
		Node<V> x = root;
		while (x != null && i < query.length()) {
			char c = query.charAt(i);
			if (c < x.c) x=x.left;
			else if (c > x.c) x=x.right;
			else {
				i++;
				if (x.val!=null) length = i;
				x=x.mid;
			}
		}
		return query.substring(0, length);
	}
	
	public static void main(String[] args) {
		TernaryST<Integer> st = new TernaryST<Integer>();
		st.put("she", 0); st.put("sells", 1); st.put("sea", 2);
		st.put("shells", 3); st.put("by", 4); st.put("the", 5);
		st.put("sea", 6); st.put("shore", 7);
		
		System.out.println("longestPrefixOf('shellsort'):");
		System.out.println(st.longestPrefixOf("shellsort"));
		System.out.println("");
		
		System.out.println("keys():");
		for (String s: st.keys()) {
			System.out.println(s+" "+st.get(s));
		}
		System.out.println("");		
		
		System.out.println("keysWithPrefix('shor'):");
		for (String s: st.keysWithPrefix("shor")) {
			System.out.println(s+" "+st.get(s));
		}
		System.out.println("");
		
		System.out.println("keysWithPrefix('sh'):");
		for (String s: st.keysWithPrefix("sh")) {
			System.out.println(s+" "+st.get(s));
		}
		System.out.println("");
	}
		
}
