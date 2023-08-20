package com.syed.identityservice.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "app")
public class AppEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToOne(mappedBy = "userApp")
    private UserEntity users;

    @OneToOne(mappedBy = "userApp")
    private ClientEntity clients;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
