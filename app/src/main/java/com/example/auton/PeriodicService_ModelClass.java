package com.example.auton;

public class PeriodicService_ModelClass {
    private String AdditionalServices, EssentialServices, PerformanceServices, Price, ServiceName, Key;

    public PeriodicService_ModelClass() {
    }

    public PeriodicService_ModelClass(String additionalServices, String essentialServices, String performanceServices, String price, String serviceName, String key) {
        AdditionalServices = additionalServices;
        EssentialServices = essentialServices;
        PerformanceServices = performanceServices;
        Price = price;
        ServiceName = serviceName;
        Key = key;
    }

    public String getAdditionalServices() {
        return AdditionalServices;
    }

    public void setAdditionalServices(String additionalServices) {
        AdditionalServices = additionalServices;
    }

    public String getEssentialServices() {
        return EssentialServices;
    }

    public void setEssentialServices(String essentialServices) {
        EssentialServices = essentialServices;
    }

    public String getPerformanceServices() {
        return PerformanceServices;
    }

    public void setPerformanceServices(String performanceServices) {
        PerformanceServices = performanceServices;
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

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }
}
