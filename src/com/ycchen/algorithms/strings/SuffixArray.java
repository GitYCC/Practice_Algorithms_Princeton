package com.ycchen.algorithms.strings;

import java.util.Arrays;

public class SuffixArray {
	private Suffix[] suffixes;
	
	public SuffixArray(String text) {
		int n = text.length();
		this.suffixes = new Suffix[n];
		for (int i=0; i<n; i++) {
			this.suffixes[i] = new Suffix(text,i);
		}
		Arrays.sort(this.suffixes);
	}
	
	private static class Suffix implements Comparable<Suffix> {
		private final String text;
		private final int index;
		
		private Suffix(String text,int index) {
			this.text = text;
			this.index = index;
		}
		public int compareTo(Suffix that) {
			if (this==that) return 0;
			int n = Math.min(this.length(), that.length());
			for (int i=0; i<n; i++) {
				if (this.charAt(i)<that.charAt(i)) return -1;
				if (this.charAt(i)>that.charAt(i)) return 1;
			}
			return this.length() - that.length();
		}
		private int length() {
			return text.length()-index;
		}
		private char charAt(int i) {
			return text.charAt(index+i);
		}
		public String toString() {
			return text.substring(index, text.length());
		}
	}
	
	public int length() {
		return suffixes.length;
	}
	
    public int lcp(int i) {
        if (i < 1 || i >= suffixes.length) throw new IndexOutOfBoundsException();
        return lcp(suffixes[i], suffixes[i-1]);
    }
    private static int lcp(Suffix s, Suffix t) {
    	int n = Math.min(s.length(), t.length());
    	for (int i=0; i<n; i++) {
    		if (s.charAt(i)!=t.charAt(i)) return i;
    	}
    	return n;
    }
    
    public int index(int i) {
    	return suffixes[i].index;
    }


}
