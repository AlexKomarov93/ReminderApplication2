package com.example.ReminderApp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReminderRequestDto {
    private String title;
    private String description;
    private LocalDateTime remind;
}
