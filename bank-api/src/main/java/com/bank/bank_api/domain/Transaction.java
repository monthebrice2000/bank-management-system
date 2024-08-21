package com.bank.bank_api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.redis.core.RedisHash;

import com.bank.bank_api.constant.TransactionType;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @NotNull
    private String id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TransactionType type;

    @Column(nullable = false)
    @NotNull
    private Double amount;

    @Column
    @NotNull
    @NotBlank
    private String notes;

    @NotNull
    private boolean completed;

    // @Column(nullable = false)
    // private Date timestamp;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private LocalDateTime modified;

    // @ManyToOne
    // @JoinColumn(name = "account_id", referencedColumnName = "id")
    // private Account account;

    // public Transaction(){
    // LocalDateTime date = LocalDateTime.now();
    // this.id = UUID.randomUUID().toString();
    // this.created = date;
    // this.modified = date;
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
