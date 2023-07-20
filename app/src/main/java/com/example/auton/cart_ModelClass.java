package com.example.auton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class cart_ModelClass {
    String model, manufacturer, price, quantity, image, username, key, productKey, totalQty, mainName, subName;

    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    String date = currentDate;

    public cart_ModelClass() {
    }

    public cart_ModelClass(String model, String manufacturer, String price, String quantity, String image, String username, String key, String productKey, String totalQty, String mainName, String subName, String date) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.username = username;
        this.key = key;
        this.productKey = productKey;
        this.totalQty = totalQty;
        this.mainName = mainName;
        this.subName = subName;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }
}
