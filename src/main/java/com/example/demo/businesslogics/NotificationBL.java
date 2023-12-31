package com.example.demo.businesslogics;

import static com.example.demo.database.NotificationDB.notifications;

import com.example.demo.entity.*;
public class NotificationBL {

  private NotificationFactory sms = new SMSNotification();
  private NotificationFactory email = new EmailNotification();
  Notification message = null;

  //    public NotificationBSL(Customer c,String s){
  //       message=sms.createNotification(s);
  //          message.setCustomerName(c.getName());
  //         message.setOrderId(c.getOrders().get(0).getOrderID());
  //         message.setFees(c.getOrders().get(0).getShippingFees());
  //    }

  //    @GetMapping("/notifications/sms/")
  public String doNotificationSMS(String s, Customer c) {
    String message = sms.displayNotification(s, c);
    notifications.add(message);
    return message;
  }

  //    @GetMapping("/notifications/email/")
  public String doNotificationEmail(String s, Customer c) {
    String message = email.displayNotification(s, c);
    notifications.add(message);

    return message;
  }
}
