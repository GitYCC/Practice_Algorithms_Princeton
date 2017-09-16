package com.ycchen.algorithms.graphs;

public class Edge implements Comparable<Edge>{
	private final int v;
	private final int w;
	private final double weight;
	
	public Edge(int v,int w,double weight) {
		if (v<0) throw new IllegalArgumentException("vertex index must be a nonnegative integer");
		if (w<0) throw new IllegalArgumentException("vertex index must be a nonnegative integer");
		if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
		
		this.v = v;
		this.w = w;
		this.weight = weight;
	}
	
	public double weight() {return weight;}
	
	public int eihter() {return v;}
	
	public int other(int v) {
		if (v==this.v) return this.w;
		else if (v==this.w) return this.v;
		else throw new IllegalArgumentException("Illegal endpoint");
	}
	
	@Override
	public int compareTo(Edge that) {
		return Double.compare(this.weight, that.weight);
	}
	
	@Override
	public String toString() {
		return String.format("%d-%d %.5f", v,w,weight);
	}
	
}
