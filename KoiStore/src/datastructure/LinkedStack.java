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
public class LinkedStack<T> {
    private Node<T> top;
    private int size;

    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public T pop() {
        if (isEmpty()) return null;
        T value = top.data;
        top = top.next;
        size--;
        return value;
    }

    public T peek() {
        return isEmpty() ? null : top.data;
    }

    public boolean isEmpty() { return top == null; }
    public int size()        { return size; }
}
