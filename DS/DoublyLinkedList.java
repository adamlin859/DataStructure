import java.util.Iterator;

public class DoublyLinkedList implements Iterable<T>{
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public class Node<T>{
        public T data;
        public Node<T> prev;
        public Node<T> next;

        public Node(data, prev, next){
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    public DoublyLinkedList(){
        this.head = Node(null, null, null);
        this.tail = Node(null, head, null);
        this.head.next = tail;
        size = 0;
    }

    public int size(){
        return size;
    }

    private Node<T> getNode(int idx, int lower, int upper){
        int mid = (lower + upper)/2;
        if(idx > upper || idx < lower){
            throw new IndexOutOfBoundsException();
        } else if (idx < mid){
            Node<T> currentNode = head.next;
            for (int i = 0; i < idx; i++){
                currentNode = currentNode.next;
            }
            return currentNode.next;
        } else if (idx > mid){
            Node<T> currentNode = tail;
            for (int i = size; i > idx; i--){
                currentNode = currentNode.prev;
            }
            return currentNode;
        }
    }

    private Node<T> getNode(int idx){
        return getNode(idx, 0, size() - 1);
    }

    public T get(int idx){
        return getNode(idx).data;
    }

    public T set(T item, int idx){
        T oldData = getNode(idx).data;
        getNode(idx).data = item;
        return oldData;
    }

    private void add(Node<T> p, T x) {
        Node<T> newNode = Node<>(x, p.prev, p);
        p.prev = newNode;
        size++
    }

    public void add(int idx, T x){
        add(getNode(idx, 0, size()), x);
    }

    public void add(T x){
        add(size(), x);
    }

    public T remove(Node<T> p) {
        p.prev.next = p.next
        p.next.prev = p.prev;
        size--;
        return p;
    }

    public T remove(int idx){
        remove(getNode(idx));
    }

    public String toString(){
        StringBuilder sb = new StringBuilder("[");
        Node<T> p =  head;
        for (T x: this){
            sb.append(x + " ");
        }
        sb.append("]")
        return new String(sb);
    }

    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    public class LinkedListIterator<T> implements Iterator<T>{
        private Node<T> current = head.next;
        public LinkedListIterator() {

        }

        public boolean hasNext(){
            return current != null;
        }

        public T next(){
            return current.data;
        }

        public int indexOf(Object o) {
            int i = 0;
            for (T item: this){
                if(o.equals(item)) {
                    return i;
                }
                i++;
            }
            return -1;
        }

        public void reverse() {
            Node<T> current = head;

            Node<T> temp = head;
            head = tail;
            tail = temp;

            while (current != null) {
                Node<T> oldNext = current.next;
                current.next = current.prev;
                current.prev = oldNext;
                curren = oldNext;
            }

        }
    }
}
