package service;

import datastructure.DoublyLinkedList;
import datastructure.LinkedQueue;
import datastructure.SinglyLinkedList;
import entity.AuctionSession;
import entity.Bid;

public class AuctionService {

    private final KoiService koiService;
    private final CustomerService customerService;

    // all sessions ever created (open + closed), searchable by id
    private final SinglyLinkedList<AuctionRecord> sessions = new SinglyLinkedList<>();
    // closed sessions only, browsable forward/backward
    private final DoublyLinkedList<AuctionRecord> closedHistory = new DoublyLinkedList<>();

    private int bidCounter = 0;

    public AuctionService(KoiService koiService, CustomerService customerService) {
        this.koiService = koiService;
        this.customerService = customerService;
    }

    private AuctionRecord findRecord(String sessionId) {
        return sessions.findById(sessionId, r -> r.getSession().getSessionId());
    }

    public boolean openSession(String sessionId, String fishId, double startPrice) {
        if (findRecord(sessionId) != null) {
            System.out.println(" [!] Session ID already exists: " + sessionId);
            return false;
        }
        if (!koiService.exists(fishId)) {
            System.out.println(" [!] Fish not found: " + fishId);
            return false;
        }
        AuctionSession session = new AuctionSession(sessionId, fishId, startPrice);
        sessions.addLast(new AuctionRecord(session));
        koiService.findById(fishId).setStatus("in_auction");
        System.out.println(" [OK] Opened session " + sessionId + " for fish " + fishId);
        return true;
    }

    public boolean closeSession(String sessionId) {
        AuctionRecord record = findRecord(sessionId);
        if (record == null) {
            System.out.println(" [!] Session not found: " + sessionId);
            return false;
        }
        AuctionSession session = record.getSession();
        if (!session.getStatus().equals("open")) {
            System.out.println(" [!] Session already closed.");
            return false;
        }
        session.setStatus("closed");
        if (session.getWinnerCustomerId() != null) {
            koiService.findById(session.getFishId()).setStatus("sold");
        } else {
            koiService.findById(session.getFishId()).setStatus("available");
        }
        closedHistory.addLast(record);
        System.out.println(" [OK] Closed session " + sessionId);
        return true;
    }

    /**
     * Places a bid in an open session.
     * Validates session, customer, and bid amount, then uses a Queue (FIFO)
     * to receive the bid and a Stack (LIFO) to record confirmed bid history.
     */
    public boolean placeBid(String sessionId, String customerId, double amount, String time) {
        AuctionRecord record = findRecord(sessionId);
        if (record == null) {
            System.out.println(" [!] Session not found: " + sessionId);
            return false;
        }
        AuctionSession session = record.getSession();
        if (!session.getStatus().equals("open")) {
            System.out.println(" [!] Session is not open.");
            return false;
        }
        if (!customerService.exists(customerId)) {
            System.out.println(" [!] Customer not found: " + customerId);
            return false;
        }
        if (amount <= session.getCurrentHighest()) {
            System.out.println(" [!] Bid must exceed current highest: " + (long) session.getCurrentHighest() + " VND");
            return false;
        }

        String customerName = customerService.findById(customerId).getName();
        Bid bid = new Bid("B" + (++bidCounter), customerId, customerName, amount, time);

        // FIFO queue receives the bid, then it is processed immediately
        LinkedQueue<Bid> incoming = new LinkedQueue<>();
        incoming.enqueue(bid);
        Bid processed = incoming.dequeue();

        session.setCurrentHighest(processed.getAmount());
        session.setWinnerCustomerId(processed.getCustomerId());
        record.getBidHistory().push(processed); // LIFO history

        System.out.println(" [OK] Bid placed: " + processed.getCustomerName() + " - " + (long) processed.getAmount() + " VND");
        return true;
    }

    public void showBidHistory(String sessionId) {
        AuctionRecord record = findRecord(sessionId);
        if (record == null) {
            System.out.println(" [!] Session not found: " + sessionId);
            return;
        }
        if (record.getBidHistory().isEmpty()) {
            System.out.println(" (no bids yet for " + sessionId + ")");
            return;
        }
        // pop everything (shows most-recent-first) into a temp stack, then push
        // back from the temp stack to restore the original order
        datastructure.LinkedStack<Bid> temp = new datastructure.LinkedStack<>();
        while (!record.getBidHistory().isEmpty()) {
            Bid b = record.getBidHistory().pop();
            b.display();
            temp.push(b);
        }
        while (!temp.isEmpty()) {
            record.getBidHistory().push(temp.pop());
        }
    }

    public void listAllSessions() {
        if (sessions.isEmpty()) {
            System.out.println(" (no sessions yet)");
            return;
        }
        sessions.traverse();
    }

    public void viewClosedHistoryForward() {
        if (closedHistory.isEmpty()) {
            System.out.println(" (no closed sessions yet)");
            return;
        }
        closedHistory.displayForward();
    }

    public void viewClosedHistoryBackward() {
        if (closedHistory.isEmpty()) {
            System.out.println(" (no closed sessions yet)");
            return;
        }
        closedHistory.displayBackward();
    }
}
