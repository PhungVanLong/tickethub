package com.tickethub.indentity.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "captcha_logs")
public class CaptchaLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String action;
    private String token;
    private boolean passed;
    private float score;
    private LocalDateTime createdAt;
}
