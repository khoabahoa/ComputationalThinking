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
public class BinarySearchTree<T> {

    private TreeNode<T> root;
    private final IdExtractor<T> keyExtractor;

    public BinarySearchTree(IdExtractor<T> keyExtractor) {
        this.keyExtractor = keyExtractor;
    }

    private static class TreeNode<T> {
        T data;
        TreeNode<T> left;
        TreeNode<T> right;

        TreeNode(T data) {
            this.data  = data;
            this.left  = null;
            this.right = null;
        }
    }

    // So sánh 2 chuỗi không dùng compareTo của thư viện
    private int compareStr(String a, String b) {
        int len = a.length() < b.length() ? a.length() : b.length();
        for (int i = 0; i < len; i++) {
            if (a.charAt(i) != b.charAt(i))
                return a.charAt(i) - b.charAt(i);
        }
        return a.length() - b.length();
    }

    public void insert(T data) {
        root = insertRec(root, data);
    }

    private TreeNode<T> insertRec(TreeNode<T> node, T data) {
        if (node == null) return new TreeNode<>(data);
        int cmp = compareStr(keyExtractor.getId(data), keyExtractor.getId(node.data));
        if      (cmp < 0) node.left  = insertRec(node.left,  data);
        else if (cmp > 0) node.right = insertRec(node.right, data);
        // cmp == 0: trùng key, bỏ qua (update ở service)
        return node;
    }

    public T search(String key) {
        TreeNode<T> cur = root;
        while (cur != null) {
            int cmp = compareStr(key, keyExtractor.getId(cur.data));
            if      (cmp == 0) return cur.data;
            else if (cmp < 0)  cur = cur.left;
            else               cur = cur.right;
        }
        return null;
    }

    public void delete(String key) {
        root = deleteRec(root, key);
    }

    private TreeNode<T> deleteRec(TreeNode<T> node, String key) {
        if (node == null) return null;
        int cmp = compareStr(key, keyExtractor.getId(node.data));
        if      (cmp < 0) node.left  = deleteRec(node.left,  key);
        else if (cmp > 0) node.right = deleteRec(node.right, key);
        else {
            if (node.left  == null) return node.right;
            if (node.right == null) return node.left;
            // Tìm node nhỏ nhất bên phải (in-order successor)
            TreeNode<T> min = node.right;
            while (min.left != null) min = min.left;
            node.data  = min.data;
            node.right = deleteRec(node.right, keyExtractor.getId(min.data));
        }
        return node;
    }

    // In theo thứ tự tăng dần của key
    public void inOrder(Visitor<T> printer) {
        inOrderRec(root, printer);
    }

    private void inOrderRec(TreeNode<T> node, Visitor<T> printer) {
        if (node == null) return;
        inOrderRec(node.left,  printer);
        printer.visit(node.data);
        inOrderRec(node.right, printer);
    }

    public boolean isEmpty() { return root == null; }
}
