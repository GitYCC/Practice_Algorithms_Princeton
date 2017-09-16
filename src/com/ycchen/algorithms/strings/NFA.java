package com.ycchen.algorithms.strings;

import com.ycchen.algorithms.graphs.DepthFirstDirectedPaths;
import com.ycchen.algorithms.graphs.Digraph;
import com.ycchen.algorithms.fundamentals.ArrayStack;
import com.ycchen.algorithms.fundamentals.ArrayBag;

public class NFA {
	
	private Digraph graph; // epsilon transitions
	private String regexp;
	private final int M; // number of characters in reg. exp.
	
	public NFA(String regexp) {
		this.regexp = regexp;
		M = regexp.length();
		graph = new Digraph(M+1);
		ArrayStack<Integer> ops = new ArrayStack<Integer>();
		
		for (int i=0; i<M; i++) {
			int lp = i; // left parentheses
			char c = regexp.charAt(i);
			if ( c =='(' || c =='|') {
				ops.push(i);
			} else if (c ==')') {
				int or = ops.pop();
				if (regexp.charAt(or)=='|') {
					lp = ops.pop();
					graph.addEdge(lp, or+1);
					graph.addEdge(or, i);
				} else if (regexp.charAt(or)=='(') {
					lp = or;
				}
			}
			// closure operator (use 1-character lookahead)
			if (i<M-1 && regexp.charAt(i+1)=='*') {
				graph.addEdge(lp, i+1);
				graph.addEdge(i+1, lp);
			}
			if (c=='(' || c=='*' || c==')') {
				graph.addEdge(i, i+1);
			}
		}
	}
	
	public boolean recongnizes(String txt) {
		
		ArrayBag<Integer> pc = new ArrayBag<>();
		DepthFirstDirectedPaths dfs = new DepthFirstDirectedPaths(graph,0);
		for (int v=0; v<graph.V();v++) {
			if (dfs.hasPathTo(v)) pc.add(v);
		}
		for (int i=0; i<txt.length();i++) {
			char txt_c = txt.charAt(i);
			if (txt_c=='*'||txt_c=='|'||txt_c=='('||txt_c==')') {
				throw new IllegalArgumentException("text contains the metacharacter '"+txt_c+"'");
			}
			ArrayBag<Integer> match = new ArrayBag<>();
			for (int v: pc) {
				if (v==M) continue;
				if (regexp.charAt(v)==txt_c || regexp.charAt(v)=='.') {
					match.add(v+1); // add next one
				}
			}
			pc = new ArrayBag<>();
			for (int v: match) {
				dfs = new DepthFirstDirectedPaths(graph,v);
				for (int k=0; k<graph.V();k++) {
					if (dfs.hasPathTo(k)) pc.add(k);
				}
			}
			
			if (pc.size()==0) return false;
		}
		// check for accept state
		for (int v: pc) {
			if (v==M) return true;
		}
		return false;		
	}
	

	
	public static void main(String[] args) {
		String regexp = "((A*B|AC)D)";

		NFA nfa = new NFA(regexp);
		String txt1 = "AABD";		
		System.out.println("matching in '"+txt1+"' ? "+nfa.recongnizes(txt1));
		String txt2 = "ABAD";		
		System.out.println("matching in '"+txt2+"' ? "+nfa.recongnizes(txt2));
		String txt3 = "ACD";		
		System.out.println("matching in '"+txt3+"' ? "+nfa.recongnizes(txt3));	
		String txt4 = "AACD";		
		System.out.println("matching in '"+txt4+"' ? "+nfa.recongnizes(txt4));
		String txt5 = "AAAAAAAAAAAABD";		
		System.out.println("matching in '"+txt5+"' ? "+nfa.recongnizes(txt5));		
	}
}
