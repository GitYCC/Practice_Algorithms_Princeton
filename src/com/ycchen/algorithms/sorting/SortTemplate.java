package com.ycchen.algorithms.sorting;

import java.util.Comparator;

public class SortTemplate {
	protected SortTemplate() {}
	
	static public void sort(Comparable[] a) {}
	static public void sort(Object[] a,Comparator comparator) {}

	
	static protected boolean less(Comparable v,Comparable w) {
		return v.compareTo(w)<0;
	}
	static protected boolean less(Object v,Object w,Comparator comparator) {
		return comparator.compare(v,w)<0;
	}
	static protected void exch(Object[] a,int i,int j) {
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}
	static protected boolean isSorted(Comparable[] a) {
		for (int i=1;i<a.length;i++){
			if (less(a[i],a[i-1])) return false;
		}
		return true;
	}
	static protected boolean isSorted(Object[] a,Comparator comparator) {
		for (int i=1;i<a.length;i++){
			if (less(a[i],a[i-1],comparator)) return false;
		}
		return true;
	}	
	static protected void show(Comparable[] a) {
		System.out.print("[ ");
		for (int i=0;i<a.length;i++) {
			System.out.print(a[i].toString()+' ');
		}
		System.out.print("]\n");
	}
}
