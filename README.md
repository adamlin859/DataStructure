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
  
Handle colisions by creating a linkedList at each location and appending value whenever an colison occurs. Load factor is important in determining the complexity of the functions. 

    public SeparateChainingHashTable(int size) {
        this.theLists = new LinkedList[ nextPrime(size) ];
        // put a LinkedList in every position in the LinkedList in order to do 
        // Separate Chaining.
        this.currentSize = 0;
    }
    
A array is created to store all of the cell of hash table. Each cell is a LinkedList. The load factor lambda is the number of elements in the hash table to the table size. Try to ke4ep the load factor to 1 in separate chaining. 

##### Search  

Unsuccessful search requires examining of lambda node. A successful search require search over 1 + (lambda/2) nodes.  

    public boolean contains(AnyType x){
        List<AnyType> whichList = theLists[ myhash(x) ];
        return whichList.contains( x );
    } 

##### Insert  

Simply append the value to the LinkedList:  

    public void insert( AnyType x) {
        List<AnyType> whichList  = theLists[myhash(x)];

        if (!whichList.contains(x) {
            whichList.add(x);

            if (++ currentSize > theLists.length)
                rehash();
        }
        
    }

##### Remove  

    public void remove(AnyType x) {
        List<AnyType> whichList  = theLists[myhash(x)];

        if (!whichList.contains(x)) {
            whichList.remove(x);
            currentSize--;
        }
    }
#### Hashing without LinkedList  

Uses an array that each can contain will the following value:  

1. null
2. A value and the entry is active
3. A value and the entry is not active because it is marked delete. 

#### Linear Probing  

To speed up the alogrithm of hashing, consider using probing to reslove collisions. The load factor for probing should be below lambda = 0.5.  

Linear probing uses a linear function f(i) = i to move the value into a empty cell. Move one up until encounter a emptycell. Linear probing suffer from a problem of primary cluster where any key that haes in to the cluster will require many probing. numebr fo probes using linear probling is roughly 1/2(1+1/(1-lambda)^2) for insertions and unsuccessful searches, and 1/2(1+1(1-lambda)) for successful searches.   

#### Quadratic Probing  

Quadratic probing uses function f(i) = i^2. This can be also represented as f(i) = f(i − 1) + 2i − 1. Suffers from secondary clustering. 

Each cell contains an HashEntry:
 
    private static class HashEntry<AnyType> {
        public AnyType element; // the element
        public boolean isActive; // false if marked deleted
        
        public HashEntry( AnyType e ) { this( e, true ); }
        
        public HashEntry( AnyType e, boolean i ) { element = e; isActive = i; }
    }
    
Most important part of the data structure:  

    private int findPos(AnyType x) {
        int offset = 1;
        int currentPos = myhash(x);

        while (array[currentPos] != null && 
            !array[currentPos].element.equals(x)) {
            // f(i) = f(i -1) + 2i - 1
            currentPos += offset;
            offset += 2;
            if (currentPos >= array.length) {
                currentPos -= array.length;
            }
        }
        return currentPos;
    }
  
##### Insert 

    public void insert(AnyType x) {
        int currentPos = findPos(x);
        if (isActive(array[currentPos])) {
            return;
        }

        array[ currentPos ] = new HashEntry<>(x, true);

        if (++currentSize > array.length / 2)
            rehash(); 
    }

##### Remove - Uses lazy deletion

     public void remove(AnyType x) {
        int currentPos = findPos(x);
        if (isActive(currentPos))
            array[ currentPos ].isActie = false;
    }

#### Double hashing  

Uses the offset function of f(i)  = i * hash_2(x).  

#### Rehashing  O(N) 
  
When the load factor become too large we perform rehashing. Create an larger table and put the value from the old table to the new table.

    private void rehash() {
        HashEntry<AnyType> [] oldArray = array;

        allocateArray(nextPrime(2 * oldArray.length));
        currentSize = 0; 

        for( int i = 0; i < oldArray.length; i++) {
            if oldArray[ i ] != null && oldArray[ i ].isActive) 
                insert( oldArray[ i ].element);
        }
    }
    
### Priority Queues (Heaps)  
  
Can be used to rank jobs by importance. Two operations insert and deleteMin. Important to the implementation of greedy algorithms. Ways to implemement: 1) LinkedList (O(1) for insertion O(N) for deletion). 2) Sorted list (O(N) insertion for O(1) for deletion). 3) binary search tree (O(log N) average for both operations).  

#### Binary Heap  

Two important properties: 1) complete binary tree (all level is fill with exception of the bottom level).  2) Heap-order property. (Smallest element at the root, any node should be smaller than all of its descendants. 

Binary tree with height h has 2^h and 2^(h+1) -1 nodes. Therefore, height is O(log N). In a array, where the element is at position i, the left child is 2i and the right child is 2i + 1. 

#### Insertion  O(log N)

Look for the next available position then percolate up (compare to parent, if parent is bigger then change positions).  

	public void insert(AnyType x) {

		if (currentSize == array.length - 1)
			enlargeArray(array.length * 2 + 1)
		array[currentSize + 1]  = x;

		//percolateUp: leave the value at the 0 position and compare to all the 
		// parent for the size of the array and up.
		int hole  = ++currentSize;
		for(array[0] = x; x.compareTo(array[hole / 2]) < 0; hole/=2) {
			array[hole] = array[i / 2];
		}
		array[hole] = x;
	}

#### deleteMin  

Pop out the element at the root take the last elememt that was inserted and percolate down.

	public AnyType deleteMin() {
		if (isEmpty())
			throw new UnderflowException();
		AnyType minItem = findMin();
		array[1] = array[currentSize--];
		percolateDown(1);

		return minItem;

	}

	public void percolateDown(int i) {
		int child;
		AnyType tmp = array[hole];

		for (; hole * 2 <= currentSize; hole = child) {
			child = hole * 2;
			if (child != currentSize && array[child + 1].compareTo(array[child]) < 0) 
				child ++;
			if (array[child].compareTo(tmp) < 0 )
				array[ hole ] = array [child];
			else
				break;
		}

		array[ hole ] = tmp;
		
	}

    public AnyType findMin( ) {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return array[ 1 ];
    }
    
####

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
