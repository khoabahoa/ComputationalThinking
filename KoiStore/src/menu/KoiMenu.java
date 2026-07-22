package menu;

import entity.KoiFish;
import java.util.Scanner;
import service.KoiService;

public class KoiMenu {

    private final KoiService koiService;
    private final Scanner sc;

    public KoiMenu(KoiService koiService, Scanner sc) {
        this.koiService = koiService;
        this.sc = sc;
    }

    public void show() {
        int choice;
        do {
            System.out.println("\n===== KOI FISH MANAGEMENT =====");
            System.out.println("1. Add fish");
            System.out.println("2. Update fish");
            System.out.println("3. Delete fish");
            System.out.println("4. Search fish by ID");
            System.out.println("5. Search fish by color");
            System.out.println("6. List all fish");
            System.out.println("7. List fish sorted by ID (BST in-order)");
            System.out.println("8. Undo last delete");
            System.out.println("0. Back to main menu");
            System.out.print("Choose: ");
            choice = readInt();

            switch (choice) {
                case 1:
                    addFish();
                    break;
                case 2:
                    updateFish();
                    break;
                case 3:
                    deleteFish();
                    break;
                case 4:
                    searchById();
                    break;
                case 5:
                    searchByColor();
                    break;
                case 6:
                    System.out.println("-- All fish --");
                    koiService.listAll();
                    break;
                case 7:
                    System.out.println("-- Fish sorted by ID --");
                    koiService.listSortedById();
                    break;
                case 8:
                    koiService.undoDelete();
                    break;
                case 0:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println(" [!] Invalid choice.");
            }
        } while (choice != 0);
    }

    private void addFish() {
        System.out.print("Fish ID: ");
        String id = sc.nextLine().trim();
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Origin: ");
        String origin = sc.nextLine().trim();
        System.out.print("Color: ");
        String color = sc.nextLine().trim();
        double price = readDouble("Price (VND): ");
        koiService.add(new KoiFish(id, name, origin, color, price, "available"));
    }

    private void updateFish() {
        System.out.print("Fish ID to update: ");
        String id = sc.nextLine().trim();
        System.out.print("New name: ");
        String name = sc.nextLine().trim();
        System.out.print("New origin: ");
        String origin = sc.nextLine().trim();
        System.out.print("New color: ");
        String color = sc.nextLine().trim();
        double price = readDouble("New price (VND): ");
        koiService.update(id, name, origin, color, price);
    }

    private void deleteFish() {
        System.out.print("Fish ID to delete: ");
        String id = sc.nextLine().trim();
        koiService.delete(id);
    }

    private void searchById() {
        System.out.print("Fish ID: ");
        String id = sc.nextLine().trim();
        KoiFish fish = koiService.findById(id);
        if (fish == null) {
            System.out.println(" [!] Fish not found: " + id);
        } else {
            fish.display();
        }
    }

    private void searchByColor() {
        System.out.print("Color: ");
        String color = sc.nextLine().trim();
        koiService.searchByColor(color);
    }

    private int readInt() {
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a number: ");
            sc.next();
        }
        int val = sc.nextInt();
        sc.nextLine(); // consume leftover newline
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