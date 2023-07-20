package com.example.auton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OrderHistory_ModelClass {
    String image, key, mainName, manufacturer, model, price, productKey, quantity, subName, totalQty, username;
    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    String date = currentDate;

    public OrderHistory_ModelClass() {
    }


    public OrderHistory_ModelClass(String date, String image, String key, String mainName, String manufacturer, String model, String price, String productKey, String quantity, String subName, String totalQty, String username) {
        this.image = image;
        this.key = key;
        this.mainName = mainName;
        this.manufacturer = manufacturer;
        this.model = model;
        this.price = price;
        this.productKey = productKey;
        this.quantity = quantity;
        this.subName = subName;
        this.totalQty = totalQty;
        this.username = username;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
