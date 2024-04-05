package com.example.busbooking;

public class Bookings {
    private String name, email, from, to;
    double price;

    public Bookings(){

    }

    public Bookings(String name, String email, String from, String to, double price) {
        this.name = name;
        this.email = email;
        this.from = from;
        this.to = to;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
