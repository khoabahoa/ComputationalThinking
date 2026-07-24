import entity.Customer;
import entity.KoiFish;
import java.util.Scanner;
import menu.AuctionMenu;
import menu.CustomerMenu;
import menu.KoiMenu;
import service.AuctionService;
import service.CustomerService;
import service.KoiService;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        KoiService koiService = new KoiService();
        CustomerService customerService = new CustomerService();
        AuctionService auctionService = new AuctionService(koiService, customerService);

        seedSampleData(koiService, customerService);

        KoiMenu koiMenu = new KoiMenu(koiService, sc);
        CustomerMenu customerMenu = new CustomerMenu(customerService, sc);
        AuctionMenu auctionMenu = new AuctionMenu(auctionService, sc);

        int choice;
        do {
            System.out.println("\n========== KOI FISH SHOP & AUCTION SYSTEM ==========");
            System.out.println("1. Koi Fish Management");
            System.out.println("2. Customer Management");
            System.out.println("3. Auction Management");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            while (!sc.hasNextInt()) {
                System.out.print("Please enter a number: ");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    koiMenu.show();
                    break;
                case 2:
                    customerMenu.show();
                    break;
                case 3:
                    auctionMenu.show();
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println(" [!] Invalid choice.");
            }
        } while (choice != 0);

        sc.close();
    }

    /** A little starter data so the menus have something to work with right away. */
    private static void seedSampleData(KoiService koiService, CustomerService customerService) {
        koiService.add(new KoiFish("F001", "Kohaku Tanaka", "Japan", "Red-White", 15000000, "available"));
        koiService.add(new KoiFish("F002", "Showa Ginrin", "Japan", "Black-Red-White", 22000000, "available"));
        customerService.add(new Customer("C001", "Nguyen Van A", "0901234567", "a@gmail.com"));
        customerService.add(new Customer("C002", "Tran Thi Bich", "0912345678", "bich@gmail.com"));
    }
}
