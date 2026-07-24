package datastructure;

import entity.Displayable;

/**
 * Custom singly linked list (no java.util.LinkedList).
 * Used by KoiService / CustomerService / AuctionService to hold their
 * master record lists.
 */
public class SinglyLinkedList<T extends Displayable> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public SinglyLinkedList() {
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
        Node<T> node = new Node<>(data);
        if (isEmpty()) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public T findById(String id, IdExtractor<T> ex) {
        Node<T> cur = head;
        while (cur != null) {
            if (ex.getId(cur.data).equals(id)) {
                return cur.data;
            }
            cur = cur.next;
        }
        return null;
    }

    public boolean deleteById(String id, IdExtractor<T> ex) {
        Node<T> cur = head, prev = null;
        while (cur != null) {
            if (ex.getId(cur.data).equals(id)) {
                if (prev == null) {
                    head = cur.next;
                } else {
                    prev.next = cur.next;
                }
                if (cur == tail) {
                    tail = prev;
                }
                size--;
                return true;
            }
            prev = cur;
            cur = cur.next;
        }
        return false;
    }

    /** Prints every element that satisfies the given condition (e.g. same color). */
    public int displayIf(Condition<T> cond) {
        Node<T> cur = head;
        int count = 0;
        while (cur != null) {
            if (cond.test(cur.data)) {
                cur.data.display();
                count++;
            }
            cur = cur.next;
        }
        return count;
    }

    public void traverse() {
        Node<T> cur = head;
        while (cur != null) {
            cur.data.display();
            cur = cur.next;
        }
    }
}
