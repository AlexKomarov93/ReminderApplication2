package com.example.ReminderApp.config;

import com.example.ReminderApp.dto.ReminderResponseDto;
import com.example.ReminderApp.entity.Reminder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReminderMapper {

    ReminderResponseDto toReminderResponse(Reminder reminder);

    List<ReminderResponseDto> toReminderResponseList(List<Reminder> reminders);

    default Page<ReminderResponseDto> toReminderResponsePage(Page<Reminder> reminders) {
        if (reminders == null) {
            return null;
        }

        List<ReminderResponseDto> content = reminders.getContent()
                .stream()
                .map(this::toReminderResponse)
                .toList();

        return new PageImpl<>(content, reminders.getPageable(), reminders.getTotalElements());
    }
}