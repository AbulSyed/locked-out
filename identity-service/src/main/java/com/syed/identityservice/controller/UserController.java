package com.syed.identityservice.controller;

import com.syed.identityservice.aspect.AuditRequest;
import com.syed.identityservice.domain.enums.ProcessEnum;
import com.syed.identityservice.domain.enums.RequestStatusEnum;
import com.syed.identityservice.domain.enums.RequestTypeEnum;
import com.syed.identityservice.domain.model.request.CreateUserRequest;
import com.syed.identityservice.domain.model.response.CreateUserResponse;
import com.syed.identityservice.domain.model.response.GetUserResponse;
import com.syed.identityservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.USER,
            requestType = RequestTypeEnum.CREATE,
            requestStatus = RequestStatusEnum.PENDING,
            log = "create user request initiated"
    )
    @PostMapping("/create-user/{appId}")
    public ResponseEntity<CreateUserResponse> createUser(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @PathVariable Long appId,
            @RequestBody CreateUserRequest request
    ) {
        return new ResponseEntity<>(userService.createUser(appId, request), HttpStatus.CREATED);
    }

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.USER,
            requestType = RequestTypeEnum.READ,
            requestStatus = RequestStatusEnum.PENDING,
            log = "get user request initiated"
    )
    @GetMapping("/get-user/{userId}")
    public ResponseEntity<GetUserResponse> getUser(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @PathVariable Long userId
    ) {
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }
}
