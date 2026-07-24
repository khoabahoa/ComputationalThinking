package service;

import datastructure.BinarySearchTree;
import datastructure.LinkedStack;
import datastructure.SinglyLinkedList;
import entity.Customer;

public class CustomerService {

    private final SinglyLinkedList<Customer> list = new SinglyLinkedList<>();
    private final BinarySearchTree<Customer> bst = new BinarySearchTree<>(Customer::getCustomerId);
    private final LinkedStack<Customer> undoStack = new LinkedStack<>();

    public boolean exists(String customerId) {
        return bst.search(customerId) != null;
    }

    public Customer findById(String customerId) {
        return bst.search(customerId);
    }

    public boolean add(Customer c) {
        if (exists(c.getCustomerId())) {
            System.out.println(" [!] Customer ID already exists: " + c.getCustomerId());
            return false;
        }
        list.addLast(c);
        bst.insert(c);
        System.out.println(" [OK] Added customer " + c.getCustomerId());
        return true;
    }

    public boolean update(String customerId, String name, String phone, String email) {
        Customer c = bst.search(customerId);
        if (c == null) {
            System.out.println(" [!] Customer not found: " + customerId);
            return false;
        }
        c.setName(name);
        c.setPhone(phone);
        c.setEmail(email);
        System.out.println(" [OK] Updated customer " + customerId);
        return true;
    }

    public boolean delete(String customerId) {
        Customer c = bst.search(customerId);
        if (c == null) {
            System.out.println(" [!] Customer not found: " + customerId);
            return false;
        }
        undoStack.push(c.clone());
        list.deleteById(customerId, Customer::getCustomerId);
        bst.delete(customerId);
        System.out.println(" [OK] Deleted customer " + customerId);
        return true;
    }

    public boolean undoDelete() {
        Customer c = undoStack.pop();
        if (c == null) {
            System.out.println(" [!] Nothing to undo.");
            return false;
        }
        list.addLast(c);
        bst.insert(c);
        System.out.println(" [OK] Restored customer " + c.getCustomerId());
        return true;
    }

    public void searchByNameKeyword(String keyword) {
        int count = list.displayIf(c -> c.getName().toLowerCase().contains(keyword.toLowerCase()));
        if (count == 0) {
            System.out.println(" (no customer matches \"" + keyword + "\")");
        }
    }

    public void listAll() {
        if (list.isEmpty()) {
            System.out.println(" (no customers yet)");
            return;
        }
        list.traverse();
    }

    public void listSortedById() {
        if (list.isEmpty()) {
            System.out.println(" (no customers yet)");
            return;
        }
        bst.inOrder();
    }
}
