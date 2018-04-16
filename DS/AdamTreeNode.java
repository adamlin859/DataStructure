public class AdamTreeNode<T>{
    public T data;
    public AdamTreeNode<T> left;
    public AdamTreeNode<T> right;

    public AdamTreeNode(T data, AdamTreeNode<T> left, AdamTreeNode<T> right){
        this.data = data;
        this.left = left;
        this.right = right;
    }

    public String toString(){
        return data.toString();
    }
}
