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
public class DoublyLinkedList<T> {
    private DNode<T> head;
    private DNode<T> tail;
    private int size;

    public void addLast(T data) {
        DNode<T> newNode = new DNode<>(data);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next    = newNode;
            newNode.prev = tail;
            tail         = newNode;
        }
        size++;
    }

    public void displayForward(Visitor<T> printer) {
        if (head == null) { System.out.println("  (Trong)"); return; }
        DNode<T> cur = head;
        while (cur != null) {
            printer.visit(cur.data);
            cur = cur.next;
        }
    }

    public void displayBackward(Visitor<T> printer) {
        if (tail == null) { System.out.println("  (Trong)"); return; }
        DNode<T> cur = tail;
        while (cur != null) {
            printer.visit(cur.data);
            cur = cur.prev;
        }
    }

    public int size()        { return size; }
    public boolean isEmpty() { return head == null; }
}
