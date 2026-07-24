package datastructure;

/**
 * Custom FIFO queue backed by a singly linked list (no java.util.Queue).
 * Used to hold bids in arrival order before they are processed.
 */
public class LinkedQueue<T> {

    private Node<T> front;
    private Node<T> rear;
    private int size;

    public LinkedQueue() {
        front = rear = null;
        size = 0;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }

    public void enqueue(T data) {
        Node<T> node = new Node<>(data);
        if (isEmpty()) {
            front = rear = node;
        } else {
            rear.next = node;
            rear = node;
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T data = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return data;
    }

    public T peek() {
        return isEmpty() ? null : front.data;
    }
}
