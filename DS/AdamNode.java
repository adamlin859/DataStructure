public class AdamNode<T>{
    private T data;
    private AdamNode<T> next;

    public AdamNode(T data, AdamNode<T> next){
        this.data = data;
        this.next = next;
    }
}
