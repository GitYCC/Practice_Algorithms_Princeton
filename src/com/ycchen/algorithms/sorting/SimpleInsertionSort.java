package com.ycchen.algorithms.sorting;

import java.util.Comparator;

import com.ycchen.algorithms.sorting.SortTemplate;

public class SimpleInsertionSort extends SortTemplate{
	
	static public void sort(Comparable[] a) {
		int N = a.length;
		for (int i=1;i<N;i++) {
			for (int j=i;j>0;j--) {
				if (less(a[j],a[j-1])) exch(a,j,j-1);
			}
		}
	}
	
	static public void sort(Object[] a,Comparator comparator) {
		int N = a.length;
		for (int i=1;i<N;i++) {
			for (int j=i;j>0;j--) {
				if (less(a[j],a[j-1],comparator)) exch(a,j,j-1);
			}
		}
	}	

	
	static public void main(String[] args) throws Exception {
		Comparable[] array = new Comparable[] {6,7,2,4,5,3,8,1,10,9};
		SimpleInsertionSort.sort(array);
		SimpleInsertionSort.show(array);
	}
}
