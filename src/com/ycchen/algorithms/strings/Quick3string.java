package com.ycchen.algorithms.strings;

import java.util.Random;

public class Quick3string {
	private static final int CUTOFF = 15; // cutoff to insertion sort

	private Quick3string() {}
	
	public static void sort(String[] a) {
		shuffle(a);
		sort(a,0,a.length-1,0);
	}
	
	private static void shuffle(String[] a) {
		long currentTime = System.currentTimeMillis();
        Random r = new Random(currentTime);
		for (int i=0;i<a.length;i++) {
			int pick = r.nextInt(i+1);
			exch(a,i,pick);
		}
	}	
	
	private static void exch(String[] a,int i,int j) {
		String tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}
	
	private static void sort(String[] a,int lo,int hi,int d) {
		if (lo>=hi) return;
		if (hi-lo<=CUTOFF) {
			insertion(a,lo,hi,d);
			return;
		}
		
		int lt = lo, gt = hi;
		int v = charAt(a[lo],d);
		int i = lo+1;
		while (i<=gt) {
			int t = charAt(a[i],d);
			if (v > t) exch(a,i++,lt++);
			else if (v < t) exch(a,i,gt--);
			else i++;
		}
		sort(a,lo,lt-1,d);
		if (v>=0) sort(a,lt,gt,d+1);
		sort(a,gt+1,hi,d);
	}
	
	private static void insertion(String[] a,int lo,int hi,int d) {
		for (int i=lo; i<=hi; i++) {
			for (int j=i; j>lo; j--) {
				if (less(a,j,j-1,d)) exch(a,j,j-1);
			}
		}
	}
	
	private static boolean less(String[] a,int i,int j,int d) {
		int min_len = Math.min(a[i].length(), a[j].length());
		for (int k=d; k<min_len; k++) {
			if (charAt(a[i],k) < charAt(a[j],k)) return true;
			else if (charAt(a[i],k) > charAt(a[j],k)) return false;
		}
		return a[i].length() < a[j].length();
	}
	
	private static int charAt(String s,int d) {
		if (s.length()==d) return -1;
		return s.charAt(d);
	}
	
	public static void main(String[] args) {
		String[] list = {"shirt","apple","funny","shoot","yummy","banana","sky","skyfall",
				"she","sells","seashells","by","the","sea","shore","shells","she","sells","surely","seashells"};
		Quick3string.sort(list);
		for (int i=0;i<list.length;i++)
			System.out.print(list[i]+" ");
		System.out.println("");

	}

}
