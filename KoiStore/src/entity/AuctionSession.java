package entity;

public class AuctionSession implements Displayable {

    private String sessionId;
    private String fishId;
    private double startPrice;
    private double currentHighest;
    private String winnerCustomerId;
    private String status; // "open" or "closed"

    public AuctionSession() {
    }

    public AuctionSession(String sessionId, String fishId, double startPrice) {
        this.sessionId = sessionId;
        this.fishId = fishId;
        this.startPrice = startPrice;
        this.currentHighest = startPrice;
        this.winnerCustomerId = null;
        this.status = "open";
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getFishId() {
        return fishId;
    }

    public void setFishId(String fishId) {
        this.fishId = fishId;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public double getCurrentHighest() {
        return currentHighest;
    }

    public void setCurrentHighest(double currentHighest) {
        this.currentHighest = currentHighest;
    }

    public String getWinnerCustomerId() {
        return winnerCustomerId;
    }

    public void setWinnerCustomerId(String winnerCustomerId) {
        this.winnerCustomerId = winnerCustomerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void display() {
        System.out.printf("%-8s fish=%-8s start=%10.0f highest=%10.0f winner=%-8s [%s]%n",
                sessionId, fishId, startPrice, currentHighest,
                winnerCustomerId == null ? "-" : winnerCustomerId, status);
    }
}
