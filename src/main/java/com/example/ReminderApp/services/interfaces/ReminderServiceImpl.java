package com.example.ReminderApp.services.interfaces;


import com.example.ReminderApp.entity.Reminder;
import com.example.ReminderApp.repos.ReminderRepository;
import com.example.ReminderApp.repos.ReminderSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Service
@AllArgsConstructor
public class ReminderServiceImpl implements ReminderService {

    private final ReminderRepository reminderRepository;

    @Override
    @Transactional
    public Reminder saveOrUpdate(String title, String description, LocalDateTime remind) {
        Reminder reminder = Reminder.builder()
                .title(title)
                .description(description)
                .remind(remind)
                .build();
        return reminderRepository.save(reminder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteReminder(Long id) {
        reminderRepository.deleteById(id);
    }

    @Override
    public List<Reminder> searchReminderByParameters(String title, LocalDate date, LocalTime time) {
        Specification<Reminder> spec = ReminderSpecifications.filterBy(title, date, time);
        return reminderRepository.findAll(spec);
    }


    @Override
    public List<Reminder> sortByParameters(Sort sort) {
        return reminderRepository.findAll(sort);
    }


    @Override
    public Page<Reminder> searchWithPagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return reminderRepository.findAll(pageable);

    }
}
