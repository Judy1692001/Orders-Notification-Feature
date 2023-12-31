package com.example.demo.entity;

public abstract class NotificationFactory {
    public Notification createNotification(String notificationType) {
        if(notificationType.equals("order")){
            return new OrderNotification();
        }
        else if(notificationType.equals("shipment")){
            return new ShipmentNotification();
        }

        return null;

    }
    public abstract String channel();

    public String displayNotification(String s,Customer c){
        return  channel()+createNotification(s).sendNotification(c);
    }

}
