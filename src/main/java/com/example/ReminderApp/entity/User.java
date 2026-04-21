package com.example.ReminderApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name", nullable = false,length = 50)
    private String userName;
    @Column(name = "email", nullable = false,unique = true,length = 100)
    private String email;
    @Column(name = "telegram_id", nullable = false,unique = true,length = 50)
    private String telegramId;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Reminder> tasks = new ArrayList<>() {
    };
}
