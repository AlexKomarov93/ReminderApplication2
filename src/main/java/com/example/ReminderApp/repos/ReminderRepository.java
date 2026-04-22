package com.example.ReminderApp.repos;

import com.example.ReminderApp.entity.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder,Long>, JpaSpecificationExecutor <Reminder> {

    List<Reminder> findAllByRemindBefore(LocalDateTime time);
}
