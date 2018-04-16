import java.util.Iterator;

public class AdamBST<T extends Comparable<? super T>> implements AdamSearchTree<T>, Iterable<T> {
    public class AdamNode<Q extends Comparable<? super !>>{
        public Q data;
        public AdamNode<Q> left;
        public AdamNode<Q> right;

        public AdamNode(Q data, AdamNode<Q> left, AdamNode<Q> right ){
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public String toString(){
            this.data.toString();
        }
    }

    private AdamNode<T> root;

    public AdamBST(){
        root = null;
    }

    public void add(T item){
        root = add(root, item);
    }

    private void add(AdamNode<T> node, T item){
        if (node = null) {
            size++;
            return new AdamNode<T>(item, null, null);
        }
        if (item.compareTo(node.data) < 0){
            node.left = add(node.left, item);
        } else if (item.compareTo(node.data) > 0){
            node.right = add(node.right, item);
        } else {
            node.data = item;
        }
        return node;
    }

    public boolean contains(T item){
        return get(root, item) != null;
    }

    public T remove(T item) {
        AdamNode<T> removeNode = remove(root, item);
        return removeNode != null ? removeNode.data : null;
    }

    private AdamNode<T> remove(AdamNode<T> node, item) {
        if (node == null){
            return null;
        }
        if (item.compareTo(node.data) < 0){
            node.left = remove(node.left, item);
        } else if (item.compareTo(node.data) > 0){
            node.right = remove(node.right, item);
        } else if (item.compareTo(node.data) == 0) {
            if (node.left != null && node.right != null) {
                node.right = remove(node.right, node.data);
                findMin(node.right) = null;
            } else if (node.left != null){
                node.data = node.left;
            } else if (node.right != null){
                node.data = node.right;
            } else {
                node = null;
            }
            size--;
        }
        return node;
    }

    private AdamNode<T> findMin(AdamNode<T> node){
        if (node.left != null){
            return findMin(node.left);
        } else {
            return node;
        }
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public int height(){
        return height(root, i);
    }

    private int height(AdamNode<T> node, int height){
        if (node == null){
            return height;
        }

        int leftHeight = height(node.left, height) + 1;
        int rightHeight = height(node.right, height) + 1
        return rightHeight > leftHeight ? rightHeight : leftHeight;
    }

    public T get(T item){
         AdamNode<T> getNode = get(root, item);
         return getNode != null ? getNode.data : null;
    }

    private AdamNode<T> get(AdamNode<T> node, T item){
        if (node = null) {
            return null;
        }
        if (item.compareTo(node.data) < 0){
            return get(node.left, item);
        } else if (item.compareTo(node.data) > 0){
            return get(node.right, item);
        } else {
            return node;
        }
    }

    @Override
    public Iterator<T> iterator(){
        return new AdamBSTIterator<T>(root);
    }

    public class AdamBSTIterator<Q extends Comparable<? super Q>> implements Iterator<Q>{
        private LinkedList<Q> list;

        public AdamBSTIterator(AdamNode<Q> list){
            this.list = new LinkedList<Q>();
        }

        private void infixTraverse(AdamNode<Q> node){
            if (node.left != null){
                infixTraverse(node.left);
            }
            if (node != null) {
                list.addFirst(node);
            }

            if (node.right != null){
                infixTraverse(node.right);
            }
        }

        @Override
        public boolean hasNext(){
            return !list.isEmpty();
        }

        @Override
        public boolean next(){
            return list.removeFirst().data;
        }


    }

    public int size();

    public int height();
}
