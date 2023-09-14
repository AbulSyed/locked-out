package com.syed.identityservice.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

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

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "userApp", cascade = CascadeType.ALL)
    private Set<UserEntity> users;

    @OneToMany(mappedBy = "userApp", cascade = CascadeType.ALL)
    private Set<ClientEntity> clients;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
