package datastructure;

import entity.Displayable;

/**
 * Custom doubly linked list. Used by AuctionService to keep closed
 * auction sessions in a history that can be browsed forward or backward.
 */
public class DoublyLinkedList<T extends Displayable> {

    private DNode<T> head;
    private DNode<T> tail;
    private int size;

    public DoublyLinkedList() {
        head = tail = null;
        size = 0;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }

    public void addLast(T data) {
        DNode<T> node = new DNode<>(data);
        if (isEmpty()) {
            head = tail = node;
        } else {
            node.prev = tail;
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public void displayForward() {
        DNode<T> cur = head;
        while (cur != null) {
            cur.data.display();
            cur = cur.next;
        }
    }

    public void displayBackward() {
        DNode<T> cur = tail;
        while (cur != null) {
            cur.data.display();
            cur = cur.prev;
        }
    }
}
