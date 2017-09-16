package com.ycchen.algorithms.strings;

public class LSD {

	private LSD() {}
	
	public static void sort(String[] a, int w) {
		int n = a.length;
		int R = 256; //extend ASCII alphabet size
		String[] aux = new String[n];
		for (int d=w-1; d>=0; d--) {
			// sort by key-indexed counting on dth character
			int[] count = new int[R+1];
			// count frequency
			for (int i=0; i<n; i++) {
				count[a[i].charAt(d)+1]++;
			}
			// cumulate
			for (int r=1; r<R+1; r++) {
				count[r] += count[r-1];
			}
			// move data
			for (int i=0; i<n; i++) {
				aux[count[a[i].charAt(d)]++]=a[i];
			}
			// copy
			for (int i=0; i<n; i++) {
				a[i] = aux[i];
			}
		}
	}
	
	public static void sort(int[] a) {
		final int BITS = 32; // each int is 32 bits
		final int BITS_PER_BYTE = 8;
		final int R = 1 << BITS_PER_BYTE; // each bytes is between 0 and 255
		final int MASK = R-1;
		final int w = BITS / BITS_PER_BYTE; // each int is 4 bytes
		
		int n=a.length;
		int[] aux = new int[n];
		
		for (int d=0; d<w; d++) {
			int[] count = new int[R+1];
			// count frequency
			for (int i=0; i<n; i++) {
				int c = (a[i]>>BITS_PER_BYTE*d) & MASK;
				count[c+1]++;
			}
			// cumulate
			for (int r=1; r<R+1; r++) {
				count[r] += count[r-1];
			}
			// final byte include information of positive or negative
			// so, 0x80-0xFF comes before 0x00-0x7F
			if (d==w-1) {
				int shift_up = count[R]-count[R/2];
				int shift_down = count[R/2];
				for (int r=0; r<R/2; r++) {
					count[r] += shift_up;
				}
				for (int r=R/2; r<R; r++) {
					count[r] -= shift_down;
				}
			}
			// move data
			for (int i=0; i<n; i++) {
				int c = (a[i]>>BITS_PER_BYTE*d) & MASK;
				aux[count[c]++] = a[i];
			}
			// copy
			for (int i=0; i<n; i++) {
				a[i]=aux[i];
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println("'a' is equal to "+(int)'a');
		System.out.println("'z' is equal to "+(int)'z');
		System.out.println("'A' is equal to "+(int)'A');
		System.out.println("'Z' is equal to "+(int)'Z');		
		System.out.println("0 is equal to '"+(char)0+"'");
		System.out.println("255 is equal to '"+(char)255+"'");	
		System.out.println("(int)1<<8 is "+((int)1<<8));
		System.out.println(Integer.MIN_VALUE+" = "+Integer.toHexString(Integer.MIN_VALUE));
		System.out.println((Integer.MIN_VALUE+1)+" = "+Integer.toHexString(Integer.MIN_VALUE+1));
		System.out.println(0+" = "+Integer.toHexString(0));
		System.out.println(Integer.MAX_VALUE+" = "+Integer.toHexString(Integer.MAX_VALUE));
		
		String[] list = {"shirt","apple","funny","shoot","yummy"};
		LSD.sort(list, 5);
		for (int i=0;i<list.length;i++)
			System.out.print(list[i]+" ");
		System.out.println("");
		int[] num_list = {3,28,13,-11,-22};
		LSD.sort(num_list);
		for (int i=0;i<num_list.length;i++)
			System.out.print(num_list[i]+" ");
		System.out.println("");
	}

}
