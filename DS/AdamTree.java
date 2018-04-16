import java.util.LinkedList;

public class AdamTree<T> implements Iterable<T>{
    private AdamTreeNode<T> parent;

    public AdamTree(AdamTreeNode<T> parent){
        this.parent = parent;
    }

    public String prefix(){
        StringBuilder sb = new StringBuilder();
        prefix(parent, sb);
        return sb.toString().trim();
    }

    private void prefix(AdamTreeNode<T> root, StringBuilder sb){
        sb.append(root);
        sb.append(" ");

        if (root.left != null) {
            prefix(root.left, sb);
        }
        if (root.right != null) {
            prefix(root.right, sb);
        }
    }

    public String infix(){
        StringBuilder sb = new StringBuilder();
        infix(parent, sb);
        return sb.toString().trim();
    }

    private void infix(AdamTreeNode<T> node, StringBuilder sb) {
        if(node.left != null) {
            infix(node.left, sb);
        }

        sb.append(node);
        sb.append(" ");

        if (node.right != null) {
            infix(node.right, sb);
        }
    }


    public String postfix() {
        StringBuilder sb = new StringBuilder();
        postfix(parent, sb);
        return sb.toString().trim();
    }

    private void postfix(AdamTreeNode<T> node, StringBuilder sb){
        if (node.left != null) {
            postfix(node.left, sb);
        }

        if (node.right != null) {
            postfix(node.right, sb);
        }

        sb.append(node);
        sb.append(" ");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(parent, sb, 0);
        return sb.toString();
    }

    private void toString(AdamTreeNode<T> node, StringBuilder sb, int level){
        for (int i = 0; i < level; i++){
            sb.append(" ");
        }

        sb.append(node);
        sb.append("\n");

        if(node.left != null) {
            toString(node.left, sb, level + 1);
        }

        if(node.left != null) {
            toString(node.right, sb, level + 1);
        }
    }

    public String prefixStack() {
        StringBuilder sb = new StringBuilder();
        LinkedList<AdamTreeNode<T>> stack = new LinkedList<>();
        stack.push(parent);

        while (!stack.isEmpty()) {
            AdamTreeNode<T> current = stack.pop();
            sb.append(current);
            sb.append(" ");

            if (current.right != null) {
                stack.push(current.right);
            }

            if (current.left != null) {
                stack.push(current.left);
            }
        }
        return sb.toString().trim();
    }

    public String breathFirst() {
        StringBuilder sb = new StringBuilder();
        LinkedList<AdamTreeNode<T>> queue = new LinkedList<>();
        queue.addFirst(parent);

        while(!queue.isEmpty()) {
            AdamTreeNode<T> current = queue.poll();
            sb.append(current);
            sb.append(" ");

            if (current.left != null) {
                queue.offer(current.left);
            }

            if (current.right != null) {
                queue.offer(current.right);
            }
        }
        return sb.toString().trim();

    }

    public class AdamTreeIterator<Q> implements iterator<Q> {
        private LinkedList<AdamTreeNode<Q>> list;
        public AdamTreeIterator(AdamTreeNode<Q> parent){
            list = new LinkedList<>();
            prefixTraverse(parent);
        }
        public void prefixTraverse(AdamTreeNode<Q> node) {
            list.add(node);
            if (node.left != null) {
                prefixTraverse(node.left);
            }
            if (node.right != null) {
                prefixTraverse(node.right);
            }
        }

        @Override
        public hasNext(){
            return !list.isEmpty();
        }

        @Override
        public next(){
            return list.removeFirst().data;
        }
    }

    public static void main(String[] args){
        AdamTreeNode<String> g1 = new AdamTreeNode<>("g1", null, null);
        AdamTreeNode<String> g2 = new AdamTreeNode<>("g2", null, null);
        AdamTreeNode<String> g3 = new AdamTreeNode<>("g3", null, null);
        AdamTreeNode<String> g4 = new AdamTreeNode<>("g4", null, null);

        AdamTreeNode<String> c1 = new AdamTreeNode<>("c1", g1, g2);
        AdamTreeNode<String> c2 = new AdamTreeNode<>("c2", g3, g4);

        AdamTreeNode<String> p1 = new AdamTreeNode<>("p1", c1, c2);

        AdamTree<String> tree = new AdamTree<>(p1);
        System.out.println(tree.prefix());
        System.out.println(tree.infix());
        System.out.println(tree.postfix());
        System.out.println(tree.toString());
        System.out.println(tree.prefixStack());
        System.out.println(tree.breathFirst());
    }
}
