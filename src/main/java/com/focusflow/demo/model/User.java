package com.focusflow.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String passwordHash;
    private String email;
    private String fullName;
    private LocalDate birthDate;
    private LocalDateTime createdAt;

}
