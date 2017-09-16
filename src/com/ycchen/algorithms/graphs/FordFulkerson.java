package com.ycchen.algorithms.graphs;

import com.ycchen.algorithms.fundamentals.ArrayQueue;

public class FordFulkerson {
	private static final double FLOATING_POINT_EPSILON = 1E-11;
	
	private final int V;
	private boolean[] marked;
	private FlowEdge[] edgeTo;
	private double value;
	
	public FordFulkerson(FlowNetwork G, int s, int t) {
		this.V = G.V();
		validate(s);
		validate(t);
		if (s==t) throw new IllegalArgumentException("source equals sink");
		if (!isFlowFeasible(G,s,t)) throw new IllegalArgumentException("Initial flow is infeasible");
		
		value = excess(G,t);
		while(hasAugmentingPath(G,s,t)) {
			double bottle = Double.POSITIVE_INFINITY;
			for (int v = t; v != s; v = edgeTo[v].other(v)) {
				bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
			}
			for (int v = t; v != s; v = edgeTo[v].other(v)) {
				edgeTo[v].addResidualFlowTo(v,bottle);
			}
			value += bottle;
		}
		
	}
	
	public double value() {
		return this.value;
	}
	
	public boolean inCut(int v) {
		return marked[v];
	}
	
	private void validate(int v) {
		if (v < 0 || v >= V) 
			throw new IllegalArgumentException("vertex "+v+" is not between 0 and "+(V-1));
	}
	
	private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
		edgeTo = new FlowEdge[G.V()];
		marked = new boolean[G.V()];
		
		ArrayQueue<Integer> queue = new ArrayQueue<Integer>();
		queue.enqueue(s);
		marked[s] = true;
		
		while (!queue.isEmpty()) {
			int v = queue.dequeue();
			for (FlowEdge e: G.adj(v)) {
				int w = e.other(v);
				if (e.residualCapacityTo(w)>0 && !marked[w]) {
					marked[w] = true;
					edgeTo[w] = e;
					queue.enqueue(w);
				}
			}
		}
		
		return marked[t];
	}
	
	private double excess(FlowNetwork G, int v) {
		double excess = 0.0;
		for (FlowEdge e: G.adj(v)) {
			if (v==e.from()) excess -= e.flow();
			else if (v==e.to()) excess += e.flow();
		}
		return excess;
	}
	
	private boolean isFlowFeasible(FlowNetwork G, int s, int t) {
				
		// flow in = flow out
		if (Math.abs(excess(G,s)+excess(G,t)) > FLOATING_POINT_EPSILON) return false;
		
		// not s and t : excess = 0
		for (int v=0; v<G.V(); v++) {
			if (v==s || v==t) {
				continue;
			} else {
				if (Math.abs(excess(G,v)) > FLOATING_POINT_EPSILON) return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		FlowNetwork G = new FlowNetwork(8);
		int[] vs = {0,0,0,1,1,1,2,2,3,4,4,5,5,6,6};
		int[] ws = {1,2,3,2,4,5,3,5,6,5,7,6,7,2,7};
		double[] caps = {0.10,0.05,0.15,0.04,0.09,0.15,0.04,0.08,0.16,0.15,0.10,0.15,0.10,0.06,0.10};
		for (int i=0; i<vs.length; i++) {
			int v = vs[i];
			int w = ws[i];
			double cap = caps[i];
			G.addEdge(new FlowEdge(v,w,cap));
		}
		System.out.print(G.toString());
		
		int s=0, t=7;
		FordFulkerson maxflow = new FordFulkerson(G,s,t);
		System.out.println("Max flow from "+s+" to "+t);
		for (int v=0; v<G.V(); v++) {
			for (FlowEdge e: G.adj(v)) {
				if (v==e.from() && e.flow()>0)
					System.out.println(" "+e);
			}
		}
		System.out.println("Max flow value = "+maxflow.value());
		
		
		System.out.print("Min cut: ");
		for (int v=0; v<G.V(); v++) {
			if (maxflow.inCut(v)) System.out.print(v+" ");
		}
		System.out.println();

	}

}
