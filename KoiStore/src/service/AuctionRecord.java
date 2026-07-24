package service;

import datastructure.LinkedStack;
import entity.AuctionSession;
import entity.Bid;
import entity.Displayable;

/**
 * Pairs one AuctionSession with its own bid-history stack, so AuctionService
 * can keep every session's history separate without needing java.util.Map.
 * Kept in the service layer (not entity) since it is bookkeeping, not data.
 */
public class AuctionRecord implements Displayable {

    private final AuctionSession session;
    private final LinkedStack<Bid> bidHistory = new LinkedStack<>();

    public AuctionRecord(AuctionSession session) {
        this.session = session;
    }

    public AuctionSession getSession() {
        return session;
    }

    public LinkedStack<Bid> getBidHistory() {
        return bidHistory;
    }

    @Override
    public void display() {
        session.display();
    }
}
