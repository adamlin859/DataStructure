# DataStructure  
## Summary of notes for COMS 3134 

### AVL Trees  

AVL trees are binary search tree with a balance condition. The depth of the tree is O(log N).(Pefect balance tree has 2^k -1 nodes when k is the height of the tree where empty tree is defined to have a height of -1. AVL Trees have nthe property where the left and the right subtree can only differ by at most 1. All tree operation can be perform in o(log N) except deletion (lazy). 

Construction of the nodes and getting the height:

    private static class AvlNode<AnyType> {
        AnyType element;
        AvlNode<AnyType> left;
        AvlNode<AnyType> right;
        int height; // height of the tree.

        AvlNode(AnyType theElememt) {
            this(theElement, null, null);
        }

        AvlNode(AnyType theElememt, AvlNode<Anytype> lt, AvlNode<AnyType rt ) {
            this.element = theElememt;
            this.left = lt;
            this.right = rt;
            height = 0;
        }
    }

    private int height(AvlNode<AnyType> t) {
        return t == null ? -1 : t.height;
    }

#### Insertion - AVL  
  
Four way of inserting:  

1. Insertion into the left subtree of the left child (single rotation).  k2 is the inbalance node

	    private AvlNode<AnyType> rotateWithLeftChild(AvlNode<AnyType> k2) {
			AvlNode<AnyType> k1 = k2.left;
			k2.left = k1.right;
			k1.right = k2;
			k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
			k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
			return k1;
	    }
	    
2. Insertion into the right subtree of the left child (double rotation). k3 is the inbalance node   

	    private AvlNode<AnyType> doubleWithRightChild(AvlNode<AnyType> k3) {
			k3.right = rotateWithLeftChild(k3.right)
			return rotateWithRightChild(k3)
	    }
3. Insertion into the left subtree of the right child (double rotation). k3 is the inbalance node  
  
	    private AvlNode<AnyType> doubleWithLeftChild(AvlNode<AnyType> k3) {
		k3.left = rotateWithRightChild(k3.left)
		return rotateWithLeftChild(k3)
	    }
		
4. Insertion into the right subtree of the right child (single rotation). k2 is the inbalance node  

	    private AvlNode<AnyType> rotateWithRightChild(<AnyType> k2) {
		AvlNode<AnyType> k1 = k2.right;
		k2.right = k2.left; 
		k1.left = k2;
		k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
		k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
		return k1;
	    }
### Hashing 
  
Insertions, deletions and search are in constant average time. findMin, findMax and print sorted order are not supported. Each value has a key that is associated with it. Mapping of the key to the hash table is call a hash function.  
  
#### hash function
  
Simple hash function (takes he char value of all characters in string and mode by table size):

	public static int hash(String key, int tableSize {
		int hashVal = 0;
		 for ( int i = 0; i < key.length(); i++)
		 	hashVal += key.charAt(i)
		return hashVal % tableSize;
	}
  
Colision happend when two keys get map to the the same location. 

#### Separate Chaining
  
Handle colisions by creating a linkedList at each location and appending value whenever an colison occurs. 
  
### Sorting  

In java sorting must be object of type Comparable. Only compareTo and operands are allowed on the input data (comparsion- based sorting).

#### Insertion Sort O(N^2)  

Insertion sort pass through the input data N-1 times.  

    public static <AnyType extends Comparable<? super AnyType>> void
    insertionSort(AnyType [] a ) {
        int j;
        for (int p = 0; p < a.length; p++){ /// O(N)
            AnyType temp = a[p];
            for (j = p; j > 0 && temp.compareTo(a [j - 1]) < 0; j--) { //O(N)
                a[j] = a[j - 1];
            }
            a[j] = temp;
        }
    }

#### Heapsort O(N log N)

Use a max heap to perform N - 1 delete max to get the sorted array. 1) Perform deleteMax, 2) Swap last elememt with the head. 3) percolate down. By keeping the max number at the back of the array, we can perform heapsort using only one array.  




#### Shellsot o(N^2)  

#### 


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
	import java.util.List;
	
	public class Vertex{
	    public String name;
	    public List<Edge> adjVertices;
	    public boolean known;
	    public int indegree;

	    public Vertex(String name) {
		this.name = name;
		this.adjVertices = new LinkedList<Edge>();
		this.known = false; 
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

Order of the vertices in a directed acyclic graph. Vertices with no path to it are remove in a squence.  

![alt text](https://github.com/ewlovewe420/DataStructure/blob/master/directedGraph.png)  
  
Graph can be contructed like this: 

    public static void main(String [] args) {
        LinkedList<Vertex> vertexList = new LinkedList<>();


        Vertex v1 = new Vertex("V1");
        vertexList.add(v1);

        Edge e1 = new Edge(v1, v2, 1);
        v1.addEdge(e1);
        ++v2.indegree; // when there is a directed edge then increase indgree by 1.
	
    }
    
Topological Sort using LinkList:

    public static void topSort(LinkedList<Vertex> vertexList) throws CycleFoundException{
        int NUM_VERTICES = vertexList.size();
        for ( int counter = 0; counter < NUM_VERTICES; counter++) {
            Vertex v = findNewVertexOfIndegreeZero(vertexList);

            if (v == null)
                throw new CycleFoundException();

            v.topNum = counter; // ordering to the vertices

            for (Edge e: v.adjVertices)
                --e.target.indegree;

            System.out.print(v.name + " "); // vertices can also be print out using topNum
        }
    }

    public static Vertex findNewVertexOfIndegreeZero(LinkedList<Vertex> vertexList) {
        for (Vertex v: vertexList){
            if (!v.known && v.indegree  == 0) {
                v.known = true;
                return v;
            }
        }
        return null;
    }
    
Topological Sort using queue:
    
    public static void topSortQueue(LinkedList<Vertex> vertexList) throws CycleFoundException{
        Queue<Vertex> q = new LinkedList<Vertex>();
        int counter = 0;

        for (Vertex v: vertexList) {
            if (v.indegree == 0)
                q.offer(v);
        }

        while (!q.isEmpty()) {
            Vertex v = q.poll();
            v.topNum = ++counter;
            for (Edge e: v.adjVertices) {
                if ( --e.target.indegree == 0) {
                    q.offer(e.target);

                }
            }
            System.out.print(v.name + " ");
        }

        int NUM_VERTICES = vertexList.size();
        if (counter != NUM_VERTICES)
            throw new CycleFoundException();

    }
        
### Minimum Spanning Tree

In an undirected graph, the minimum spanning tree is formed from graph edges that connectes all vertices of the grpah at lowest total cost. Only exist if the graph is connected. The minimum spanning tree is a tree because it is acyclic and spanning because it covers every vertex with edges |V| - 1.

#### Prim's Algorithm. Running time is O(|V|^2) without heaps (good for dense graphs) and O(|E| log |V|) using binary heap (good for sparse graphs.

Compute the minimum spanning tree by growing the tree in successive stages. This is greedy algorithm that at each stage adds a node to the tree by choice the edges with the lowest cost edge.
  
#### Kruskal's Algorithm. O(|E| + |V|) since we are running through the edges and vertices.

Build minimum spanning tree by continually seleting the edges with the smallest weight and accepts the edges if it does not cause a cycle. Implemented using a forest (a collection of trees). Starting with |V| single node trees, then adding edges to merge two trees into one. Uses disjoint set to determine if there are cycle.

    private ArrayList<Edge> kruskal(List<Edge> edges, int numVertices){
        PriorityQueue<Edge> pq = new PriorityQueue<>(); // edges store in PriortyQueue to better find min
        DisjointSetLinkedList<Vertex> ds = new DisjointSetLinkedList<Vertex>(); // disjoint sets of vertex
        ArrayList<Edge> mst = new ArrayList<>(); //the final output of edges

        for (Edge e: edges) {
            pq.offer(e);
        }

        for (String u: vertexNames.keySet()){
            ds.makeSet(vertexNames.get(u));
        }

        while (mst.size() != numVertices - 1) {
            Edge e = pq.poll();
            Vertex uset = ds.find(e.source);
            Vertex vset = ds.find(e.target);

            if (uset != vset) {
                mst.add(e);
                ds.union(uset, vset);
            }
        }
        return mst;

    }

### NP-Completeness  

Complexity are not known. One of the formost open problem in theoretical computer science. 

#### Easy vs Hard Problem  

Easy problems are easy to check for correctness well hard problems (undecidable problem) is hard to check. 

#### Class NP  

NP stands for nondeterministic polynomial-time. Nondeterministic machine has the power of extremely good (optimal) guessing. It has a choice of next steps and it will always choice the correct step that leads to the solution. NP includes all problems that have polynomial-time solutions. There are problems in NP that do not have polynomial-time solutions, but no such problems has been found. Not all decidable problems are in NP.  

#### NP-Complete Problems  

NP-complete problems are a subset of NP that contains the hardest problems. An NP-complete problem has the property that any
problem in NP can be polynomially reduced to it.
