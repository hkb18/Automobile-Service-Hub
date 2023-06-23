package com.example.auton;

public class cart_ModelClass {
    String model,maufacturer,price,quantity,image,username;

    public cart_ModelClass() {
    }

    public cart_ModelClass(String model, String maufacturer, String price, String quantity, String image,String username) {
        this.model = model;
        this.maufacturer = maufacturer;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMaufacturer() {
        return maufacturer;
    }

    public void setMaufacturer(String maufacturer) {
        this.maufacturer = maufacturer;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
