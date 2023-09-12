package com.michael.usersService.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 50)
    private String username;
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    @Column(unique = true, nullable = false, length = 120)
    private String email;
    @Column(nullable = false)
    private String encryptedPassword;

    @CreationTimestamp
    @Column(updatable = false, name = "registration_date")
    private LocalDateTime registrationDate;
    @UpdateTimestamp
    @Column(updatable = true, name = "update_date")
    private LocalDateTime updateDate;

}
