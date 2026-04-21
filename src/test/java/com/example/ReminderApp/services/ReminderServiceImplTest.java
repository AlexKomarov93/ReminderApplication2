package com.example.ReminderApp.services;

import com.example.ReminderApp.entity.Reminder;
import com.example.ReminderApp.repos.ReminderRepository;
import com.example.ReminderApp.services.interfaces.ReminderServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReminderServiceImplTest {

    @Mock
    private ReminderRepository reminderRepository;

    @InjectMocks
    private ReminderServiceImpl reminderService;

    //Preparing
    private final Reminder reminder = Reminder.builder()
            .title("Новая жизнь с понедельника")
            .description("1000 проц бросаю курить и иду на турнички")
            .remind(LocalDateTime.now())
            .build();

    private final Reminder savedReminder = Reminder.builder()
            .id(1L)
            .title("Новая жизнь с понедельника")
            .description("1000 проц бросаю курить и иду на турнички")
            .remind(LocalDateTime.now())
            .build();

    @Test
    @DisplayName("Напоминание сохраняется корректно")
    void shouldSaveReminderCorrectly() {
        //Arrange
        when(reminderRepository.save(reminder)).thenReturn(savedReminder);
        //Assert
        Reminder result = reminderService.saveOrUpdate(
                reminder.getTitle(),
                reminder.getDescription(),
                reminder.getRemind()
        );
        assertNotNull(result);
        assertEquals(savedReminder.getId(), result.getId());
        verify(reminderRepository, times(1)).save(reminder);
    }

    @Test
    @DisplayName("Напоминание удаляется корректно")
    void shouldDeleteReminderCorrectly() {
        // Arrange
        doNothing().when(reminderRepository).deleteById(anyLong());
        // ACT
        reminderService.deleteReminder(1L); // Вызываем метод сервиса
        // Assert
        verify(reminderRepository, times(1)).deleteById(anyLong());
    }

    @Test
    @DisplayName("Поиск напоминаний по параметрам вызывает репозиторий с собранной спецификацией")
    void searchReminderByParameters_ShouldCallRepoWithBuiltSpec() {
        String testTitle = "Уборка";
        LocalDate testDate = LocalDate.now();
        LocalTime testTime = LocalTime.now();

        List<Reminder> expectedList = List.of(
                Reminder.builder().id(100L).title("Найдено").build()
        );

        when(reminderRepository.findAll(any(Specification.class))).thenReturn(expectedList);

        List<Reminder> result = reminderService.searchReminderByParameters(testTitle, testDate, testTime);

        assertEquals(expectedList, result);
        verify(reminderRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("Сортировка напоминаний вызывает репозиторий с параметрами сортировки")
    void sortByParameters_ShouldCallRepositoryWithSort() {
        Sort sort = Sort.by(Sort.Direction.ASC, "title");

        List<Reminder> expectedList = List.of(
                Reminder.builder().id(1L).title("А").build(),
                Reminder.builder().id(2L).title("Б").build()
        );

        when(reminderRepository.findAll(sort)).thenReturn(expectedList);

        Iterable<Reminder> result = reminderService.sortByParameters(sort);

        assertEquals(expectedList, result);
        verify(reminderRepository, times(1)).findAll(sort);
    }



    @Test
    @DisplayName("Пагинация возвращает страницу из репозитория")
    void searchWithPagination_ShouldReturnPageFromRepo() {
        int pageNumber = 0;
        int pageSize = 10;

        Page<Reminder> expectedPage = new PageImpl<>(List.of(
                Reminder.builder().id(1L).title("Элемент 1").build()
        ));

        when(reminderRepository.findAll(PageRequest.of(pageNumber, pageSize))).thenReturn(expectedPage);

        Page<Reminder> result = reminderService.searchWithPagination(pageNumber, pageSize);

        assertEquals(expectedPage, result);
        verify(reminderRepository, times(1)).findAll(PageRequest.of(pageNumber, pageSize));
    }

}
