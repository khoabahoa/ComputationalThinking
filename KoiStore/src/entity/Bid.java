/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author pc
 */
public class Bid {
    private final String bidId;
    private final String customerId;
    private final String customerName;
    private final double amount;
    private final String time; // đơn giản dùng String cho console app

    public Bid(String bidId, String customerId, String customerName, double amount, String time) {
        this.bidId        = bidId;
        this.customerId   = customerId;
        this.customerName = customerName;
        this.amount       = amount;
        this.time         = time;
    }

    public String getBidId()        { return bidId; }
    public String getCustomerId()   { return customerId; }
    public String getCustomerName() { return customerName; }
    public double getAmount()       { return amount; }
    public String getTime()         { return time; }

    public void display() {
        System.out.printf("[%s] %s (%s) đặt %.0f VND lúc %s%n",
            bidId, customerName, customerId, amount, time);
    }
}
