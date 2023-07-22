package com.syed.identityservice.data.entity;

import com.syed.identityservice.domain.enums.ProcessEnum;
import com.syed.identityservice.domain.enums.RequestStatusEnum;
import com.syed.identityservice.domain.enums.RequestTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "request_audit")
public class RequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "correlation_id", nullable = false)
    private String correlationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "process", nullable = false)
    private ProcessEnum process;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_type", nullable = false)
    private RequestTypeEnum requestType;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_status", nullable = false)
    private RequestStatusEnum requestStatus;

    @Column(name = "log")
    private String log;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
