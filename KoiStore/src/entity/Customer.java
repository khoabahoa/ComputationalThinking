/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author pc
 */
public class Customer {
     private final String customerId;
    private final String name;
    private String phone;
    private String email;

    public Customer(String customerId, String name, String phone, String email) {
        this.customerId = customerId;
        this.name       = name;
        this.phone      = phone;
        this.email      = email;
    }

    public String getCustomerId() { return customerId; }
    public String getName()       { return name; }
    public String getPhone()      { return phone; }
    public String getEmail()      { return email; }

    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }

    public void display() {
        System.out.printf("[%s] %s | %s | %s%n", customerId, name, phone, email);
    }
}
