package com.profport.lms.course.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "pw_hash", columnDefinition = "TEXT", nullable = false)
    private String pwHash;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @CreationTimestamp
    private Instant createdAt;
}
