package com.example.demo.controller;


import com.example.demo.entity.EmailNotification;
import com.example.demo.entity.Notification;
import com.example.demo.entity.NotificationFactory;
import com.example.demo.entity.SMSNotification;
import java.util.List;
import java.util.Queue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.database.NotificationDB.notifications;

@RestController
public class NotificationController {

  private NotificationFactory sms = new SMSNotification();
  private NotificationFactory email = new EmailNotification();

  Notification n;

  //    public NotificationController(NotificationFactory nf) {
  //        this.nf = nf;
  //    }

  //    @GetMapping("/notifications/sms/")
  //    public String doNotificationSMS(String s){
  //        String message=sms.displayNotification(s);
  //        notifications.add(message);
  //        return message;
  //    }
  //
  //    @GetMapping("/notifications/email/")
  //    public String doNotificationEmail(String s){
  //            String message=email.displayNotification(s);
  //        notifications.add(message);
  //
  //        return message;
  //    }

  @GetMapping("/notifications/display/")
  public Queue<String> doDisplay() {
    return notifications;
  }
}
