package com.tickethub.indentity.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "system_logs")
public class SystemLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String action;
    private String entityType;
    private UUID entityId;

    @Column(columnDefinition = "jsonb")
    private String metadata;

    private String ipAddress;
    private LocalDateTime createdAt;
}
