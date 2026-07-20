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
