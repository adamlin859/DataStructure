import java.util.Iterator;

public class AdamLinkedList<T> implements AdamList<T>, Iterable<T>{
    private AdamNode<T> head;;
    private int size;

    public AdamLinkedList(){
        this.head = new AdamNode<>(null, null);
        size = 0;
    }

    public void addFirst(T item){
        add(item, 0);
    }

    public void addLast(T item) {
        add(item, size);
    }

    public void add(T item, int index) {
        if(0 > index || index > size){
            throw new IndexOutOfBoundsException();
        }

        AdamNode<T> currentNode = head;
        for (int i = 0; i < index; i++){
            currentNode = currentNode.next;
        }
        AdamNode<T> newNode = new AdamNode<T>(item, currentNode.next);
        currentNode.next = newNode;
        size++;
    }

    public void removeFirst(){
        if (head.next != null) {
            head.next = head.next.next;
        }
        size--;
    }
    public void removeLast(){
        AdamNode<T> currentNode = head;
        while (currentNode.next.next != null){
            currentNode = currentNode.next;
        }
        currentNode.next = null;
        size--;
    }
    public void remove(int index){
        if(0 > index || index > size){
            throw new IndexOutOfBoundsException();
        }
        AdamNode<T> currentNode = head;
        for (int i = 0; i < index -1; i++){
            currentNode = currentNode.next;
        }
        currentNode.next = currentNode.next.next;
        size--;
    }

    public T getFirst(){
        return get(0);
    }
    public T getLast(){
        return get(size - 1);
    }

    public T get(int index){
        if(0 > index || index > size){
            throw new IndexOutOfBoundsException();
        }

        AdamNode<T> currentNode = head.next;
        for (int i = 0; i < index; i++){
            currentNode = currentNode.next;
        }
        return currentNode.data;
    }

    public void setFirst(T item){
        set(item, 0);
    }
    public void setLast(T item){
        set(item, size - 1);
    }
    public void set(T item, int index){
        if(0 > index || index > size){
            throw new IndexOutOfBoundsException();
        }

        AdamNode<T> currentNode = head;
        for (int i = 0; i < index; i++){
            currentNode = currentNode.next;
        }
        currentNode.next.data = item;
    }

    public Iterator<T> iterator(){
        return new AdamLinkedListIterator<T>();
    }

    public class AdamLinkedListIterator<AnotherT> implements Iterator<T>{
        private AdamNode<T> currentNode;

        public AdamLinkedListIterator(){
            this.currentNode = head.next;
        }

        public boolean hasNext(){
            return currentNode == null;
        }

        public T next(){
            T data = currentNode.data;
            currentNode = currentNode.next;
            return data;
        }

    }

    public class AdamNode<DifferentT>{
        private DifferentT data;
        private AdamNode<DifferentT> next;

        public AdamNode(DifferentT data, AdamNode<DifferentT> next){
            this.data = data;
            this.next = next;
        }
    }


    public int size(){
        return size;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        Iterator<T> itr = iterator();
        while (itr.hasNext()){
            sb.append(itr.next());
            sb.append(" ");
        }
        return sb.toString().trim();
    }
}
