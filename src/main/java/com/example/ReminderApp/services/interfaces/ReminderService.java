package com.example.ReminderApp.services.interfaces;

import com.example.ReminderApp.entity.Reminder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReminderService {
    Reminder saveOrUpdate(String title, String description, LocalDateTime remind);
    void deleteReminder(Long id);
    List<Reminder> searchReminderByParameters(String title, LocalDate date, LocalTime time);
    List <Reminder> sortByParameters(Sort sort);
    Page<Reminder> searchWithPagination(int pageNumber, int pageSize);

}
