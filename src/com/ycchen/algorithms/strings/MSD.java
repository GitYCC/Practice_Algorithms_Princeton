package com.ycchen.algorithms.strings;

public class MSD {
    private static final int BITS_PER_BYTE =   8;
    private static final int BITS_PER_INT  =  32;   // each Java int is 32 bits 
    private static final int R             = 256;   // extended ASCII alphabet size
    private static final int CUTOFF        =  15;   // cutoff to insertion sort
    
	private MSD () {}
	
	public static void sort(String[] a) {
		int n = a.length;
		String[] aux = new String[n];
		sort(a,aux,0,n-1,0);
	}
	
	private static void sort(String[] a,String[] aux,int lo,int hi,int d) {
		if (lo >= hi) return;
		if (hi-lo <= CUTOFF) {
			insertion(a,lo,hi,d);
			return;
		}
        // compute frequency counts
        int[] count = new int[R+2]; // include sentinel -1
		for (int i=lo; i<=hi; i++) {
			int c = charAt(a[i],d);
			count[c+2]++;
		}
		// transform counts to indicies
		for (int r=0; r<R+1; r++) {
			count[r+1] += count[r];
		}
		// distribute
		for (int i=lo; i<=hi; i++) {
			int c = charAt(a[i],d);
			aux[count[c+1]++] = a[i];
		}
		// copy back
		for (int i=lo; i<=hi; i++) {
			a[i] = aux[i-lo];
		}
		// sort subset (excluding sentinel -1)
		for (int r=0; r<R+1; r++) {
			sort(a,aux,lo+count[r],lo+count[r+1]-1,d+1);
		}
	}
	
	private static int charAt(String s,int d) {
		if (s.length()==d) return -1;
		return s.charAt(d);
	}
	
	private static void insertion(String[] a,int lo,int hi,int d) {
		for (int i=lo; i<=hi; i++) {
			for (int j=i; j>lo; j--) {
				if (less(a[j],a[j-1],d)) exch(a,j,j-1);
			}
		}
	}
	
	private static void exch(String[] a,int i,int j) {
		String tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}
	
	private static boolean less(String v,String w,int d) {
		for (int i=d; i<Math.min(v.length(), w.length());i++) {
			if (v.charAt(i) > w.charAt(i)) return false;
			if (v.charAt(i) < w.charAt(i)) return true;
		}
		return v.length() < w.length();
	}

	
	public static void main(String[] args) {
		String[] list = {"shirt","apple","funny","shoot","yummy","banana","sky","skyfall",
				"she","sells","seashells","by","the","sea","shore","shells","she","sells","surely","seashells"};
		MSD.sort(list);
		for (int i=0;i<list.length;i++)
			System.out.print(list[i]+" ");
		System.out.println("");
		
	}

}
