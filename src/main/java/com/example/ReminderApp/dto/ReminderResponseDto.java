package com.example.ReminderApp.dto;

import java.time.LocalDateTime;

public record ReminderResponseDto(
        String title,
        String description,
        LocalDateTime remind) {
}