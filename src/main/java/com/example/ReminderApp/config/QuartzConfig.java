package com.example.ReminderApp.config;

import com.example.ReminderApp.quartz.ReminderJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class QuartzConfig {
    @Bean
    public JobDetail reminderJobDetail(){
        return JobBuilder.newJob(ReminderJob.class)
                .withIdentity("ReminderJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger reminderJobTrigger(JobDetail reminderJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(reminderJobDetail)
                .withIdentity("reminderJobTrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(60)
                        .repeatForever())
                .build();
    }



}
