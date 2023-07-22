package com.syed.identityservice.aspect;

import com.syed.identityservice.data.repository.AuditRequestRepository;
import com.syed.identityservice.domain.enums.ProcessEnum;
import com.syed.identityservice.domain.enums.RequestStatusEnum;
import com.syed.identityservice.domain.enums.RequestTypeEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(2)
@Aspect
@Component
@AllArgsConstructor
public class AuditAspect {

    private final AuditRequestRepository requestRepository;

    @Before("@annotation(auditRequest) && args(correlationId,..)")
    public void auditInitialRequestAdvice(JoinPoint joinPoint, AuditRequest auditRequest, String correlationId) {
        ProcessEnum process = auditRequest.process();
        RequestTypeEnum requestType = auditRequest.requestType();
        RequestStatusEnum requestStatus = auditRequest.requestStatus();
        String log = auditRequest.log();

        System.out.println("Correlation ID: " + correlationId);
        System.out.println("Process: " + process);
        System.out.println("Request Type: " + requestType);
        System.out.println("Request Status: " + requestStatus);
        System.out.println("Log: " + log);
    }
}
