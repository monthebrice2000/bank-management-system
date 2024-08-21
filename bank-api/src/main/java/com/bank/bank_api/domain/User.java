package com.bank.bank_api.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "USERS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(unique = true)
    private String email;

    private String name;

    private String password;

    private String role = "USER";

    private boolean enabled = true;

    private LocalDate birthday;

    @Column(insertable = true, updatable = false)
    private LocalDateTime created;

    private LocalDateTime modified;

    // public User() {
    // }

    // public User(String email, String name, String password, String birthday) {
    //     this.email = email;
    //     this.name = name;
    //     this.password = password;
    //     this.birthday = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    // }

    // public User(String email, String name, String password, LocalDate birthday) {
    //     this.email = email;
    //     this.name = name;
    //     this.password = password;
    //     this.birthday = birthday;
    // }

    // public User(String email, String name, String password, String birthday, String role, boolean enabled) {
    //     this(email, name, password, birthday);
    //     this.role = role;
    //     this.enabled = enabled;
    // }

    @PrePersist
    void onCreate() {
        this.setCreated(LocalDateTime.now());
        this.setModified(LocalDateTime.now());
    }

    @PreUpdate
    void onUpdate() {
        this.setModified(LocalDateTime.now());
    }

}
