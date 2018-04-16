public class AdamBinarySearchTree<T extends Comparable<? super T>> implements AdamSearchTree<T> {
    private static class BinaryNode<AnyType> {
        BinaryNode( AnyType theElement){
            this (theElement, null, null);
        }
        BinaryNode( AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt ) {
            element = theElement; left = lt; right = rt; DELETE_FLAG = flase;
        }

        AnyType element;
        BinaryNode<AnyType> left;
        BinaryNode<AnyType> right;
        boolean DELETE_FLAG;
    }

    public class AdamBSTNode<Q extends Comparable<? super Q>> {
        public Q data;
        public AdamBSTNode<Q> left;
        public AdamBSTNode<Q> right;
        public boolean DELETE_FLAG;

        public AdamBSTNode(Q data, AdamBSTNode<Q> left, AdamBSTNode<Q> right){
            this.data = data;
            this.left = left;
            this.right = right;
            this.DELETE_FLAG = false;
        }

        public String toString() {return data.toString();}
    }

    private BinaryNode<T> root;
    private int size;

    public AdamBinarySearchTree() {
        root = null;
        size = 0;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        toString(root, sb, 0);
        return sb.toString();
    }

    private void toString(AdamBSTNode<T> node, StringBuilder sb, int level) {
        if (node != null) {
            if (node.left != null) {
                toString(node.left, sb, level + 1);
            }
        }

        for (int i = 0; i < level; i++) {
            sb.append(" ");
        }

        sb.append(node);
        sb.append("\n");

        if (node.right != null) {
            toString(node.right, sb, level + 1);
        }
    }

    @Override
    public void add(T item) {
        root = add(root, item);
    }

    private AdamBSTNode<T> add(AdamBSTNode<T> node, T item) {
        if (node == null) {
            size ++;
            return new AdamBSTNode<> (item, null, null);
        }
         if (item.compareTo(node.data) < 0) {
             node.left = add(node.left, item);
         } else if (item.compareTo(node.data) > 0) {
             node.right = add(node.right, item);
         } else {
             node.DELETE_FLAG = false;
         }
         return node;
    }

    @Override
    public T get(T item) {
        AdamBSTNode<T> getNode = get(root, item);
        return getNode != null ? getNode.data : null;
    }

    private AdamBSTNode<T> get(AdamBSTNode<T> node, T item){
        if (node == null ) {
            return null;
        }

        if (item.compareTo(node.data) < 0) {
            return get(node.left, item);
        } else if (item.compareTo(node.data) > 0) {
            return get(node.right, item);
        } else {
            return node.DELETE_FLAG != true ? node : null;
        }
    }

    @Override
    public boolean contains(T item) {
        return get(root, item) != null;
    }

    @Override
    public T remove(T item) {
        AdamBSTNode<T> removeNode = remove(root, item);
        return removeNode != null ? removeNode.data : null;
    }

    private AdamBSTNode<T> remove(AdamBSTNode<T> node, T item) {
        if (node == null) {
            return null;
        }

        if (item.compareTo(node.data) < 0) {
            return remove(node.left, item);
        } else if (item.compareTo(node.data) > 0) {
            return remove(node.right, item);
        } else {
            node.DELETE_FLAG = true;
            size--;
            return node;
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    public BinaryNode<T> findMin() {
        return findMin(root);
    }

    private BinaryNode<T> findMin(BinaryNode<T> node) {
        if (node == null){
            return null;
        }

        BinaryNode<T> node_left = findMin(node.left);

        if (node_left != null) {
            return node_left;
        } else if (node.DELETE_FLAG == false){
            return node;
        } else {
            return findMin(node.right);
        }
    }

    // private AdamBSTNode<T> findMin(AdamBSTNode<T> node) {
    //     AdamBSTNode<T> node_left;
    //     AdamBSTNode<T> node_right;
    //     if (node.left != null) {
    //         node.left = findMin(node.left);
    //     }
    //
    //     if (node.right != null) {
    //         node.right = findMin(node.left);
    //     }
    //
    //     return node.DELETE_FLAG != true ? node : null;
    // }
    //
    // private AdamBSTNode<T> findMin(AdamBSTNode<T> node) {
    //     if (node == null) {
    //         return null;
    //     }
    //     if (node.left.item.compareTo(node.right.item) < 0) {
    //         return node.left
    //     }
    //
    // }


    @Override
    public int height() {
        return -1;
    }


    public static void main(String[] args){
        AdamBinarySearchTree<Integer> binaryST = new AdamBinarySearchTree<>();
        binaryST.add(7);
        binaryST.add(5);
        binaryST.add(6);
        binaryST.add(2);
        binaryST.add(4);
        binaryST.add(3);
        binaryST.add(2);
        binaryST.add(1);
        binaryST.add(10);
        binaryST.add(11);
        binaryST.remove(10);
        binaryST.remove(1);
        binaryST.remove(2);
        binaryST.remove(3);
        binaryST.remove(4);
        binaryST.remove(5);
        binaryST.remove(6);
        binaryST.remove(7);


        //System.out.print(binaryST);
        System.out.print(binaryST.findMin());
        //System.out.print(binaryST);
    }

}
