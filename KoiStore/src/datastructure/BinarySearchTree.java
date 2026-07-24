package datastructure;

import entity.Displayable;

/**
 * Custom Binary Search Tree keyed by a String id (fishId / customerId).
 * Gives O(log n) average search instead of the O(n) linear scan of the list.
 * Not self-balancing (see course slides on AVL trees for a future upgrade).
 */
public class BinarySearchTree<T extends Displayable> {

    private TreeNode<T> root;
    private final IdExtractor<T> keyExtractor;

    public BinarySearchTree(IdExtractor<T> keyExtractor) {
        this.root = null;
        this.keyExtractor = keyExtractor;
    }

    /** Custom string comparison, no String.compareTo() needed. */
    private int compareStr(String a, String b) {
        int len = Math.min(a.length(), b.length());
        for (int i = 0; i < len; i++) {
            if (a.charAt(i) != b.charAt(i)) {
                return a.charAt(i) - b.charAt(i);
            }
        }
        return a.length() - b.length();
    }

    public void insert(T data) {
        root = insertRec(root, data);
    }

    private TreeNode<T> insertRec(TreeNode<T> node, T data) {
        if (node == null) {
            return new TreeNode<>(data);
        }
        int cmp = compareStr(keyExtractor.getId(data), keyExtractor.getId(node.data));
        if (cmp < 0) {
            node.left = insertRec(node.left, data);
        } else if (cmp > 0) {
            node.right = insertRec(node.right, data);
        } else {
            node.data = data; // same id -> overwrite (used after edit)
        }
        return node;
    }

    /** Iterative search, O(log n) average / O(n) worst case if unbalanced. */
    public T search(String key) {
        TreeNode<T> cur = root;
        while (cur != null) {
            int cmp = compareStr(key, keyExtractor.getId(cur.data));
            if (cmp == 0) {
                return cur.data;
            } else if (cmp < 0) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        return null;
    }

    public void delete(String key) {
        root = deleteRec(root, key);
    }

    private TreeNode<T> deleteRec(TreeNode<T> node, String key) {
        if (node == null) {
            return null;
        }
        int cmp = compareStr(key, keyExtractor.getId(node.data));
        if (cmp < 0) {
            node.left = deleteRec(node.left, key);
        } else if (cmp > 0) {
            node.right = deleteRec(node.right, key);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            // two children: replace with in-order successor (smallest in right subtree)
            TreeNode<T> successor = node.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            node.data = successor.data;
            node.right = deleteRec(node.right, keyExtractor.getId(successor.data));
        }
        return node;
    }

    /** In-order traversal prints records sorted by id. */
    public void inOrder() {
        inOrderRec(root);
    }

    private void inOrderRec(TreeNode<T> node) {
        if (node == null) {
            return;
        }
        inOrderRec(node.left);
        node.data.display();
        inOrderRec(node.right);
    }
}
