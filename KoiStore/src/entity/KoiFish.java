package entity;

public class KoiFish implements Displayable {

    private String fishId;
    private String name;
    private String origin;
    private String color;
    private double price;
    private String status; // "available", "in_auction", "sold"

    public KoiFish() {
    }

    public KoiFish(String fishId, String name, String origin, String color, double price, String status) {
        this.fishId = fishId;
        this.name = name;
        this.origin = origin;
        this.color = color;
        this.price = price;
        this.status = status;
    }

    public String getFishId() {
        return fishId;
    }

    public void setFishId(String fishId) {
        this.fishId = fishId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public KoiFish clone() {
        return new KoiFish(fishId, name, origin, color, price, status);
    }

    @Override
    public void display() {
        System.out.printf("%-8s %-15s %-10s %-10s %12.0f VND  [%s]%n",
                fishId, name, origin, color, price, status);
    }
}
