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
    
   
#### 'find' -> look for the element in the set by traversing the list 
