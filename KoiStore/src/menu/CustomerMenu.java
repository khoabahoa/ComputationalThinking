/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu;

/**
 *
 * @author pc
 */
import entity.Customer;
import service.CustomerService;
import java.util.Scanner;

public class CustomerMenu {
    private CustomerService customerService;
    private Scanner sc;

    public CustomerMenu(CustomerService customerService, Scanner sc) {
        this.customerService = customerService;
        this.sc              = sc;
    }

    public void show() {
        int choice;
        do {
            System.out.println("\n========== QUAN LY KHACH HANG ==========");
            System.out.println("  1. Them khach hang");
            System.out.println("  2. Xoa khach hang");
            System.out.println("  3. Cap nhat khach hang");
            System.out.println("  4. Tim theo ID");
            System.out.println("  5. Tim theo ten");
            System.out.println("  6. Hien thi tat ca (thu tu nhap)");
            System.out.println("  7. Hien thi tat ca (tang dan ID)");
            System.out.println("  8. Undo thao tac truoc");
            System.out.println("  0. Quay lai");
            System.out.print("  Chon: ");
            choice = readInt();
            switch (choice) {
                case 1: addCustomer();    break;
                case 2: deleteCustomer(); break;
                case 3: updateCustomer(); break;
                case 4: findById();       break;
                case 5: findByName();     break;
                case 6: customerService.displayAll();         break;
                case 7: customerService.displaySortedById();  break;
                case 8: customerService.undo();               break;
                case 0: break;
                default: System.out.println("  [!] Lua chon khong hop le.");
            }
        } while (choice != 0);
    }

    private void addCustomer() {
        System.out.println("\n-- Them khach hang --");
        System.out.print("  Customer ID: "); String id    = sc.nextLine().trim();
        System.out.print("  Ho ten     : "); String name  = sc.nextLine().trim();
        System.out.print("  So dien thoai: "); String phone = sc.nextLine().trim();
        System.out.print("  Email      : "); String email = sc.nextLine().trim();
        customerService.add(new Customer(id, name, phone, email));
    }

    private void deleteCustomer() {
        System.out.print("  Customer ID: "); String id = sc.nextLine().trim();
        customerService.delete(id);
    }

    private void updateCustomer() {
        System.out.print("  Customer ID    : "); String id       = sc.nextLine().trim();
        System.out.print("  So dien thoai moi: "); String newPhone = sc.nextLine().trim();
        System.out.print("  Email moi      : "); String newEmail = sc.nextLine().trim();
        customerService.update(id, newPhone, newEmail);
    }

    private void findById() {
        System.out.print("  Customer ID: "); String id = sc.nextLine().trim();
        Customer c = customerService.findById(id);
        if (c != null) c.display();
    }

    private void findByName() {
        System.out.print("  Tu khoa ten: "); String keyword = sc.nextLine().trim();
        customerService.findByName(keyword);
    }

    private int readInt() {
        try { return Integer.parseInt(sc.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }
}
