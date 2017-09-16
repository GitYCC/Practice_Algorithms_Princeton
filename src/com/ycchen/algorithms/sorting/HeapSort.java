package com.ycchen.algorithms.sorting;

import java.util.Comparator;

import com.ycchen.algorithms.sorting.SortTemplate;

public class HeapSort extends SortTemplate{
	private HeapSort() {}
	
	// override exchange method
	static protected void exch(Object[] a,int i,int j) {
		Object swap = a[i-1];
		a[i-1] = a[j-1];
		a[j-1] = swap;
	}
	
	
	// *** Comparable as Argument *** //
	
	public static void sort(Comparable[] a) {
		int n = a.length;
		for (int k=n/2;k>=1;k--) sink(a,k,n);
		while (n>1) {
			exch(a,n,1);
			n--;
			sink(a,1,n);
		}
	}
	
	
	private static void sink(Comparable[] a,int k,int n) {
		int candidate;
		while (k*2<=n) { //stop if it has no left child
			candidate=k*2;
			if ((candidate+1<=n)&&(less(a[candidate-1],a[candidate]))){
				candidate++;
			}
			if (less(a[candidate-1],a[k-1])) break;
			exch(a,candidate,k);
			k = candidate;
		}
	}
	
	
	// *** Comparator as Argument *** //	
	
	public static void sort(Object[] a,Comparator comparator) {
		int n = a.length;
		for (int k=n/2;k>=1;k--) sink(a,comparator,k,n);
		while (n>1) {
			exch(a,n,1);
			n--;
			sink(a,comparator,1,n);
		}
	}
	
	
	private static void sink(Object[] a,Comparator comparator,int k,int n) {
		int candidate;
		while (k*2<=n) { //stop if it has no left child
			candidate=k*2;
			if ((candidate+1<=n)&&(less(a[candidate-1],a[candidate],comparator))){
				candidate++;
			}
			if (less(a[candidate-1],a[k-1],comparator)) break;
			exch(a,candidate,k);
			k = candidate;
		}
	}	
	
	
	static public void main(String[] args) throws Exception {
		Comparable[] array = new Comparable[] {6,7,2,4,5,3,8,1,10,9};
		
		sort(array);
		show(array);		
		System.out.println("isSorted: "+(isSorted(array)));
		
			
	}
}
