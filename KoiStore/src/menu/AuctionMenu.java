/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu;

/**
 *
 * @author pc
 */
import service.AuctionService;
import java.util.Scanner;

public class AuctionMenu {
    private AuctionService auctionService;
    private Scanner sc;

    public AuctionMenu(AuctionService auctionService, Scanner sc) {
        this.auctionService = auctionService;
        this.sc             = sc;
    }



    public void show() {
        int choice;
        do {
            System.out.println("\n========== DAU GIA CA KOI ==========");
            System.out.println("  1. Tao phien dau gia");
            System.out.println("  2. Dat gia");
            System.out.println("  3. Xem lich su dat gia");
            System.out.println("  4. Ket thuc phien");
            System.out.println("  5. Huy phien");
            System.out.println("  6. Hien thi tat ca phien");
            System.out.println("  7. Hien thi phien dang mo");
            System.out.println("  8. Lich su phien (cu -> moi)");
            System.out.println("  9. Lich su phien (moi -> cu)");
            System.out.println("  0. Quay lai");
            System.out.print("  Chon: ");
            choice = readInt();
            switch (choice) {
                case 1: createSession();   break;
                case 2: placeBid();        break;
                case 3: showBidHistory();  break;
                case 4: closeSession();    break;
                case 5: cancelSession();   break;
                case 6: auctionService.displayAllSessions();     break;
                case 7: auctionService.displayOpenSessions();    break;
                case 8: auctionService.displayHistoryForward();  break;
                case 9: auctionService.displayHistoryBackward(); break;
                case 0: break;
                default: System.out.println("  [!] Lua chon khong hop le.");
            }
        } while (choice != 0);
    }

    private void createSession() {
        System.out.println("\n-- Tao phien dau gia --");
        System.out.print("  Session ID        : "); String sid        = sc.nextLine().trim();
        System.out.print("  Fish ID           : "); String fishId     = sc.nextLine().trim();
        System.out.print("  Gia khoi diem (VND): "); double startPrice = readDouble();
        auctionService.createSession(sid, fishId, startPrice);
    }

    private void placeBid() {
        System.out.println("\n-- Dat gia --");
        System.out.print("  Session ID   : "); String sid        = sc.nextLine().trim();
        System.out.print("  Customer ID  : "); String customerId = sc.nextLine().trim();
        System.out.print("  So tien (VND): "); double amount     = readDouble();
        System.out.print("  Thoi gian    : "); String time       = sc.nextLine().trim();
        auctionService.placeBid(sid, customerId, amount, time);
    }

    private void showBidHistory() {
        System.out.print("  Session ID: "); String sid = sc.nextLine().trim();
        auctionService.showBidHistory(sid);
    }

    private void closeSession() {
        System.out.print("  Session ID: "); String sid = sc.nextLine().trim();
        auctionService.closeSession(sid);
    }

    private void cancelSession() {
        System.out.print("  Session ID: "); String sid = sc.nextLine().trim();
        auctionService.cancelSession(sid);
    }

    private int readInt() {
        try { return Integer.parseInt(sc.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    private double readDouble() {
        try { return Double.parseDouble(sc.nextLine().trim()); }
        catch (NumberFormatException e) {
            System.out.println("  [!] Gia tri khong hop le, mac dinh 0.");
            return 0;
        }
    }
}
