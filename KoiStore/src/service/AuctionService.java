/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author pc
 */
import datastructure.DoublyLinkedList;
import datastructure.IdExtractor;
import datastructure.LinkedQueue;
import datastructure.LinkedStack;
import datastructure.SinglyLinkedList;
import datastructure.Visitor;
import entity.AuctionSession;
import entity.Bid;

public class AuctionService {

    private SinglyLinkedList<AuctionSession> sessionList = new SinglyLinkedList<>();
    private DoublyLinkedList<AuctionSession> sessionHistory = new DoublyLinkedList<>();

    // Mỗi phiên có 1 Queue đặt giá + 1 Stack lịch sử
    // Dùng mảng song song, giới hạn tối đa 100 phiên cùng lúc
    private static final int MAX = 100;
    private String[]                  sessionIds  = new String[MAX];
    private LinkedQueue<Bid>[]        bidQueues   = new LinkedQueue[MAX];
    private LinkedStack<Bid>[]        bidStacks   = new LinkedStack[MAX];
    private int                       slotCount   = 0;

    private KoiService     koiService;
    private CustomerService customerService;

    public AuctionService(KoiService koiService, CustomerService customerService) {
        this.koiService      = koiService;
        this.customerService = customerService;
    }

    // ── Tìm slot index theo sessionId ───────────────────────────────────────
    private int findSlot(String sessionId) {
        for (int i = 0; i < slotCount; i++) {
            if (sessionIds[i].equals(sessionId)) return i;
        }
        return -1;
    }

    // ── TẠO PHIÊN ───────────────────────────────────────────────────────────
    public boolean createSession(String sessionId, String fishId, double startPrice) {
        if (sessionList.findById(sessionId, new IdExtractor<AuctionSession>() {
            public String getId(AuctionSession s) { return s.getSessionId(); }
        }) != null) {
            System.out.println("  [!] SessionId da ton tai: " + sessionId);
            return false;
        }
        if (!koiService.exists(fishId)) {
            System.out.println("  [!] Khong tim thay ca: " + fishId);
            return false;
        }
        if (slotCount >= MAX) {
            System.out.println("  [!] Da dat gioi han phien.");
            return false;
        }
        AuctionSession session = new AuctionSession(sessionId, fishId, startPrice);
        sessionList.addLast(session);
        sessionHistory.addLast(session);
        koiService.setStatus(fishId, "auctioning");

        sessionIds[slotCount] = sessionId;
        bidQueues[slotCount]  = new LinkedQueue<>();
        bidStacks[slotCount]  = new LinkedStack<>();
        slotCount++;

        System.out.println("  [OK] Mo phien dau gia: " + sessionId
            + " | Ca: " + fishId
            + " | Gia khoi diem: " + (long) startPrice + " VND");
        return true;
    }

    // ── ĐẶT GIÁ ─────────────────────────────────────────────────────────────
    public boolean placeBid(String sessionId, String customerId, double amount, String time) {
        AuctionSession session = sessionList.findById(sessionId, (AuctionSession s) -> s.getSessionId());
        if (session == null) {
            System.out.println("  [!] Khong tim thay phien: " + sessionId);
            return false;
        }
        if (!session.getStatus().equals("open")) {
            System.out.println("  [!] Phien khong con mo: " + sessionId);
            return false;
        }
        if (!customerService.exists(customerId)) {
            System.out.println("  [!] Khong tim thay khach: " + customerId);
            return false;
        }
        if (amount <= session.getCurrentHighest()) {
            System.out.println("  [!] Gia phai lon hon gia hien tai: "
                + (long) session.getCurrentHighest() + " VND");
            return false;
        }

        int slot = findSlot(sessionId);
        String bidId = "B" + (bidStacks[slot].size() + 1);
        String customerName = customerService.findById(customerId).getName();
        Bid bid = new Bid(bidId, customerId, customerName, amount, time);

        bidQueues[slot].enqueue(bid);   // xếp vào hàng chờ xử lý
        processNextBid(sessionId, slot, session); // xử lý ngay
        return true;
    }

    // Xử lý lượt đặt giá từ Queue
    private void processNextBid(String sessionId, int slot, AuctionSession session) {
        Bid bid = bidQueues[slot].dequeue();
        if (bid == null) return;
        session.setCurrentHighest(bid.getAmount());
        session.setWinnerCustomerId(bid.getCustomerId());
        bidStacks[slot].push(bid);      // lưu vào Stack lịch sử
        System.out.println("  [OK] Dat gia thanh cong: "
            + bid.getCustomerName() + " - " + (long) bid.getAmount() + " VND");
    }

    // ── XEM LỊCH SỬ ĐẶT GIÁ ────────────────────────────────────────────────
    public void showBidHistory(String sessionId) {
        int slot = findSlot(sessionId);
        if (slot == -1) {
            System.out.println("  [!] Khong tim thay phien: " + sessionId);
            return;
        }
        System.out.println("  --- Lich su dat gia phien " + sessionId + " (moi nhat truoc) ---");
        // Dùng Stack phụ để duyệt không phá Stack gốc
        LinkedStack<Bid> temp  = new LinkedStack<>();
        LinkedStack<Bid> clone = new LinkedStack<>();
        // Đổ toàn bộ sang temp (đảo ngược)
        // Vì không có traverse cho Stack, tự duyệt bằng pop/push
        Bid b;
        while ((b = bidStacks[slot].pop()) != null) temp.push(b);
        // temp bây giờ có thứ tự cũ → mới
        // In và đổ vào clone để khôi phục
        boolean empty = true;
        while ((b = temp.pop()) != null) {
            b.display();
            clone.push(b);
            empty = false;
        }
        if (empty) System.out.println("  (Chua co luot dat gia nao)");
        // Khôi phục Stack gốc
        while ((b = clone.pop()) != null) bidStacks[slot].push(b);
    }

    // ── KẾT THÚC PHIÊN ──────────────────────────────────────────────────────
    public boolean closeSession(String sessionId) {
        AuctionSession session = sessionList.findById(sessionId,
            new IdExtractor<AuctionSession>() {
                public String getId(AuctionSession s) { return s.getSessionId(); }
            });
        if (session == null) {
            System.out.println("  [!] Khong tim thay phien: " + sessionId);
            return false;
        }
        if (!session.getStatus().equals("open")) {
            System.out.println("  [!] Phien khong dang mo.");
            return false;
        }
        int slot = findSlot(sessionId);
        if (bidStacks[slot].isEmpty()) {
            System.out.println("  [!] Chua co luot dat gia nao. Khong the chot phien.");
            return false;
        }
        session.setStatus("closed");
        koiService.setStatus(session.getFishId(), "sold");
        System.out.println("  [OK] Phien " + sessionId + " da ket thuc.");
        System.out.println("       Nguoi thang: " + session.getWinnerCustomerId()
            + " | Gia chot: " + (long) session.getCurrentHighest() + " VND");
        return true;
    }

    // ── HUỶ PHIÊN ───────────────────────────────────────────────────────────
    public boolean cancelSession(String sessionId) {
        AuctionSession session = sessionList.findById(sessionId,
            new IdExtractor<AuctionSession>() {
                public String getId(AuctionSession s) { return s.getSessionId(); }
            });
        if (session == null) {
            System.out.println("  [!] Khong tim thay phien: " + sessionId);
            return false;
        }
        if (!session.getStatus().equals("open")) {
            System.out.println("  [!] Chi huy duoc phien dang mo.");
            return false;
        }
        int slot = findSlot(sessionId);
        // Xả hết Queue
        while (!bidQueues[slot].isEmpty()) bidQueues[slot].dequeue();

        session.setStatus("cancelled");
        koiService.setStatus(session.getFishId(), "available");
        System.out.println("  [OK] Da huy phien: " + sessionId
            + " | Ca " + session.getFishId() + " tro ve trang thai available.");
        return true;
    }

    // ── HIỂN THỊ PHIÊN ──────────────────────────────────────────────────────
    public void displayAllSessions() {
        System.out.println("  --- Tat ca phien dau gia ---");
        sessionList.traverse(new Visitor<AuctionSession>() {
            public void visit(AuctionSession s) { s.display(); }
        });
    }

    public void displayHistoryForward() {
        System.out.println("  --- Lich su phien (cu den moi) ---");
        sessionHistory.displayForward(new Visitor<AuctionSession>() {
            public void visit(AuctionSession s) { s.display(); }
        });
    }

    public void displayHistoryBackward() {
        System.out.println("  --- Lich su phien (moi den cu) ---");
        sessionHistory.displayBackward(new Visitor<AuctionSession>() {
            public void visit(AuctionSession s) { s.display(); }
        });
    }

    public void displayOpenSessions() {
        System.out.println("  --- Phien dang mo ---");
        final boolean[] found = {false};
        sessionList.traverse(new Visitor<AuctionSession>() {
            public void visit(AuctionSession s) {
                if (s.getStatus().equals("open")) {
                    s.display();
                    found[0] = true;
                }
            }
        });
        if (!found[0]) System.out.println("  (Khong co phien nao dang mo)");
    }


}
