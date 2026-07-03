/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author pc
 */
public class KoiFish {
   
    private String fishId;
    private String name;
    private String origin;
    private String color;
    private double price;
    private String status; // "available" | "auctioning" | "sold"

    public KoiFish(String fishId, String name, String origin, String color, double price) {
        this.fishId = fishId;
        this.name   = name;
        this.origin = origin;
        this.color  = color;
        this.price  = price;
        this.status = "available";
    }

    // Getters
    public String getFishId()  { return fishId; }
    public String getName()    { return name; }
    public String getOrigin()  { return origin; }
    public String getColor()   { return color; }
    public double getPrice()   { return price; }
    public String getStatus()  { return status; }

    // Setters
    public void setPrice(double price)   { this.price  = price; }
    public void setStatus(String status) { this.status = status; }
    public void setColor(String color)   { this.color  = color; }

    public void display() {
        System.out.printf("[%s] %s | %s | %s | %.0f VND | %s%n",
            fishId, name, origin, color, price, status);
    }

    // Dùng cho undo — tạo bản sao để lưu trạng thái cũ

    public KoiFish clone() {
        KoiFish copy = new KoiFish(fishId, name, origin, color, price);
        copy.setStatus(this.status);
        return copy;
    }
}

