package com.ycchen.algorithms.sorting;

import java.util.Comparator;
import java.util.Random;

import com.ycchen.algorithms.sorting.SortTemplate;

public class OptimizedQuickSort extends SortTemplate{
	private static final int INSERTION_SORT_CUTOFF = 8;
	private static final int MEDIAN_OF_3_CUTOFF = 40;
	
	private OptimizedQuickSort() {}
	
	// *** Comparable as Argument *** //
	static public void sort(Comparable[] a) {
		int N = a.length;
		sort(a,0,N-1);
		assert isSorted(a,0,N-1);
	}
	
	private static void sort(Comparable[] a,int lo,int hi) {
		int n = hi-lo+1;
		
		// cutoff to insertion sort
		if (n<=INSERTION_SORT_CUTOFF) {
			InsertionSort.sort(a,lo,hi);
			return;
		// use median-of-3 as partitioning element
		} else if (n<=MEDIAN_OF_3_CUTOFF) {
			int mid = lo + n/2;
			int median = medianOf3(a,lo,mid,hi);
			exch(a,lo,median);
		
		// use Tukey ninther as partitioning element
		} else {
			int mid = lo + n/2;
			int itv= n/8;
			int m1 = medianOf3(a,lo,lo+itv,lo+2*itv);
			int m2 = medianOf3(a,mid-itv,mid,mid+itv);
			int m3 = medianOf3(a,hi-2*itv,hi-itv,hi);
			int median = medianOf3(a,m1,m2,m3);
			exch(a,lo,median);
		}
		
		// Bentley-McIlroy 3-way partitioning
		Comparable v = a[lo];
		int p=lo,q=hi+1,i=lo,j=hi+1;
		while (true) {
			while (less(a[++i],v)) 
				if (i==hi) break;
			while (less(v,a[--j])) 
				if (j==lo) break;
			
			if ((i==j)&&(eq(a[i],v))) {
				exch(a,++p,i);
			}
			
			if (i>=j) 	break;
			
			exch(a,i,j); //change first
			if (eq(a[i],v)) exch(a,++p,i); //and then check eqaul
			if (eq(a[j],v)) exch(a,--q,j);
		}
		i = j+1; //@@
		for (int k=lo;k<=p;k++) exch(a,j--,k);
		for (int k=hi;k>=q;k--) exch(a,i++,k);
		
		sort(a,lo,j);
		sort(a,i,hi);
		assert isSorted(a,lo,hi);
	}
	
	private static int medianOf3 (Comparable[] a,int i,int j,int k) {
		if (less(a[i],a[j])) {
			if (less(a[j],a[k])) {
				return j;
			} else {
				if (less(a[i],a[k])){
					return k;
				} else {
					return i;
				}
			}
		} else {
			if (less(a[j],a[k])) {
				if (less(a[i],a[k])){
					return i;
				} else {
					return k;
				}
			} else {
				return j;
			}
		}
	}

	// *** Comparator as Argument *** //	
	static public void sort(Object[] a,Comparator comparator) {
		int N = a.length;
		sort(a,comparator,0,N-1);
		assert isSorted(a,comparator,0,N-1);
	}
	
	private static void sort(Object[] a,Comparator comparator,int lo,int hi) {
		int n = hi-lo+1;
		
		// cutoff to insertion sort
		if (n<=INSERTION_SORT_CUTOFF) {
			InsertionSort.sort(a,comparator,lo,hi);
			return;
		// use median-of-3 as partitioning element
		} else if (n<=MEDIAN_OF_3_CUTOFF) {
			int mid = lo + n/2;
			int median = medianOf3(a,comparator,lo,mid,hi);
			exch(a,lo,median);
		
		// use Tukey ninther as partitioning element
		} else {
			int mid = lo + n/2;
			int itv= n/8;
			int m1 = medianOf3(a,comparator,lo,lo+itv,lo+2*itv);
			int m2 = medianOf3(a,comparator,mid-itv,mid,mid+itv);
			int m3 = medianOf3(a,comparator,hi-2*itv,hi-itv,hi);
			int median = medianOf3(a,comparator,m1,m2,m3);
			exch(a,lo,median);
		}
		
		// Bentley-McIlroy 3-way partitioning
		Object v = a[lo];
		int p=lo,q=hi+1,i=lo,j=hi+1;
		while (true) {
			while (less(a[++i],v,comparator)) 
				if (i==hi) break;
			while (less(v,a[--j],comparator)) 
				if (j==lo) break;
			
			if ((i==j)&&(eq(a[i],v,comparator))) {
				exch(a,++p,i);
			}
			
			if (i>=j) 	break;
			
			exch(a,i,j); //change first
			if (eq(a[i],v,comparator)) exch(a,++p,i); //and then check eqaul
			if (eq(a[j],v,comparator)) exch(a,--q,j);
		}
		i = j+1; //@@
		for (int k=lo;k<=p;k++) exch(a,j--,k);
		for (int k=hi;k>=q;k--) exch(a,i++,k);
		
		sort(a,comparator,lo,j);
		sort(a,comparator,i,hi);
		assert isSorted(a,comparator,lo,hi);
	}
	
	private static int medianOf3 (Object[] a,Comparator comparator,int i,int j,int k) {
		if (less(a[i],a[j],comparator)) {
			if (less(a[j],a[k],comparator)) {
				return j;
			} else {
				if (less(a[i],a[k],comparator)){
					return k;
				} else {
					return i;
				}
			}
		} else {
			if (less(a[j],a[k],comparator)) {
				if (less(a[i],a[k],comparator)){
					return i;
				} else {
					return k;
				}
			} else {
				return j;
			}
		}
	}
	
	// equal check
	static protected boolean eq(Comparable v,Comparable w) {
		return v.compareTo(w)==0;
	}
	static protected boolean eq(Object v,Object w,Comparator comparator) {
		return comparator.compare(v,w)==0;
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
