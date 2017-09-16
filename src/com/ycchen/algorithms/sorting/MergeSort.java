package com.ycchen.algorithms.sorting;

import java.util.Comparator;

import com.ycchen.algorithms.sorting.SortTemplate;

public class MergeSort extends SortTemplate{
	private MergeSort() {}
	
	// *** Comparable as Argument *** //
	private static void merge(Comparable[] a,Comparable[] aux,int lo,int mid,int hi) {
		assert isSorted(a,lo,mid);
		assert isSorted(a,mid+1,hi);
		
		// copy to aux[]
		for (int k=lo;k<=hi;k++) aux[k]=a[k];
		// merge back to a[]
		int i = lo;
		int j = mid+1;
		for (int k=lo;k<=hi;k++) {
			if      (i > mid)             a[k] = aux[j++];
			else if (j > hi)              a[k] = aux[i++];
			else if (less(aux[j],aux[i])) a[k] = aux[j++];
			else                          a[k] = aux[i++];
			//takes from left sub-array if equal keys
		}
		
		assert isSorted(a,lo,hi);
	}
	
	private static void sort(Comparable[] a,Comparable[] aux,int lo,int hi) {
		if (hi<=lo) return;
		int mid = lo + (hi-lo)/2;
		sort(a,aux,lo,mid);
		sort(a,aux,mid+1,hi);
		merge(a,aux,lo,mid,hi);
	}
	
	static public void sort(Comparable[] a) {
		int N = a.length;
		Comparable[] aux = new Comparable[N];
		int lo = 0;
		int hi = N-1;
		sort(a,aux,lo,hi);
	}
	
	
	// *** Comparator as Argument *** //	
	private static void merge(Object[] a,Object[] aux,Comparator comparator,
			int lo,int mid,int hi) {
		assert isSorted(a,comparator,lo,mid);
		assert isSorted(a,comparator,mid+1,hi);
		
		// copy to aux[]
		for (int k=lo;k<=hi;k++) aux[k]=a[k];
		// merge back to a[]
		int i = lo;
		int j = mid+1;
		for (int k=lo;k<=hi;k++) {
			if      (i > mid)                         a[k] = aux[j++];
			else if (j > hi)                          a[k] = aux[i++];
			else if (less(aux[j],aux[i],comparator))  a[k] = aux[j++];
			else                                      a[k] = aux[i++]; 
			//takes from left sub-array if equal keys
		}
		
		assert isSorted(a,comparator,lo,hi);
	}
	
	private static void sort(Object[] a,Object[] aux,Comparator comparator,int lo,int hi) {
		if (hi<=lo) return;
		int mid = lo + (hi-lo)/2;
		sort(a,aux,comparator,lo,mid);
		sort(a,aux,comparator,mid+1,hi);
		merge(a,aux,comparator,lo,mid,hi);
	}
	
	static public void sort(Object[] a,Comparator comparator) {
		int N = a.length;
		Comparable[] aux = new Comparable[N];
		int lo = 0;
		int hi = N-1;
		sort(a,aux,comparator,lo,hi);
	}
	


	static protected boolean isSorted(Comparable[] a,int lo,int hi) {
		for (int i=lo+1;i<=hi;i++){
			if (less(a[i],a[i-1])) return false;
		}
		return true;
	}
	static protected boolean isSorted(Object[] a,Comparator comparator,int lo,int hi) {
		for (int i=lo+1;i<=hi;i++){
			if (less(a[i],a[i-1],comparator)) return false;
		}
		return true;
	}
	
	static public void main(String[] args) throws Exception {
		Comparable[] array = new Comparable[] {6,7,2,4,5,3,8,1,10,9};
		
		sort(array);
		show(array);		
		System.out.println("isSorted: "+(isSorted(array)));
		
			
	}
}
