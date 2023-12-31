package com.example.demo.entity;

public class EmailNotification extends NotificationFactory{


    @Override
    public String channel() {
        return "Email Message ";
    }
}
