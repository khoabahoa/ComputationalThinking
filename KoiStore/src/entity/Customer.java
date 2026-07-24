package entity;

public class Customer implements Displayable {

    private String customerId;
    private String name;
    private String phone;
    private String email;

    public Customer() {
    }

    public Customer(String customerId, String name, String phone, String email) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Customer clone() {
        return new Customer(customerId, name, phone, email);
    }

    @Override
    public void display() {
        System.out.printf("%-8s %-20s %-12s %-20s%n", customerId, name, phone, email);
    }
}
