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
public class LinkedQueue<T> {
    private Node<T> front;
    private Node<T> rear;
    private int size;

    public void enqueue(T data) {
        Node<T> newNode = new Node<>(data);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()) return null;
        T value = front.data;
        front = front.next;
        if (front == null) rear = null;
        size--;
        return value;
    }

    public T peek()          { return isEmpty() ? null : front.data; }
    public boolean isEmpty() { return front == null; }
    public int size()        { return size; }

    // Duyệt toàn bộ hàng đợi không phá cấu trúc
    public void traverse(Visitor<T> printer) {
        if (front == null) {
            System.out.println("  (Hang doi trong)");
            return;
        }
        Node<T> cur = front;
        while (cur != null) {
            printer.visit(cur.data);
            cur = cur.next;
        }
    }
}
