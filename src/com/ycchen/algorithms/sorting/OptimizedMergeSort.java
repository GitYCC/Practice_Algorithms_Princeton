package com.ycchen.algorithms.sorting;

import java.util.Comparator;

import com.ycchen.algorithms.sorting.SortTemplate;

public class OptimizedMergeSort extends SortTemplate{
	private static final int CUTOFF = 7;
	
	private OptimizedMergeSort() {}
	
	// *** Comparable as Argument *** //
	private static void merge(Comparable[] src,Comparable[] dst,int lo,int mid,int hi) {
		assert isSorted(src,lo,mid);
		assert isSorted(src,mid+1,hi);
		
		// merge to dat[]
		int i = lo;
		int j = mid+1;
		for (int k=lo;k<=hi;k++) {
			if      (i > mid)             dst[k] = src[j++];
			else if (j > hi)              dst[k] = src[i++];
			else if (less(src[j],src[i])) dst[k] = src[j++];
			else                          dst[k] = src[i++];
			//for stable: takes from left sub-array if equal keys
		}
		
		assert isSorted(dst,lo,hi);
	}
	
	private static void sort(Comparable[] src,Comparable[] dst,int lo,int hi) {
		// if (hi<=lo) return;
		if (hi<=lo+CUTOFF) {
			InsertionSort.sort(dst,lo,hi);
			return;
		}
		int mid = lo + (hi-lo)/2;
		sort(dst,src,lo,mid);    // change position of dst and src
		sort(dst,src,mid+1,hi);  // change position of dst and src
	
		if (!less(src[mid+1],src[mid])) {
			// for (int i=lo;i<=hi;i++) dst[i]=src[i];
			// using System.arraycopy() is a bit faster than the above loop
			System.arraycopy(src, lo, dst, lo, hi-lo+1);
		}
		
		merge(src,dst,lo,mid,hi);
	}
	
	static public void sort(Comparable[] a) {
		Comparable[] aux = a.clone();
		sort(aux,a,0,a.length-1);
	}
	
	
	// *** Comparator as Argument *** //	
	private static void merge(Object[] src,Object[] dst,Comparator comparator,
			int lo,int mid,int hi) {
		assert isSorted(src,comparator,lo,mid);
		assert isSorted(src,comparator,mid+1,hi);
		
		// merge to dat[]
		int i = lo;
		int j = mid+1;
		for (int k=lo;k<=hi;k++) {
			if      (i > mid)             dst[k] = src[j++];
			else if (j > hi)              dst[k] = src[i++];
			else if (less(src[j],src[i],comparator)) dst[k] = src[j++];
			else                          dst[k] = src[i++];
			//for stable: takes from left sub-array if equal keys
		}
		
		assert isSorted(dst,comparator,lo,hi);
	}
	
	private static void sort(Object[] src,Object[] dst,Comparator comparator,int lo,int hi) {
		// if (hi<=lo) return;
		if (hi<=lo+CUTOFF) {
			InsertionSort.sort(dst,comparator,lo,hi);
			return;
		}
		int mid = lo + (hi-lo)/2;
		sort(dst,src,comparator,lo,mid);    // change position of dst and src
		sort(dst,src,comparator,mid+1,hi);  // change position of dst and src
	
		if (!less(src[mid+1],src[mid],comparator)) {
			// for (int i=lo;i<=hi;i++) dst[i]=src[i];
			// using System.arraycopy() is a bit faster than the above loop
			System.arraycopy(src, lo, dst, lo, hi-lo+1);
		}
		
		merge(src,dst,comparator,lo,mid,hi);
	}
	
	static public void sort(Object[] a,Comparator comparator) {
		Object[] aux = a.clone();
		sort(aux,a,comparator,0,a.length-1);
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
		Comparable[] array = new Comparable[] {1,6,7,2,4,14,3,6,1,7,9,3,4,7,9,11,15,12,17,5,3,8,1,10,9};
		
		sort(array);
		show(array);		
		System.out.println("isSorted: "+(isSorted(array)));
		
			
	}
}
