package com.ycchen.algorithms.sorting;

import java.util.Comparator;
import java.util.Random;

import com.ycchen.algorithms.sorting.SortTemplate;

public class QuickSort extends SortTemplate{
	private QuickSort() {}
	
	// *** Comparable as Argument *** //
	private static int partition(Comparable[] a,int lo,int hi) {
		Comparable v = a[lo];
		int i=lo,j=hi+1;
		while (true){
			while(less(a[++i],v)) if (i==hi) break;
			while(less(v,a[--j])) if (j==lo) break;
			if (i>=j) break;
			exch(a,i,j);
		}		
		exch(a,j,lo);
		return j;

	}
	
	private static void sort(Comparable[] a,int lo,int hi) {
		if (hi<=lo) return;
		int p = partition(a,lo,hi);
		sort(a,lo,p-1);
		sort(a,p+1,hi);
		assert isSorted(a,lo,hi);
	}
	
	static public void sort(Comparable[] a) {
		int N = a.length;
		shuffle(a);
		sort(a,0,N-1);
		assert isSorted(a,0,N-1);
	}
	
	static private void shuffle(Object[] a) {
		long currentTime = System.currentTimeMillis();
        Random r = new Random(currentTime);
		for (int i=0;i<a.length;i++) {
			int pick = r.nextInt(i+1);
			exch(a,i,pick);
		}
	}
	
	// *** Comparator as Argument *** //	
	private static int partition(Object[] a,Comparator comparator,int lo,int hi) {
		Object v = a[lo];
		int i=lo,j=hi+1;
		while (true){
			while(less(a[++i],v,comparator)) if (i==hi) break;
			while(less(v,a[--j],comparator)) if (j==lo) break;
			if (i>=j) break;
			exch(a,i,j);
		}		
		exch(a,j,lo);
		return j;

	}
	
	private static void sort(Object[] a,Comparator comparator,int lo,int hi) {
		if (hi<=lo) return;
		int p = partition(a,comparator,lo,hi);
		sort(a,comparator,lo,p-1);
		sort(a,comparator,p+1,hi);
		assert isSorted(a,comparator,lo,hi);
	}
	
	static public void sort(Object[] a,Comparator comparator) {
		int N = a.length;
		shuffle(a);
		sort(a,comparator,0,N-1);
		assert isSorted(a,comparator,0,N-1);
	}
	

	// sorted check
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
		Comparable[] array = new Comparable[] {1,6,7,2,4,14,3,6,1,7,
				9,3,4,7,9,11,15,12,17,5,3,8,1,10,9,5,1,9,3,5,11,24,16,23,
				5,2,4,6,2,3,7,5,9,3,0,6,4,2,1,5,4,15,13,6,23,14,19,35,23};
		
		sort(array);
		show(array);		
		System.out.println("isSorted: "+(isSorted(array)));
		
	}
}
