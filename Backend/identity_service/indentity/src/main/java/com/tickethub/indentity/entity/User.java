package com.tickethub.indentity.entity;

import com.tickethub.indentity.entity.enums.UserRole;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users" )
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;
    @Column(nullable=false, unique=true)
    private String email;
    @Column(nullable=false)
    private String passwordHash;

    private String fullName;

    private String phone;

    private String avatarUrll;

    @Enumerated(EnumType.STRING)
    private UserRole roles;

    private boolean isVerified;

    private boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
