package com.ycchen.algorithms.graphs;


import com.ycchen.algorithms.fundamentals.ArrayStack;
import com.ycchen.algorithms.fundamentals.ArrayQueue;

public class EulerianPath {
    private ArrayStack<Integer> path = null;   

    // an undirected edge, with a field to indicate whether the edge has already been used
    private static class Edge {
        private final int v;
        private final int w;
        private boolean isUsed;

        public Edge(int v, int w) {
            this.v = v;
            this.w = w;
            isUsed = false;
        }

        // returns the other vertex of the edge
        public int other(int vertex) {
            if      (vertex == v) return w;
            else if (vertex == w) return v;
            else throw new IllegalArgumentException("Illegal endpoint");
        }
    }

    // returns any non-isolated vertex; -1 if no such vertex
    private static int nonIsolatedVertex(Graph G) {
        for (int v = 0; v < G.V(); v++)
            if (G.degree(v) > 0)
                return v;
        return -1;
    }

    
    public EulerianPath(Graph G) {
        int oddDegreeVertices = 0;
        int s = nonIsolatedVertex(G);
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) % 2 != 0) {
                oddDegreeVertices++;
                s = v;
            }
        }
        if (oddDegreeVertices > 2) return;

        // special case for graph with zero edges (has a degenerate Eulerian path)
        if (s == -1) s = 0;

        // create local view of adjacency lists, to iterate one vertex at a time
        // the helper Edge data type is used to avoid exploring both copies of an edge v-w
        ArrayQueue<Edge>[] adj = (ArrayQueue<Edge>[]) new ArrayQueue[G.V()];
        for (int v = 0; v < G.V(); v++)
            adj[v] = new ArrayQueue<Edge>();

        for (int v = 0; v < G.V(); v++) {
            int selfLoops = 0;
            for (int w : G.adj(v)) {
                // careful with self loops (double count)
                if (v == w) {
                    if (selfLoops % 2 == 0) {
                        Edge e = new Edge(v, w);
                        adj[v].enqueue(e);
                        adj[w].enqueue(e);
                    }
                    selfLoops++;
                }
                else if (v < w) {
                    Edge e = new Edge(v, w);
                    adj[v].enqueue(e);
                    adj[w].enqueue(e);
                }
            }
        }

        // initialize stack with any non-isolated vertex
        ArrayStack<Integer> stack = new ArrayStack<Integer>();
        stack.push(s);

        // greedily search through edges in iterative DFS style
        path = new ArrayStack<Integer>();
        while (!stack.isEmpty()) {
            int v = stack.pop();
            while (!adj[v].isEmpty()) {
                Edge edge = adj[v].dequeue();
                if (edge.isUsed) continue;
                edge.isUsed = true;
                stack.push(v);
                v = edge.other(v);
            }
            // push vertex with no more leaving edges to path
            path.push(v);
            System.out.println(stack);
        }

        // check if all edges are used
        if (path.size() != G.E() + 1)
            path = null;

        assert certifySolution(G);
    }

    
    public Iterable<Integer> path() {
        return path;
    }

    
    public boolean hasEulerianPath() {
        return path != null;
    }




    
    // Determines whether a graph has an Eulerian path using necessary
    // and sufficient conditions (without computing the path itself):
    //    - degree(v) is even for every vertex, except for possibly two
    //    - the graph is connected (ignoring isolated vertices)
    // This method is solely for unit testing.
    private static boolean hasEulerianPath(Graph G) {
        if (G.E() == 0) return true;

        // Condition 1: degree(v) is even except for possibly two
        int oddDegreeVertices = 0;
        for (int v = 0; v < G.V(); v++)
            if (G.degree(v) % 2 != 0)
                oddDegreeVertices++;
        if (oddDegreeVertices > 2) return false;

        // Condition 2: graph is connected, ignoring isolated vertices
        int s = nonIsolatedVertex(G);
        BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);
        for (int v = 0; v < G.V(); v++)
            if (G.degree(v) > 0 && !bfs.hasPathTo(v))
                return false;

        return true;
    }

    // check that solution is correct
    private boolean certifySolution(Graph G) {

        // internal consistency check
        if (hasEulerianPath() == (path() == null)) return false;

        // hashEulerianPath() returns correct value
        if (hasEulerianPath() != hasEulerianPath(G)) return false;

        // nothing else to check if no Eulerian path
        if (path == null) return true;

        // check that path() uses correct number of edges
        if (path.size() != G.E() + 1) return false;

        // check that path() is a path in G
        // TODO

        return true;
    }


    private static void unitTest(Graph G, String description) {
        System.out.println(description);
        System.out.println("-------------------------------------");
        System.out.print(G);

        EulerianPath euler = new EulerianPath(G);

        System.out.print("Eulerian path:  ");
        if (euler.hasEulerianPath()) {
            for (int v : euler.path()) {
            	System.out.print(v + " ");
            }
            System.out.println();
        }
        else {
        	System.out.println("none");
        }
        System.out.println();
    }


    /**
     * Unit tests the {@code EulerianPath} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
		//        0
    	//      /  \
    	//     1----2
    	//     | \/ |
    	//     | /\ |
    	//     3----4
    	//      \  /
    	//        5
    	//      //\\
    	//      \\//
    	//       \/
    	
		Graph G = new Graph(6);
		G.addEdge(0, 1); G.addEdge(0, 2); G.addEdge(1, 2); G.addEdge(1, 3);
		G.addEdge(2, 4); G.addEdge(2, 3); G.addEdge(1, 4); G.addEdge(3, 4); 
		G.addEdge(4, 5); G.addEdge(3, 5); G.addEdge(5, 5); G.addEdge(5, 5);
		
        unitTest(G, "Eulerian cycle");

    }
}

