package com.ycchen.algorithms.strings;

public class BoyerMoore {
	private final int R;
	private int[] right;
	
	private String pat;
	
	public BoyerMoore(String pattern) {
		this.R = 256;
		this.pat = pattern;
		
		// position of "rightmost" occurrence of c in the pattern
		right = new int[this.R];
		for (int i=0; i<this.R; i++) {
			right[i] = -1;
		}
		for (int m=0; m<this.pat.length(); m++) {
			right[this.pat.charAt(m)] = m;
		}
	}
	
	public int search(String txt) {
		int N = txt.length();
		int M = pat.length();
		int skip;
		for (int i=0; i<N-M; i+=skip) {
			skip = 0;
			for (int j=M-1; j>=0; j--) {
				int txt_posi = i+j;
				if (pat.charAt(j)!=txt.charAt(txt_posi)) {
					skip = Math.max(1, j-right[txt.charAt(txt_posi)]);
					break;
				}
			}
			if (skip==0) return i;
		}
		return N;
	}
	
	public static void main(String[] args) {
		String txt = "abacadabrabracabracadabrabrabracad";
		String pattern = "abracadabra";
		BoyerMoore bm = new BoyerMoore(pattern);
		int index = bm.search(txt);

		if (index!=txt.length()) {
			System.out.println("index at "+index	);
			System.out.println(txt);
			String space = "";
			for (int i=0; i<index; i++) {
				space += " ";
			}
			System.out.println(space+pattern);
			
		}

	}

}
