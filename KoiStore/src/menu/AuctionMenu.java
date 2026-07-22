package menu;

import java.util.Scanner;
import service.AuctionService;

public class AuctionMenu {

    private final AuctionService auctionService;
    private final Scanner sc;

    public AuctionMenu(AuctionService auctionService, Scanner sc) {
        this.auctionService = auctionService;
        this.sc = sc;
    }

    public void show() {
        int choice;
        do {
            System.out.println("\n===== AUCTION MANAGEMENT =====");
            System.out.println("1. Open session");
            System.out.println("2. Close session");
            System.out.println("3. Place bid");
            System.out.println("4. Show bid history for a session");
            System.out.println("5. List all sessions");
            System.out.println("6. View closed sessions (forward)");
            System.out.println("7. View closed sessions (backward)");
            System.out.println("0. Back to main menu");
            System.out.print("Choose: ");
            choice = readInt();

            switch (choice) {
                case 1:
                    openSession();
                    break;
                case 2:
                    closeSession();
                    break;
                case 3:
                    placeBid();
                    break;
                case 4:
                    showBidHistory();
                    break;
                case 5:
                    System.out.println("-- All sessions --");
                    auctionService.listAllSessions();
                    break;
                case 6:
                    System.out.println("-- Closed sessions (oldest -> newest) --");
                    auctionService.viewClosedHistoryForward();
                    break;
                case 7:
                    System.out.println("-- Closed sessions (newest -> oldest) --");
                    auctionService.viewClosedHistoryBackward();
                    break;
                case 0:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println(" [!] Invalid choice.");
            }
        } while (choice != 0);
    }

    private void openSession() {
        System.out.print("Session ID: ");
        String sessionId = sc.nextLine().trim();
        System.out.print("Fish ID: ");
        String fishId = sc.nextLine().trim();
        double startPrice = readDouble("Start price (VND): ");
        auctionService.openSession(sessionId, fishId, startPrice);
    }

    private void closeSession() {
        System.out.print("Session ID to close: ");
        String sessionId = sc.nextLine().trim();
        auctionService.closeSession(sessionId);
    }

    private void placeBid() {
        System.out.print("Session ID: ");
        String sessionId = sc.nextLine().trim();
        System.out.print("Customer ID: ");
        String customerId = sc.nextLine().trim();
        double amount = readDouble("Bid amount (VND): ");
        System.out.print("Time (e.g. 09:15): ");
        String time = sc.nextLine().trim();
        auctionService.placeBid(sessionId, customerId, amount, time);
    }

    private void showBidHistory() {
        System.out.print("Session ID: ");
        String sessionId = sc.nextLine().trim();
        auctionService.showBidHistory(sessionId);
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

    private double readDouble(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextDouble()) {
            System.out.print("Please enter a valid number: ");
            sc.next();
        }
        double val = sc.nextDouble();
        sc.nextLine();
        return val;
    }
}