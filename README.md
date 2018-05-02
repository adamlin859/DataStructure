# DataStructure  
## Summary of notes for COMS 3134 
  
### Disjoint Sets 

Three operation:
  1) 'makeSet': Creates a new subeset with single element.
  2) 'find': Determine which subset is variable in.
  3) 'union': joins two subsets into a single subset.
  
makeSet(x), makeSet(y) -> creates a set containing element x and a set for element y.  
union(x, y) -> [x,y]  
union(a, b) -> [a,b]  
union(a, x) -> [x,y,a,b]  

Disjointed set can be created throught the use of a LinkedList data structure that incorporate a 'HashMap<T, Node<T>>. This stores the value and the Node that store the data.  

Node with the prev equal to null will be representive element of the set.

#### 'makeSet' -> creates a set in O(1)  
No duplicate allow since we are using a HashMap

    public void makeSet(T data) {
        if (nodeReference.contains(data)){
            System.out.println("Duplicate data.")
        }
        
        // Repersentative node of the the set.
        Node<T> root = new Node<>(data, null);
        nodeReference.put(data, root);
    }
    
   
#### 'find' -> look for the element in the set by traversing the list. O(N) Since it might need to traverse the entire LinkedList  
This uses a recursive method to find the root node (representitive node) of a given set.  

    public T find(T data) {
    	Node<T> node = nodeReference.get(data);
    	if (node.prev == null) {
    		return data;
    	} else {
    		return find(node.prev.data);
    	}
    }
    
#### 'union' -> joins two set together. (Append the root node of the second linked list to the value of the first list)  
union(x, b)-> [x, y]
                  [a, b]  
  
    public void union(T data1, T data2) {
    	Node<T> data1Node = nodeReference.get(data1);

    	T root2 = find(data2);
    	Node<T> root2Node = nodeReference.get(root2);

    	root2Node.prev = data1Node;
    }

#### ‘toString' -> creates a HashMap of using the root node as a key and a linked list as the value. O(N) 

    public String toString() {
    	HashMap<T, LinkedList<T>> rootNode = new HashMap<>();
    	for (T data: nodeReference.keySet()) {
    		T root = find(data); 

    		if (!rootNode.containsKey(root)) {
    			// if the root is not map already
    			rootNode.put(root, new LinkedList<T>());
    		}

    		// add the data
    		rootNode.get(root).add(data);
    	}
    	return rootNode.toString();
    }
    
### Disjoint Set Forest  
Uses trees to implements the same disjoint set structure. Higher performance in find.

Change in Node structure:  

    public class Node<R>{
        public Node<R> parent;
        public R data;
        public int rank;

        public Node(R data, Node<R> parent) {
            this.parent = parent;
            this.data = data;
            this.rank = rank
        }

        public String toString() {
            return this.data.toString();
        }
    }	
    
 
#### 'find' implemenented path compression where all nodes except for the representative node has .prev O(log N)  

This is done through by setting the parent of the node to representative node, when using the find method.  

	public T find(T data) {
		Node<T> node = nodeReference.get(data);
		if (node.parent == null) {
			return data;
		} else {
			// path compression
			node.parent = nodeReference.get(find(node.parent.data));
			return node.parent.data;
		}
	}
  
#### 'union' simply set the parent of one tree to another. Each tree has a rank number(determine number of merges of the same rank. Attach the tree with less rank to the one with higher rank.  

	public void union(T data1, T data2) {
		T root1 = find(data1);
		T root2 = find(data2);

		if (root1.equals(root2)) {
			return;
		}

		Node<T> root1Node = nodeReference.get(root1);
		Node<T> root2Node = nodeReference.get(root2);

		if (root1Node.rank < root2Node.rank) {
			root1Node.parent = root2Node;
		} else if (root1Node.rank > root2Node.rank) {
			root2Node.parent = root1Node;
		} else {
			root2Node.parent = root1Node;
			root1Node.rank++;
		}

	}

### Graphs  
G = (V, E), graph G has sets of vertices V and edges E. Each edge (arcs) has a parir (v, w), where (v,w) is subset of V. In an undirected graph v is adjacent to w, and w to v. Loop happend when there a path that lead back to orginial node. Cycle have at lease one loop. Undirected graph should noe be considered a cycle. Directed graph that has no cycles is call acyclic.  Undireccted graph is connected if there is a path from every vertex to every other vertex. A directed graph that has a path from every vertex to every other vertex if call strongly connected. Weakly connected happend when nodes in a graph are connected without the regard for the direction. Complete graph is a graph where there is an edge between every pair of verticies.   

We can represent a graph using adjcency matrix (better for dense graphs) space requirement O(|V|^2) and adjacency list (better for sparse graph).  Dense graph |E| = O(|V|^2)

Simple Vertex (Using adjacency list):

	import java.util.LinkedList;
	public class Vertex{
	    public String name;
	    public List<Edge> adjVertices;

	    public Vertex(String name) {
		this.name = name;
		this.adjVertices = new LinkedList<Edge>;
	    }

	    public void addEdge(Edge edge){
		adjVertices.add(edge);
	    }
	}  

Simple Edge:

    public class Edge{
        public double weight;
        public Vertex source;
        public Vertex target;

        public Edge(Vertex source, Vertex target, double weight) {
            this.source = source;
            this.target = target;
            this.weight = weight;
        }
    }


### Topological Sort

### Minimum Spanning Tree

In an undirected graph, the minimum spanning tree is formed from graph edges that connectes all vertices of the grpah at lowest total cost. Only exist if the graph is connected. The minimum spanning tree is a tree because it is acyclic and spanning because it covers every vertex with edges |V| - 1.

#### Prim's Algorithm. Running time is O(|V|^2) without heaps (good for dense graphs) and O(|E| log |V|) using binary heap (good for sparse graphs.

Compute the minimum spanning tree by growing the tree in successive stages. This is greedy algorithm that at each stage adds a node to the tree by choice the edges with the lowest cost edge.
  
#### Kruskal's Algorithm  

Build minimum spanning tree by continually seleting the edges with the smallest weight and accepts the edges if it does not cause a cycle. Implemented using a forest (a collection of trees). Starting with |V| single node trees, then adding edges to merge two trees into one. Uses disjoint set to determine if there are cycle.



### NP-Completeness  

Complexity are not known. One of the formost open problem in theoretical computer science. 

#### Easy vs Hard Problem  

Easy problems are easy to check for correctness well hard problems (undecidable problem) is hard to check. 

#### Class NP  

NP stands for nondeterministic polynomial-time. Nondeterministic machine has the power of extremely good (optimal) guessing. It has a choice of next steps and it will always choice the correct step that leads to the solution. NP includes all problems that have polynomial-time solutions. There are problems in NP that do not have polynomial-time solutions, but no such problems has been found. Not all decidable problems are in NP.  

#### NP-Complete Problems  

NP-complete problems are a subset of NP that contains the hardest problems. An NP-complete problem has the property that any
problem in NP can be polynomially reduced to it.
