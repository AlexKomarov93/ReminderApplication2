package com.example.ReminderApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reminder")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reminder {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false, length = 200)
    private String title;
    @Column(name = "description", length = 1000)
    private String description;
    @Column(name = "remind", nullable = false)
    private LocalDateTime remind;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;


}
