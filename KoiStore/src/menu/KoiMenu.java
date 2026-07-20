/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu;

/**
 *
 * @author pc
 */
import entity.KoiFish;
import service.KoiService;
import java.util.Scanner;

public class KoiMenu {
    private KoiService koiService;
    private Scanner sc;

    public KoiMenu(KoiService koiService, Scanner sc) {
        this.koiService = koiService;
        this.sc         = sc;
    }

    public void show() {
        int choice;
        do {
            System.out.println("\n========== QUAN LY CA KOI ==========");
            System.out.println("  1. Them ca moi");
            System.out.println("  2. Xoa ca");
            System.out.println("  3. Cap nhat ca");
            System.out.println("  4. Tim ca theo ID");
            System.out.println("  5. Tim ca theo mau sac");
            System.out.println("  6. Hien thi tat ca (thu tu nhap)");
            System.out.println("  7. Hien thi tat ca (tang dan ID)");
            System.out.println("  8. Thong ke");
            System.out.println("  9. Undo thao tac truoc");
            System.out.println("  0. Quay lai");
            System.out.print("  Chon: ");
            choice = readInt();
            switch (choice) {
                case 1: addFish();           break;
                case 2: deleteFish();        break;
                case 3: updateFish();        break;
                case 4: findById();          break;
                case 5: findByColor();       break;
                case 6: koiService.displayAll();          break;
                case 7: koiService.displaySortedById();   break;
                case 8: koiService.statistics();          break;
                case 9: koiService.undo();                break;
                case 0: break;
                default: System.out.println("  [!] Lua chon khong hop le.");
            }
        } while (choice != 0);
    }

    private void addFish() {
        System.out.println("\n-- Them ca moi --");
        System.out.print("  Fish ID   : "); String fishId = sc.nextLine().trim();
        System.out.print("  Ten ca    : "); String name   = sc.nextLine().trim();
        System.out.print("  Xuat xu   : "); String origin = sc.nextLine().trim();
        System.out.print("  Mau sac   : "); String color  = sc.nextLine().trim();
        System.out.print("  Gia (VND) : "); double price  = readDouble();
        koiService.add(new KoiFish(fishId, name, origin, color, price));
    }

    private void deleteFish() {
        System.out.println("\n-- Xoa ca --");
        System.out.print("  Fish ID: "); String fishId = sc.nextLine().trim();
        koiService.delete(fishId);
    }

    private void updateFish() {
        System.out.println("\n-- Cap nhat ca --");
        System.out.print("  Fish ID       : "); String fishId   = sc.nextLine().trim();
        System.out.print("  Gia moi (VND) : "); double newPrice = readDouble();
        System.out.print("  Mau moi       : "); String newColor = sc.nextLine().trim();
        koiService.update(fishId, newPrice, newColor);
    }

    private void findById() {
        System.out.print("  Fish ID: "); String fishId = sc.nextLine().trim();
        KoiFish f = koiService.findById(fishId);
        if (f != null) f.display();
    }

    private void findByColor() {
        System.out.print("  Mau sac: "); String color = sc.nextLine().trim();
        koiService.findByColor(color);
    }

    // ── Helper đọc số an toàn ───────────────────────────────────────────────
    private int readInt() {
        try {
            int v = Integer.parseInt(sc.nextLine().trim());
            return v;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private double readDouble() {
        try {
            double v = Double.parseDouble(sc.nextLine().trim());
            return v;
        } catch (NumberFormatException e) {
            System.out.println("  [!] Gia tri khong hop le, mac dinh 0.");
            return 0;
        }
    }
}
