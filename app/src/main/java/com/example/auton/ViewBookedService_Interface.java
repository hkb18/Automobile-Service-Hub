package com.example.auton;

public interface ViewBookedService_Interface {
    void accept(String username, String key, int position, String userLat, String userLong);

    void delete(String username, String key, int position);

    void view(String key, int position);
}
