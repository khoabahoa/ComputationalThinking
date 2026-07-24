package entity;

public class Bid implements Displayable {

    private String bidId;
    private String customerId;
    private String customerName;
    private double amount;
    private String time;

    public Bid() {
    }

    public Bid(String bidId, String customerId, String customerName, double amount, String time) {
        this.bidId = bidId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.amount = amount;
        this.time = time;
    }

    public String getBidId() {
        return bidId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getAmount() {
        return amount;
    }

    public String getTime() {
        return time;
    }

    @Override
    public void display() {
        System.out.printf("%-6s %-8s %-20s %12.0f VND  @%s%n",
                bidId, customerId, customerName, amount, time);
    }
}
