package com.ycchen.algorithms.strings;

public class KMP {
	private final int R;
	private int[][] dfa;
	private String pat;
	
	public KMP(String pattern) {
		R = 256;
		pat = pattern;
		int M = pat.length();
		dfa = new int[R][M];
		dfa[pat.charAt(0)][0] = 1;
		for (int m=1, X=0; m<M; m++) {
			for (int r=0; r<R; r++) {
				dfa[r][m]=dfa[r][X];
			}
			dfa[pat.charAt(m)][m] = m+1;
			X = dfa[pat.charAt(m)][X];
		}
	}
	
	public int search(String txt) {
		for (int t=0, p=0; t<txt.length(); t++) {
			char c = txt.charAt(t);
			p = dfa[c][p];
			if (p==pat.length()) return t-(pat.length()-1);
		}
		return txt.length();
	}
	
	public static void main(String[] args) {
		String txt = "abacadabrabracabracadabrabrabracad";
		String pattern = "abracadabra";
		KMP kmp = new KMP(pattern);
		int index = kmp.search(txt);

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
