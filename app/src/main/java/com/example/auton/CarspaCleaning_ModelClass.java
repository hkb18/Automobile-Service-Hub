package com.example.auton;

public class CarspaCleaning_ModelClass {
    String Price, ServiceName, WtsIncluded;

    public CarspaCleaning_ModelClass() {
    }

    public CarspaCleaning_ModelClass(String price, String serviceName, String wtsIncluded) {
        Price = price;
        ServiceName = serviceName;
        WtsIncluded = wtsIncluded;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public String getWtsIncluded() {
        return WtsIncluded;
    }

    public void setWtsIncluded(String wtsIncluded) {
        WtsIncluded = wtsIncluded;
    }
}
