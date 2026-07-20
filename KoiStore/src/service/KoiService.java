/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import datastructure.BinarySearchTree;
import datastructure.Condition;
import datastructure.IdExtractor;
import datastructure.LinkedStack;
import datastructure.SinglyLinkedList;
import datastructure.Visitor;
import entity.KoiFish;

/**
 *
 * @author pc
 */
public class KoiService {

    private final SinglyLinkedList<KoiFish> fishList = new SinglyLinkedList<>();
    private final BinarySearchTree<KoiFish> fishTree = new BinarySearchTree<>(
        (KoiFish f) -> f.getFishId());
    private final LinkedStack<KoiFish> undoStack = new LinkedStack<>();

    // ── THÊM ────────────────────────────────────────────────────────────────
    public boolean add(KoiFish fish) {
        if (fishTree.search(fish.getFishId()) != null) {
            System.out.println("  [!] FishId da ton tai: " + fish.getFishId());
            return false;
        }
        fishList.addLast(fish);
        fishTree.insert(fish);
        System.out.println("  [OK] Da them ca: " + fish.getFishId());
        return true;
    }

    // ── XOÁ ─────────────────────────────────────────────────────────────────
    public boolean delete(String fishId) {
        KoiFish found = fishTree.search(fishId);
        if (found == null) {
            System.out.println("  [!] Khong tim thay ca: " + fishId);
            return false;
        }
        if (found.getStatus().equals("auctioning")) {
            System.out.println("  [!] Ca dang dau gia, khong the xoa.");
            return false;
        }
        undoStack.push(found.clone()); // lưu bản sao để undo
        fishList.deleteById(fishId, new IdExtractor<KoiFish>() {
            public String getId(KoiFish f) { return f.getFishId(); }
        });
        fishTree.delete(fishId);
        System.out.println("  [OK] Da xoa ca: " + fishId);
        return true;
    }

    // ── CẬP NHẬT ────────────────────────────────────────────────────────────
    public boolean update(String fishId, double newPrice, String newColor) {
        KoiFish found = fishTree.search(fishId);
        if (found == null) {
            System.out.println("  [!] Khong tim thay ca: " + fishId);
            return false;
        }
        undoStack.push(found.clone()); // lưu trạng thái cũ
        found.setPrice(newPrice);
        found.setColor(newColor);
        System.out.println("  [OK] Da cap nhat ca: " + fishId);
        return true;
    }

    // ── UNDO ────────────────────────────────────────────────────────────────
    public void undo() {
        KoiFish snapshot = undoStack.pop();
        if (snapshot == null) {
            System.out.println("  [!] Khong co thao tac nao de undo.");
            return;
        }
        // Nếu cá vẫn còn trong list → restore thuộc tính
        KoiFish inList = fishTree.search(snapshot.getFishId());
        if (inList != null) {
            inList.setPrice(snapshot.getPrice());
            inList.setColor(snapshot.getColor());
            inList.setStatus(snapshot.getStatus());
            System.out.println("  [OK] Da undo cap nhat ca: " + snapshot.getFishId());
        } else {
            // Cá đã bị xoá → thêm lại
            fishList.addLast(snapshot);
            fishTree.insert(snapshot);
            System.out.println("  [OK] Da undo xoa ca: " + snapshot.getFishId());
        }
    }

    // ── TÌM KIẾM ────────────────────────────────────────────────────────────
    public KoiFish findById(String fishId) {
        KoiFish found = fishTree.search(fishId);
        if (found == null) System.out.println("  [!] Khong tim thay ca: " + fishId);
        return found;
    }

    public void findByColor(String color) {
        System.out.println("  --- Ca mau " + color + " ---");
        final boolean[] found = {false};
        fishList.traverse(new Visitor<KoiFish>() {
            public void visit(KoiFish f) {
                if (f.getColor().equalsIgnoreCase(color)) {
                    f.display();
                    found[0] = true;
                }
            }
        });
        if (!found[0]) System.out.println("  Khong tim thay.");
    }

    // ── HIỂN THỊ ────────────────────────────────────────────────────────────
    public void displayAll() {
        System.out.println("  --- Danh sach ca (theo thu tu nhap) ---");
        fishList.traverse(new Visitor<KoiFish>() {
            public void visit(KoiFish f) { f.display(); }
        });
    }

    public void displaySortedById() {
        System.out.println("  --- Danh sach ca (theo fishId tang dan) ---");
        fishTree.inOrder(new Visitor<KoiFish>() {
            public void visit(KoiFish f) { f.display(); }
        });
    }

    // ── THỐNG KÊ ────────────────────────────────────────────────────────────
    public void statistics() {
        int total = fishList.size();
        int available = fishList.countIf(new Condition<KoiFish>() {
            public boolean check(KoiFish f) { return f.getStatus().equals("available"); }
        });
        int auctioning = fishList.countIf(new Condition<KoiFish>() {
            public boolean check(KoiFish f) { return f.getStatus().equals("auctioning"); }
        });
        int sold = fishList.countIf(new Condition<KoiFish>() {
            public boolean check(KoiFish f) { return f.getStatus().equals("sold"); }
        });
        KoiFish mostExpensive = fishList.findMax(new datastructure.Comparator<KoiFish>() {
            public int compare(KoiFish a, KoiFish b) {
                return a.getPrice() > b.getPrice() ? 1 : -1;
            }
        });
        System.out.println("  Tong so ca     : " + total);
        System.out.println("  Con hang       : " + available);
        System.out.println("  Dang dau gia   : " + auctioning);
        System.out.println("  Da ban         : " + sold);
        if (mostExpensive != null)
            System.out.println("  Ca dat nhat    : " + mostExpensive.getFishId()
                + " - " + mostExpensive.getName()
                + " - " + (long) mostExpensive.getPrice() + " VND");
    }

    // ── DÙNG NỘI BỘ cho AuctionService ─────────────────────────────────────
    public boolean setStatus(String fishId, String status) {
        KoiFish found = fishTree.search(fishId);
        if (found == null) return false;
        found.setStatus(status);
        return true;
    }

    public boolean exists(String fishId) {
        return fishTree.search(fishId) != null;
    }


}
