package com.example.demo.entity;

public interface Notification {
    public String sendNotification(Customer c);

   public void setCustomerName(String s);
    public void setOrderId(int orderId);
    public void setFees(double fees);
}
