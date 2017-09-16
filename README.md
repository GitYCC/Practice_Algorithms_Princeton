# Algorithms, Part I and Part II  (Princeton University)

This project is my practice coding of data structure and algorithm, referencing MOOC Algorithms by Princeton University.

Coursera Algorithms, Part I: https://www.coursera.org/learn/algorithms-part1

Coursera Algorithms, Part II: https://www.coursera.org/learn/algorithms-part2

Algorithms, 4th Edition: http://algs4.cs.princeton.edu/home/

## Fundamentals

1. Union–Find Data Type: An unordered collection supports the union and find operations.
   * algorithms/fundamentals/AbstractUF.java
   * algorithms/fundamentals/I_UF.java
   * algorithms/fundamentals/QuickFindUF.java
   * algorithms/fundamentals/QuickUnionUF.java
   * algorithms/fundamentals/WeightedQuickUnionUF.java
   * algorithms/fundamentals/PathCompressionUF.java
2. Bag: An unordered collection of elements
   * algorithms/fundamentals/I_Bag.java
   * algorithms/fundamentals/ArrayBag.java
   * algorithms/fundamentals/LinkedBag.java (including algorithms/fundamentals/Node.java)
3. Stack: A collection of elements obeys LIFO (last in, first out) rule, with two principal operations: push and pop. 
   * algorithms/fundamentals/I_Stack.java
   * algorithms/fundamentals/ArrayStack.java  
   * algorithms/fundamentals/LinkedStack.java (including algorithms/fundamentals/Node.java)
4. Queue: A collection of elements obeys FIFO (first in, first out) rule, with two principal operations: enqueue and dequeue.
   * algorithms/fundamentals/I_Queue.java
   * algorithms/fundamentals/ArrayQueue.java  
   * algorithms/fundamentals/LinkedQueue.java (including algorithms/fundamentals/Node.java)
5. Deque: Double-ended queue,  abstract data type that generalizes a queue, for which elements can be added to or removed from either the front (head) or back (tail).
   * algorithms/fundamentals/I_Deque.java
   * algorithms/fundamentals/ArrayDeque.java
   * algorithms/fundamentals/LinkedDeque.java (including algorithms/fundamentals/Node.java)
6. List: implement of python list data type, abstract data type that generalizes a queue, for which elements can be added to or removed from any positions.
   * algorithms/fundamentals/I_List.java
   * algorithms/fundamentals/ArrayList.java  
   * algorithms/fundamentals/LinkedList.java (including algorithms/fundamentals/Node.java)

## Sorting

1. Selection Sort: 

   ```{java}
   int N=a.length;
   for (int i=0;i<N;i++){
     int min=i;
     for (int j=i+1;j<N;j++){
       if (less(a[j],a[min])) min=j;
     }
     exchange(a,i,min);
   }
   ```

   * algorithms/sorting/SelectionSort.java

2. Insertion Sort:

   ```Java
   int N=a.length;
   for (int i=0;i<N;i++){
     for (int j=i;j>0;j--){
       if (less(a[j],a[j-1])){
         exchange(a,i,min);
       }else{
         break;
       }
     }
   }
   ```

   * algorithms/sorting/SimpleInsertionSort.java
   * algorithms/sorting/InsertionSort.java


   * algorithms/sorting/ShellSort.java

3. Merge Sort:

   ```java
   private static void sort(Comparable[] a,Comparable[] aux,int lo,int hi) {
   	if (hi<=lo) return;
   	int mid = lo + (hi-lo)/2;
   	sort(a,aux,lo,mid);
   	sort(a,aux,mid+1,hi);
   	merge(a,aux,lo,mid,hi);
   }
   private static void merge(Comparable[] a,Comparable[] aux,int lo,int mid,int hi) {		
   	// copy to aux[]
   	for (int k=lo;k<=hi;k++) aux[k]=a[k];
   	// merge back to a[]
   	int i = lo;
   	int j = mid+1;
   	for (int k=lo;k<=hi;k++) {
   		if      (i > mid)             a[k] = aux[j++];
   		else if (j > hi)              a[k] = aux[i++];
   		else if (less(aux[j],aux[i])) a[k] = aux[j++];
   		else                          a[k] = aux[i++];
   		//takes from left sub-array if equal keys
   	}
   }
   sort(a,aux,lo,hi);
   ```

   * algorithms/sorting/MergeSort.java
   * algorithms/sorting/MergeSortBU.java
   * algorithms/sorting/OptimizedMergeSort.java

4. Quick Sort:

   ```java
   private static void sort(Comparable[] a,int lo,int hi) {
   	if (hi<=lo) return;
   	int p = partition(a,lo,hi);
   	sort(a,lo,p-1);
   	sort(a,p+1,hi);
   }
   private static int partition(Comparable[] a,int lo,int hi) {
   	Comparable v = a[lo];
   	int i=lo,j=hi+1;
   	while (true){
   		while(less(a[++i],v)) if (i==hi) break;
   		while(less(v,a[--j])) if (j==lo) break;
   		if (i>=j) break;
   		exchange(a,i,j);
   	}		
   	exchange(a,j,lo);
   	return j;
   }
   int N = a.length;
   sort(a,0,N-1);
   ```

   * algorithms/sorting/QuickSort.java
   * algorithms/sorting/ThreeWayQuickSort.java
   * algorithms/sorting/OptimizedQuickSort.java

5. Priority Queue:

   * algorithms/sorting/I_MaxPriorityQueue.java
   * algorithms/sorting/I_MinPriorityQueue.java
   * algorithms/sorting/HeapMaxPQ.java
   * algorithms/sorting/HeapMinPQ.java


   * algorithms/sorting/HeapSort.java

## Searching

1. Symbol Tables:
   * algorithms/searching/I_SymbolTable.java
   * algorithms/searching/I_OrderedSymbolTable.java
   * algorithms/searching/SequentialSearchST.java
2. Binary Search Tree:
   * algorithms/searching/BinarySearchTree.java
   * algorithms/searching/BinarySearchST.java
3. Red Black Binary Search Tree:
   * algorithms/searching/RedBlackBST.java
4. Hash Table:
   * algorithms/searching/SeparateChainingHashST.java

## Graph

1. Undirected Graphs:
   * algorithms/graphs/Graph.java
   * algorithms/graphs/DepthFirstPaths.java
   * algorithms/graphs/BreadthFirstPaths.java
   * algorithms/graphs/Cycle.java
   * algorithms/graphs/Bipartite.java
   * algorithms/graphs/EulerianPath.java
2. Directed Graphs:
   * algorithms/graphs/Digraph.java
   * algorithms/graphs/DepthFirstDirectedPaths.java
   * algorithms/graphs/BreadthFirstDirectedPaths.java
   * algorithms/graphs/DirectedCycle.java
   * algorithms/graphs/WebCrawler.java
   * algorithms/graphs/DepthFirstOrder.java
   * algorithms/graphs/KosarajuSharirSCC.java
3. Minimum Spanning Trees (MST):
   * algorithms/graphs/Edge.java
   * algorithms/graphs/KruskalMST.java
   * algorithms/graphs/EdgeWeightedGraph.java
   * algorithms/graphs/LazyPrimMST.java
   * algorithms/graphs/PrimMST.java
   * algorithms/graphs/IndexMinPQ.java
   * algorithms/graphs/Topological.java
4. Shortest Paths:
   * algorithms/graphs/DirectedEdge.java
   * algorithms/graphs/EdgeWeightedDigraph.java
   * algorithms/graphs/EdgeWeightedDirectedCycle.java
   * algorithms/graphs/DijkstraSP.java
   * algorithms/graphs/BellmanFordSP.java
   * algorithms/graphs/AcyclicSP.java
5. Maximum Flow:
   * algorithms/graphs/FlowEdge.java
   * algorithms/graphs/FlowNetwork.java
   * algorithms/graphs/FordFulkerson.java

## Strings

1. String Sort:
   * algorithms/strings/LSD.java
   * algorithms/strings/MSD.java
   * algorithms/strings/Quick3string.java
   * algorithms/strings/SuffixArray.java
   * algorithms/strings/LongestRepeatedSubstring.java
2. Tries:
   * algorithms/strings/TernaryST.java
3. Sub-string Search:
   * algorithms/strings/KMP.java
   * algorithms/strings/BoyerMoore.java
   * algorithms/strings/RabinKarp.java
4. Regular Expression:
   * algorithms/strings/NFA.java