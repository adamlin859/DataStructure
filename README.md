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

  



