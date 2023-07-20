package com.example.auton;

public class DetailingService_ModelClass {
    String Price, AdditionalServices, ServiceName, ValueaddingService;

    public DetailingService_ModelClass() {
    }

    public DetailingService_ModelClass(String price, String additionalServices, String serviceName, String valueaddingService) {
        Price = price;
        AdditionalServices = additionalServices;
        ServiceName = serviceName;
        ValueaddingService = valueaddingService;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getAdditionalServices() {
        return AdditionalServices;
    }

    public void setAdditionalServices(String additionalServices) {
        AdditionalServices = additionalServices;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public String getValueaddingService() {
        return ValueaddingService;
    }

    public void setValueaddingService(String valueaddingService) {
        ValueaddingService = valueaddingService;
    }
}
