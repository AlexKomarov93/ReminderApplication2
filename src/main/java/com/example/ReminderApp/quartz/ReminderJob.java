package com.example.ReminderApp.quartz;

import com.example.ReminderApp.entity.Reminder;
import com.example.ReminderApp.entity.User;
import com.example.ReminderApp.repos.ReminderRepository;
import com.example.ReminderApp.services.interfaces.NotificationService;
import lombok.AllArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class ReminderJob implements Job {

  private final  ReminderRepository reminderRepository;
  private final NotificationService notificationService;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<Reminder> toNotifyUser = reminderRepository.findAllByRemindBefore(LocalDateTime.now());

        for (Reminder reminder: toNotifyUser){
           User user = reminder.getUser();

            notificationService.send(
                    user,
                    reminder,
                    user.getEmail(),
                    reminder.getTitle(),
                    reminder.getDescription());
        }





    }
}
