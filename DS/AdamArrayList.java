import java.util.Iterator;

public class AdamArrayList<T> implements AdamList<T>, Iterable<T>{
    private T[] data;
    public static final int INITIAL_SIZE = 20;
    public static final int GROWTH_FACTOR = 2;
    private int size;

    @SuppressWarnings("unchecked")
    public AdamArrayList(int length ){
        this.data = (T[]) new Object[length];
        this.size = 0;
    }

    public AdamArrayList(){
        this(INITIAL_SIZE);
    }

    public void addFirst(T item){
        add(item, 0);
    }
    public void addLast(T item){
        add(item, size);
    }

    public void ensureCapacity(int minCapacity) {
        T[] temp = (T[]) new Object[minCapacity];
        for (int i = 0; i < size; i++){
            temp[i] = data[i];
        }
        data = temp;
    }

    public void add(T item, int index) {
        if (0 > index || index > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        if (data.length == size){
            ensureCapacity(data.length * GROWTH_FACTOR + 1);
        }
        for (int i = size; i > index; i--){
            data[i] = data[i - 1];
        }
        data[index] = item;
        size++;
    }

    public void removeFirst(){
        remove(0);
    }
    public void removeLast(){
        remove(size - 1);
    }
    public void remove(int index){
        if (0 > index || index > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = index; i < size; i--) {
            data[i] = data[i + 1];
        }
        data[size - 1] =null;
        size--;
    }
    public T getFirst() {
        return get(0);
    }
    public T getLast(){
        return get(size - 1);
    }
    public T get(int index){
        if (0 > index || index > size) {
            return null;
        }
        return data[index];
    }
    public void setFirst(T item){
        set(item, 0);
    }
    public void setLast(T item){
        set(item, size-1);
    }
    public void set(T item, int index){
        if (0 > index || index > size) {
            throw new IndexOutOfBoundsException();
        }
        data[index] = item;
    }

    @Override
    public Iterator<T> iterator(){
        Iterator<T> iterator = new AdamArrayListIterator<T>(this);
        return iterator;
    }

    public class AdamArrayListIterator<T> implements Iterator<T>{
        private int index;
        private AdamArrayList<T> list;

        public AdamArrayListIterator(AdamArrayList<T> list){
            this.list = list;
            this.index = 0;
        }

        public boolean hasNext(){
            return this.index < list.size();
        }

        public T next(){
            T item = list.get(index);
            index++;
            return item;
        }
    }

    public int size(){
        return this.size;
    }
}
