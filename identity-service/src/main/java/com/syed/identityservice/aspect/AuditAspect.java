package com.syed.identityservice.aspect;

import com.syed.identityservice.data.entity.AuditRequestEntity;
import com.syed.identityservice.data.repository.AuditRequestRepository;
import com.syed.identityservice.domain.enums.ProcessEnum;
import com.syed.identityservice.domain.enums.RequestStatusEnum;
import com.syed.identityservice.domain.enums.RequestTypeEnum;
import com.syed.identityservice.utility.MapperUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
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
    public void auditInitialRequestAdvice(AuditRequest auditRequest, String correlationId) {
        ProcessEnum process = auditRequest.process();
        RequestTypeEnum requestType = auditRequest.requestType();
        RequestStatusEnum requestStatus = auditRequest.requestStatus();
        String log = auditRequest.log();

        AuditRequestEntity initialRequestEntity = MapperUtil.createInitialRequestEntity(
                correlationId, process, requestType, requestStatus, log
        );
        requestRepository.save(initialRequestEntity);
    }

    @AfterReturning("@annotation(auditRequest) && args(correlationId,..)")
    public void auditSuccessRequestAdvice(AuditRequest auditRequest, String correlationId) {
        ProcessEnum process = auditRequest.process();
        RequestTypeEnum requestType = auditRequest.requestType();

        AuditRequestEntity fulfilledRequestEntity = MapperUtil.createInitialRequestEntity(
                correlationId, process, requestType, RequestStatusEnum.FULFILLED, ""
        );
        requestRepository.save(fulfilledRequestEntity);
    }

    @AfterThrowing(value = "@annotation(auditRequest) && args(correlationId,..)", throwing = "ex")
    public void auditFailedRequestAdvice(AuditRequest auditRequest, String correlationId, Throwable ex) {
        ProcessEnum process = auditRequest.process();
        RequestTypeEnum requestType = auditRequest.requestType();

        AuditRequestEntity rejectedRequestEntity = MapperUtil.createInitialRequestEntity(
                correlationId, process, requestType, RequestStatusEnum.REJECTED, ex.getMessage()
        );
        requestRepository.save(rejectedRequestEntity);
    }
}
