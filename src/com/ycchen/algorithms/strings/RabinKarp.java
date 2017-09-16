package com.ycchen.algorithms.strings;

import java.math.BigInteger;
import java.util.Random;

public class RabinKarp {
	private String pat;
	private final int R;
	private final long Q;
	private long patHash;
	private int M; // pattern length
	private long RM; // R^(M-1)%Q
	
	public RabinKarp(String pattern) {
		pat = pattern;
		R = 256;
		M = pat.length();
		Q = longRandomPrime();
		
		patHash = hash(pat,M);
		// pre-compute R^(M-1)%q for use in removing leading digit
		RM = 1;
		for (int i=1; i<=M-1; i++) {
			RM = (RM*R)%Q;
		}
	}
	
	private long hash(String key, int m) {
		long h = 0;
		for (int i=0; i<m; i++) {
			h = (h*R+key.charAt(i))%Q;
		}
		return h;
	}
	
	// Las Vegas version
	private boolean check(String txt,int i) {
		for (int j=0; j<M; j++) {
			if (pat.charAt(j)!=txt.charAt(i+j)) {
				System.out.println("catch wrong");
				return false;
			}
		}
		return true;
	}
	
	public int search(String txt) {
		int N = txt.length();
		if (N < M) return N;
		long txtHash = hash(txt,M);
		if ((patHash==txtHash) && check(txt,0)) return 0;
		
		for (int i=M; i<N; i++) {
			int leading = txt.charAt(i-M);
			int add = txt.charAt(i);
			txtHash = (txtHash + Q - (RM*leading)%Q)%Q;
			txtHash = (txtHash*R+add)%Q;
			
			int offset = i-M+1;
			if ((patHash==txtHash) && check(txt,offset)) return offset;
		}
		return N;
	}
	
	private static long longRandomPrime() {
		BigInteger prime = BigInteger.probablePrime(31, new Random());
		return prime.longValue();
	}
	
	public static void main(String[] args) {
		String txt = "abacadabrabracabracadabrabrabracad";
		String pattern = "abracadabra";
		RabinKarp rk = new RabinKarp(pattern);
		int index = rk.search(txt);

		if (index!=txt.length()) {
			System.out.println("index at "+index	);
			System.out.println(txt);
			String space = "";
			for (int i=0; i<index; i++) {
				space += " ";
			}
			System.out.println(space+pattern);
			
		} else {
			System.out.println("not found");
		}

	}

}
