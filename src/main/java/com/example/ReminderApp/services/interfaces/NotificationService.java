package com.example.ReminderApp.services.interfaces;


import com.example.ReminderApp.entity.Reminder;
import com.example.ReminderApp.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {

    private final JavaMailSender javaMailSender;

    public void send(User user, Reminder reminder, String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        String email = user.getEmail();
        String title = reminder.getTitle();
        String description = reminder.getDescription();

        message.setTo(email);
        message.setSubject(title);
        message.setText(description);
        javaMailSender.send(message);

    }
}
