package com.example.demo.entity;

public class SMSNotification extends NotificationFactory{

    @Override
    public String channel() {
        return "SMS Message ";
    }
}
