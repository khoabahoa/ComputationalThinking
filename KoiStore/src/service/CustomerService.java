/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author pc
 */
import datastructure.BinarySearchTree;
import datastructure.IdExtractor;
import datastructure.LinkedStack;
import datastructure.SinglyLinkedList;
import datastructure.Visitor;
import entity.Customer;

public class CustomerService {

    private final SinglyLinkedList<Customer> customerList = new SinglyLinkedList<>();
    private final BinarySearchTree<Customer> customerTree = new BinarySearchTree<>(
        (Customer c) -> c.getCustomerId());
    private final LinkedStack<Customer> undoStack = new LinkedStack<>();

    // ── THÊM ────────────────────────────────────────────────────────────────
    public boolean add(Customer customer) {
        if (customerTree.search(customer.getCustomerId()) != null) {
            System.out.println("  [!] CustomerId da ton tai: " + customer.getCustomerId());
            return false;
        }
        customerList.addLast(customer);
        customerTree.insert(customer);
        System.out.println("  [OK] Da them khach hang: " + customer.getCustomerId());
        return true;
    }

    // ── XOÁ ─────────────────────────────────────────────────────────────────
    public boolean delete(String customerId) {
        Customer found = customerTree.search(customerId);
        if (found == null) {
            System.out.println("  [!] Khong tim thay khach: " + customerId);
            return false;
        }
        undoStack.push(new Customer(
            found.getCustomerId(), found.getName(),
            found.getPhone(),      found.getEmail()
        ));
        customerList.deleteById(customerId, new IdExtractor<Customer>() {
            public String getId(Customer c) { return c.getCustomerId(); }
        });
        customerTree.delete(customerId);
        System.out.println("  [OK] Da xoa khach: " + customerId);
        return true;
    }

    // ── CẬP NHẬT ────────────────────────────────────────────────────────────
    public boolean update(String customerId, String newPhone, String newEmail) {
        Customer found = customerTree.search(customerId);
        if (found == null) {
            System.out.println("  [!] Khong tim thay khach: " + customerId);
            return false;
        }
        undoStack.push(new Customer(
            found.getCustomerId(), found.getName(),
            found.getPhone(),      found.getEmail()
        ));
        found.setPhone(newPhone);
        found.setEmail(newEmail);
        System.out.println("  [OK] Da cap nhat khach: " + customerId);
        return true;
    }

    // ── UNDO ────────────────────────────────────────────────────────────────
    public void undo() {
        Customer snapshot = undoStack.pop();
        if (snapshot == null) {
            System.out.println("  [!] Khong co thao tac nao de undo.");
            return;
        }
        Customer inTree = customerTree.search(snapshot.getCustomerId());
        if (inTree != null) {
            inTree.setPhone(snapshot.getPhone());
            inTree.setEmail(snapshot.getEmail());
            System.out.println("  [OK] Da undo cap nhat khach: " + snapshot.getCustomerId());
        } else {
            customerList.addLast(snapshot);
            customerTree.insert(snapshot);
            System.out.println("  [OK] Da undo xoa khach: " + snapshot.getCustomerId());
        }
    }

    // ── TÌM KIẾM ────────────────────────────────────────────────────────────
    public Customer findById(String customerId) {
        Customer found = customerTree.search(customerId);
        if (found == null) System.out.println("  [!] Khong tim thay khach: " + customerId);
        return found;
    }

    public void findByName(String keyword) {
        System.out.println("  --- Ket qua tim theo ten: " + keyword + " ---");
        final boolean[] found = {false};
        customerList.traverse(new Visitor<Customer>() {
            public void visit(Customer c) {
                if (c.getName().toLowerCase().contains(keyword.toLowerCase())) {
                    c.display();
                    found[0] = true;
                }
            }
        });
        if (!found[0]) System.out.println("  Khong tim thay.");
    }

    // ── HIỂN THỊ ────────────────────────────────────────────────────────────
    public void displayAll() {
        System.out.println("  --- Danh sach khach hang ---");
        customerList.traverse(new Visitor<Customer>() {
            public void visit(Customer c) { c.display(); }
        });
    }

    public void displaySortedById() {
        System.out.println("  --- Khach hang (theo ID tang dan) ---");
        customerTree.inOrder(new Visitor<Customer>() {
            public void visit(Customer c) { c.display(); }
        });
    }

    // ── DÙNG NỘI BỘ ─────────────────────────────────────────────────────────
    public boolean exists(String customerId) {
        return customerTree.search(customerId) != null;
    }
}
