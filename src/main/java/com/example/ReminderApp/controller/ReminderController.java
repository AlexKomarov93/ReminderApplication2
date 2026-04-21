package com.example.ReminderApp.controller;

import com.example.ReminderApp.config.ReminderMapper;
import com.example.ReminderApp.dto.ReminderRequestDto;
import com.example.ReminderApp.dto.ReminderResponseDto;
import com.example.ReminderApp.entity.Reminder;
import com.example.ReminderApp.services.interfaces.ReminderServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/reminder")
public class ReminderController {

    private final ReminderServiceImpl reminderService;
    private final ReminderMapper reminderMapper;


    @PostMapping("/create")
    public ReminderResponseDto addOrUpdateReminder(@RequestBody ReminderRequestDto reminderRequestDto) {
         Reminder reminder =  reminderService.saveOrUpdate(
                reminderRequestDto.getTitle(),
                reminderRequestDto.getDescription(),
                reminderRequestDto.getRemind()
        );
        return reminderMapper.toReminderResponse(reminder);

    }

    @DeleteMapping("/{id}")
    public void deleteReminderById(@PathVariable Long id) {
        reminderService.deleteReminder(id);
    }

    @GetMapping("/search")
    public List<ReminderResponseDto> searchReminderByParameters(
            @RequestParam(required = false) String title,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time
    ) {

        List<Reminder> reminders = reminderService.searchReminderByParameters(title, date, time);
        return reminderMapper.toReminderResponseList(reminders);
    }

    @GetMapping("/sort")
    public List<ReminderResponseDto> sortReminderByParameters(Sort sort) {
        List <Reminder> reminders = reminderService.sortByParameters(sort);
        return reminderMapper.toReminderResponseList(reminders);
    }

    @GetMapping("/filter")
    public List<ReminderResponseDto> filterReminderByDateAndTime(
            @RequestParam(required = false) String title,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time
    ) {

        List<Reminder> reminders = reminderService.searchReminderByParameters(title, date, time);
        return reminderMapper.toReminderResponseList(reminders);
    }

    @GetMapping("/list")
    public Page<ReminderResponseDto> ToShowPaginationOfReminders(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page <Reminder> reminders = reminderService.searchWithPagination(pageNumber, pageSize);
        return reminderMapper.toReminderResponsePage(reminders);

    }


}
