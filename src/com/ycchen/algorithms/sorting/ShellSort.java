package com.ycchen.algorithms.sorting;

import java.util.Comparator;

import com.ycchen.algorithms.sorting.SortTemplate;

public class ShellSort extends SortTemplate{
	private ShellSort()  {}
	static public void sort(Comparable[] a) {
		int N = a.length;
		
		int h = 1;
		while(h<N/3) h=3*h+1; // 1,4,13,40,121,364,1093,...
		
		// put smallest element in position to serve as sentinel
		int exchanges = 0;
		for (int i=N-1;i>0;i--) {
			if (less(a[i],a[i-1])) {
				exch(a,i,i-1);
				exchanges++;
			}
		}
		if (exchanges==0) return;
		
		// shell sort with half-exchanges
		while (h>=1) {
			for (int i=2;i<N;i++) {
				Comparable keep = a[i];
				int j=i;
				while (less(keep,a[j-h])&&(j-h>=0)) {
					a[j]=a[j-h];
					j -= h;
				}
				a[j]=keep;
			}
			h = h/3;
		}
	}
	
	static public void sort(Object[] a,Comparator comparator) {
		int N = a.length;
		
		int h = 1;
		while(h<N/3) h=3*h+1; // 1,4,13,40,121,364,1093,...
		
		// put smallest element in position to serve as sentinel
		int exchanges = 0;
		for (int i=N-1;i>0;i--) {
			if (less(a[i],a[i-1],comparator)) {
				exch(a,i,i-1);
				exchanges++;
			}
		}
		if (exchanges==0) return;
		
		// shell sort with half-exchanges
		while (h>=1) {
			for (int i=2;i<N;i++) {
				Object keep = a[i];
				int j=i;
				while (less(keep,a[j-h],comparator)&&(j-h>=0)) {
					a[j]=a[j-h];
					j -= h;
				}
				a[j]=keep;
			}
			h = h/3;
		}
	}	

	
	static public void main(String[] args) throws Exception {
		Comparable[] array = new Comparable[] {6,7,2,4,5,3,8,1,10,9};
		ShellSort.sort(array);
		ShellSort.show(array);
	}
}
