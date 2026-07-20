/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructure;

/**
 *
 * @author pc
 * @param <T>
 */
public class SinglyLinkedList<T> {        // ← phải có <T>
    private Node<T> head;                 // ← Node phải có <T>
    private int size;

    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> cur = head;
            while (cur.next != null) cur = cur.next;
            cur.next = newNode;
        }
        size++;
    }

    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = head;
        head = newNode;
        size++;
    }

    public boolean deleteById(String id, IdExtractor<T> extractor) {
        if (head == null) return false;
        if (extractor.getId(head.data).equals(id)) {
            head = head.next;
            size--;
            return true;
        }
        Node<T> cur = head;
        while (cur.next != null) {
            if (extractor.getId(cur.next.data).equals(id)) {
                cur.next = cur.next.next;
                size--;
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    public T findById(String id, IdExtractor<T> extractor) {
        Node<T> cur = head;
        while (cur != null) {
            if (extractor.getId(cur.data).equals(id)) return cur.data;
            cur = cur.next;
        }
        return null;
    }

    public void traverse(Visitor<T> printer) {
        if (head == null) {
            System.out.println("  (Danh sach trong)");
            return;
        }
        Node<T> cur = head;
        while (cur != null) {
            printer.visit(cur.data);
            cur = cur.next;
        }
    }

    public int countIf(Condition<T> condition) {
        int count = 0;
        Node<T> cur = head;
        while (cur != null) {
            if (condition.check(cur.data)) count++;
            cur = cur.next;
        }
        return count;
    }

    public T findMax(Comparator<T> comparator) {
        if (head == null) return null;
        T max = head.data;
        Node<T> cur = head.next;
        while (cur != null) {
            if (comparator.compare(cur.data, max) > 0) max = cur.data;
            cur = cur.next;
        }
        return max;
    }

    public int size()        { return size; }
    public boolean isEmpty() { return head == null; }
}