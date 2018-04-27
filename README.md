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

                 

