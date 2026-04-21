package com.example.ReminderApp.repos;
import com.example.ReminderApp.entity.Reminder;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReminderSpecifications {


    public static final String FIELD_TITLE = "title";
    public static final String FIELD_REMIND = "remind";


    private ReminderSpecifications() {
    }


    public static Specification<Reminder> titleContains(String title) {
        return (root, query, builder) -> {
            if (title == null || title.isEmpty()) {
                return null;
            }

            return builder.like(
                    builder.lower(root.get(FIELD_TITLE)),
                    "%" + title.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Reminder> dateEquals(LocalDate date) {
        return (root, query, builder) -> {
            if (date == null) {
                return null;
            }
            return builder.equal(
                    builder.function("date", LocalDate.class, root.get(FIELD_REMIND)),
                    date
            );
        };
    }

    public static Specification<Reminder> timeEquals(LocalTime time) {
        return (root, query, builder) -> {
            if (time == null) {
                return null;
            }
            return builder.equal(
                    builder.function("time", LocalTime.class, root.get(FIELD_REMIND)),
                    time
            );
        };
    }


    public static Specification<Reminder> filterBy(String title, LocalDate date, LocalTime time) {
        Specification<Reminder> spec = null; // Начнем с null

        if (title != null && !title.isEmpty()) {
            spec = titleContains(title);
        }
        if (date != null) {
            spec = (spec == null) ? dateEquals(date) : spec.and(dateEquals(date));
        }
        if (time != null) {
            spec = (spec == null) ? timeEquals(time) : spec.and(timeEquals(time));
        }

        return spec;
    }
}
