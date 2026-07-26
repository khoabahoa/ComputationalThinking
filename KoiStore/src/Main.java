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
    // ── 10 KoiFish ────────────────────────────────────────────────────────────
    koiService.add(new KoiFish("F001", "Kohaku Tanaka",    "Japan",       "Red-White",         15000000, "available"));
    koiService.add(new KoiFish("F002", "Showa Ginrin",     "Japan",       "Black-Red-White",   22000000, "available"));
    koiService.add(new KoiFish("F003", "Ogon Silver",      "Germany",     "Silver",            18000000, "available"));
    koiService.add(new KoiFish("F004", "Bekko Shiro",      "Japan",       "White-Black",        9500000, "available"));
    koiService.add(new KoiFish("F005", "Asagi Nishikigoi", "Japan",       "Blue-Red",          31000000, "available"));
    koiService.add(new KoiFish("F006", "Utsuri Shiro",     "Japan",       "Black-White",       27500000, "available"));
    koiService.add(new KoiFish("F007", "Koromo Ai",        "Japan",       "Blue-Red-White",    19800000, "available"));
    koiService.add(new KoiFish("F008", "Chagoi Green",     "China",       "Olive-Green",       12000000, "available"));
    koiService.add(new KoiFish("F009", "Yamabuki Ogon",    "South Korea", "Golden-Yellow",     24000000, "available"));
    koiService.add(new KoiFish("F010", "Tancho Kohaku",    "Japan",       "White-Red-Crown",   35000000, "available"));

    // ── 10 Customers ──────────────────────────────────────────────────────────
    customerService.add(new Customer("C001", "Nguyen Van An",    "0901234567", "an@gmail.com"));
    customerService.add(new Customer("C002", "Tran Thi Bich",    "0912345678", "bich@gmail.com"));
    customerService.add(new Customer("C003", "Le Hoang Nam",     "0923456789", "nam@gmail.com"));
    customerService.add(new Customer("C004", "Pham Minh Quan",   "0934567890", "quan@gmail.com"));
    customerService.add(new Customer("C005", "Vo Thi Lan",       "0945678901", "lan@gmail.com"));
    customerService.add(new Customer("C006", "Dang Van Duc",     "0956789012", "duc@gmail.com"));
    customerService.add(new Customer("C007", "Nguyen Thi Mai",   "0967890123", "mai@gmail.com"));
    customerService.add(new Customer("C008", "Hoang Van Khanh",  "0978901234", "khanh@gmail.com"));
    customerService.add(new Customer("C009", "Bui Thi Thu",      "0989012345", "thu@gmail.com"));
    customerService.add(new Customer("C010", "Tran Van Phuc",    "0990123456", "phuc@gmail.com"));
}
}
