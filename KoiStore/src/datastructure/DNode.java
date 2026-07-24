package datastructure;

public class DNode<T> {
    public T data;
    public DNode<T> prev;
    public DNode<T> next;

    public DNode(T data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}
