/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author pc
 */
public class AuctionSession {
     private final String sessionId;
    private final String fishId;
    private final double startPrice;
    private double currentHighest;
    private String winnerCustomerId;
    private String status; // "open" | "closed" | "cancelled"

    public AuctionSession(String sessionId, String fishId, double startPrice) {
        this.sessionId        = sessionId;
        this.fishId           = fishId;
        this.startPrice       = startPrice;
        this.currentHighest   = startPrice;
        this.winnerCustomerId = null;
        this.status           = "open";
    }

    public String getSessionId()        { return sessionId; }
    public String getFishId()           { return fishId; }
    public double getStartPrice()       { return startPrice; }
    public double getCurrentHighest()   { return currentHighest; }
    public String getWinnerCustomerId() { return winnerCustomerId; }
    public String getStatus()           { return status; }

    public void setCurrentHighest(double amount)      { this.currentHighest   = amount; }
    public void setWinnerCustomerId(String customerId) { this.winnerCustomerId = customerId; }
    public void setStatus(String status)               { this.status           = status; }

    public void display() {
        System.out.printf("[%s] Cá: %s | Khởi điểm: %.0f | Cao nhất: %.0f | %s%n",
            sessionId, fishId, startPrice, currentHighest, status);
    }
}
