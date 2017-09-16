package com.ycchen.algorithms.sorting;

import java.util.Comparator;

import com.ycchen.algorithms.sorting.SortTemplate;

public class InsertionSort extends SortTemplate{
	private InsertionSort() {}
	
	static public void sort(Comparable[] a) {
		int N = a.length;
		int lo = 0;
		int hi = N-1;
		sort(a,lo,hi);
	}
	static public void sort(Comparable[] a,Comparator comparator) {
		int N = a.length;
		int lo = 0;
		int hi = N-1;
		sort(a,comparator,lo,hi);
	}
	
	static public void sort(Comparable[] a,int lo,int hi) {
		// put smallest element in position to serve as sentinel
		int exchanges = 0;
		for (int i=hi;i>lo;i--) {
			if (less(a[i],a[i-1])) {
				exch(a,i,i-1);
				exchanges++;
			}
		}
		if (exchanges==0) return;
		
		// insertion sort with half-exchanges
		for (int i=lo+1;i<=hi;i++) {
			Comparable keep = a[i];
			int j=i;
			while (less(keep,a[j-1])) {
				a[j]=a[j-1];
				j--;
			}
			a[j]=keep;
		}
	}
	
	static public void sort(Object[] a,Comparator comparator,int lo,int hi) {
		// put smallest element in position to serve as sentinel
		int exchanges = 0;
		for (int i=hi;i>lo;i--) {
			if (less(a[i],a[i-1],comparator)) {
				exch(a,i,i-1);
				exchanges++;
			}
		}
		if (exchanges==0) return;
		
		// insertion sort with half-exchanges
		for (int i=lo+1;i<=hi;i++) {
			Object keep = a[i];
			int j=i;
			while (less(keep,a[j-1],comparator)) {
				a[j]=a[j-1];
				j--;
			}
			a[j]=keep;
		}
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
		
		array = new Comparable[] {6,7,2,4,5,3,8,1,10,9};
		sort(array,0,5);
		show(array);			
		System.out.println("isAllSorted: "+(isSorted(array)));
		System.out.println("isPartialSorted: "+(isSorted(array,0,5)));
			
	}
}
