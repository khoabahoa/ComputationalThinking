/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author pc
 */
import entity.Customer;
import entity.KoiFish;
import menu.AuctionMenu;
import menu.CustomerMenu;
import menu.KoiMenu;
import service.AuctionService;
import service.CustomerService;
import service.KoiService;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // ── Khởi tạo service ────────────────────────────────────────────────
        KoiService      koiService      = new KoiService();
        CustomerService customerService = new CustomerService();
        AuctionService  auctionService  = new AuctionService(koiService, customerService);

        // ── Nạp dữ liệu mẫu ────────────────────────────────────────────────
        loadSampleData(koiService, customerService);

        // ── Khởi tạo menu ───────────────────────────────────────────────────
        KoiMenu      koiMenu      = new KoiMenu(koiService, sc);
        CustomerMenu customerMenu = new CustomerMenu(customerService, sc);
        AuctionMenu  auctionMenu  = new AuctionMenu(auctionService, sc);

        // ── Menu chính ──────────────────────────────────────────────────────
        int choice;
        do {
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("║     CUA HANG CA KOI - MAIN       ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║  1. Quan ly ca Koi               ║");
            System.out.println("║  2. Quan ly khach hang           ║");
            System.out.println("║  3. Dau gia                      ║");
            System.out.println("║  0. Thoat                        ║");
            System.out.println("╚══════════════════════════════════╝");
            System.out.print("  Chon: ");
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                choice = -1;
            }
            switch (choice) {
                case 1: koiMenu.show();      break;
                case 2: customerMenu.show(); break;
                case 3: auctionMenu.show();  break;
                case 0: System.out.println("\n  Tam biet!"); break;
                default: System.out.println("  [!] Lua chon khong hop le.");
            }
        } while (choice != 0);

        sc.close();
    }

    // ── Dữ liệu mẫu để demo ─────────────────────────────────────────────────
    private static void loadSampleData(KoiService koiService,
                                       CustomerService customerService) {
        System.out.println("\n[SYSTEM] Dang nap du lieu mau...");

        // Cá Koi mẫu
        koiService.add(new KoiFish("F001", "Kohaku Tanaka",  "Nhat Ban", "Trang-Do",  15000000));
        koiService.add(new KoiFish("F002", "Showa Yamamoto", "Nhat Ban", "Den-Trang",  22000000));
        koiService.add(new KoiFish("F003", "Ogon Silver",    "Duc",      "Bac",        18000000));
        koiService.add(new KoiFish("F004", "Bekko Gold",     "Han Quoc", "Vang",        9500000));
        koiService.add(new KoiFish("F005", "Asagi Blue",     "Nhat Ban", "Xanh-Do",   31000000));

        // Khách hàng mẫu
        customerService.add(new Customer("C001", "Nguyen Van An",  "0901234567", "an@email.com"));
        customerService.add(new Customer("C002", "Tran Thi Bich",  "0912345678", "bich@email.com"));
        customerService.add(new Customer("C003", "Le Hoang Nam",   "0923456789", "nam@email.com"));
        customerService.add(new Customer("C004", "Pham Minh Quan", "0934567890", "quan@email.com"));

        System.out.println("[SYSTEM] Nap xong. San sang su dung.\n");
    }
}
