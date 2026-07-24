package service;

import datastructure.BinarySearchTree;
import datastructure.LinkedStack;
import datastructure.SinglyLinkedList;
import entity.KoiFish;

public class KoiService {

    // master record list (insertion order) + BST for fast id lookup
    private final SinglyLinkedList<KoiFish> list = new SinglyLinkedList<>();
    private final BinarySearchTree<KoiFish> bst = new BinarySearchTree<>(KoiFish::getFishId);
    // stores a clone of any deleted fish so the last delete can be undone
    private final LinkedStack<KoiFish> undoStack = new LinkedStack<>();

    public boolean exists(String fishId) {
        return bst.search(fishId) != null;
    }

    public KoiFish findById(String fishId) {
        return bst.search(fishId);
    }

    public boolean add(KoiFish fish) {
        if (exists(fish.getFishId())) {
            System.out.println(" [!] Fish ID already exists: " + fish.getFishId());
            return false;
        }
        list.addLast(fish);
        bst.insert(fish);
        System.out.println(" [OK] Added fish " + fish.getFishId());
        return true;
    }

    public boolean update(String fishId, String name, String origin, String color, double price) {
        KoiFish fish = bst.search(fishId);
        if (fish == null) {
            System.out.println(" [!] Fish not found: " + fishId);
            return false;
        }
        fish.setName(name);
        fish.setOrigin(origin);
        fish.setColor(color);
        fish.setPrice(price);
        System.out.println(" [OK] Updated fish " + fishId);
        return true;
    }

    public boolean delete(String fishId) {
        KoiFish fish = bst.search(fishId);
        if (fish == null) {
            System.out.println(" [!] Fish not found: " + fishId);
            return false;
        }
        undoStack.push(fish.clone());
        list.deleteById(fishId, KoiFish::getFishId);
        bst.delete(fishId);
        System.out.println(" [OK] Deleted fish " + fishId);
        return true;
    }

    /** Restores the most recently deleted fish, if any. */
    public boolean undoDelete() {
        KoiFish fish = undoStack.pop();
        if (fish == null) {
            System.out.println(" [!] Nothing to undo.");
            return false;
        }
        list.addLast(fish);
        bst.insert(fish);
        System.out.println(" [OK] Restored fish " + fish.getFishId());
        return true;
    }

    public void searchByColor(String color) {
        int count = list.displayIf(f -> f.getColor().equalsIgnoreCase(color));
        if (count == 0) {
            System.out.println(" (no fish with color " + color + ")");
        }
    }

    public void listAll() {
        if (list.isEmpty()) {
            System.out.println(" (no fish yet)");
            return;
        }
        list.traverse();
    }

    public void listSortedById() {
        if (list.isEmpty()) {
            System.out.println(" (no fish yet)");
            return;
        }
        bst.inOrder();
    }
}
