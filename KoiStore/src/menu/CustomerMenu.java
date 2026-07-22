package menu;

import entity.Customer;
import java.util.Scanner;
import service.CustomerService;

public class CustomerMenu {

    private final CustomerService customerService;
    private final Scanner sc;

    public CustomerMenu(CustomerService customerService, Scanner sc) {
        this.customerService = customerService;
        this.sc = sc;
    }

    public void show() {
        int choice;
        do {
            System.out.println("\n===== CUSTOMER MANAGEMENT =====");
            System.out.println("1. Add customer");
            System.out.println("2. Update customer");
            System.out.println("3. Delete customer");
            System.out.println("4. Search customer by ID");
            System.out.println("5. Search customer by name keyword");
            System.out.println("6. List all customers");
            System.out.println("7. List customers sorted by ID (BST in-order)");
            System.out.println("8. Undo last delete");
            System.out.println("0. Back to main menu");
            System.out.print("Choose: ");
            choice = readInt();

            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    updateCustomer();
                    break;
                case 3:
                    deleteCustomer();
                    break;
                case 4:
                    searchById();
                    break;
                case 5:
                    searchByName();
                    break;
                case 6:
                    System.out.println("-- All customers --");
                    customerService.listAll();
                    break;
                case 7:
                    System.out.println("-- Customers sorted by ID --");
                    customerService.listSortedById();
                    break;
                case 8:
                    customerService.undoDelete();
                    break;
                case 0:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println(" [!] Invalid choice.");
            }
        } while (choice != 0);
    }

    private void addCustomer() {
        System.out.print("Customer ID: ");
        String id = sc.nextLine().trim();
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Phone: ");
        String phone = sc.nextLine().trim();
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        customerService.add(new Customer(id, name, phone, email));
    }

    private void updateCustomer() {
        System.out.print("Customer ID to update: ");
        String id = sc.nextLine().trim();
        System.out.print("New name: ");
        String name = sc.nextLine().trim();
        System.out.print("New phone: ");
        String phone = sc.nextLine().trim();
        System.out.print("New email: ");
        String email = sc.nextLine().trim();
        customerService.update(id, name, phone, email);
    }

    private void deleteCustomer() {
        System.out.print("Customer ID to delete: ");
        String id = sc.nextLine().trim();
        customerService.delete(id);
    }

    private void searchById() {
        System.out.print("Customer ID: ");
        String id = sc.nextLine().trim();
        Customer c = customerService.findById(id);
        if (c == null) {
            System.out.println(" [!] Customer not found: " + id);
        } else {
            c.display();
        }
    }

    private void searchByName() {
        System.out.print("Name keyword: ");
        String keyword = sc.nextLine().trim();
        customerService.searchByNameKeyword(keyword);
    }

    private int readInt() {
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a number: ");
            sc.next();
        }
        int val = sc.nextInt();
        sc.nextLine();
        return val;
    }
}