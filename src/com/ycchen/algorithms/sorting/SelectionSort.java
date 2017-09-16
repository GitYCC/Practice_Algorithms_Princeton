package com.ycchen.algorithms.sorting;

import java.util.Comparator;

import com.ycchen.algorithms.sorting.SortTemplate;

public class SelectionSort extends SortTemplate{
	private SelectionSort() {}
	
	static public void sort(Comparable[] a) {
		int N = a.length;
		for (int i=0;i<N;i++) {
			int min = i;
			for (int j=i+1;j<N;j++) {
				if (less(a[j],a[min])) min=j;
			}
			exch(a,i,min);
		}
	}
	
	static public void sort(Object[] a,Comparator comparator) {
		
		int N = a.length;
		for (int i=0;i<N;i++) {
			int min = i;
			for (int j=i+1;j<N;j++) {
				if (less(a[j],a[min],comparator)) min=j;
			}
			exch(a,i,min);
		}
	}	

	
	static public void main(String[] args) throws Exception {
		Comparable[] array = new Comparable[] {1,2,4,5,3,6};
		sort(array);
		show(array);
		
		array = new Comparable[] {1,6,7,2,4,14,3,6,1,7,
				9,3,4,7,9,11,15,12,17,5,3,8,1,10,9,5,1,9,3,5,11,24,16,23,
				5,2,4,6,2,3,7,5,9,3,0,6,4,2,1,5,4,15,13,6,23,14,19,35,23};
		sort(array);
		show(array);
	}
}
