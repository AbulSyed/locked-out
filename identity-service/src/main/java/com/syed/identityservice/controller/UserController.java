package com.syed.identityservice.controller;

import com.syed.identityservice.aspect.AuditRequest;
import com.syed.identityservice.domain.enums.ProcessEnum;
import com.syed.identityservice.domain.enums.RequestStatusEnum;
import com.syed.identityservice.domain.enums.RequestTypeEnum;
import com.syed.identityservice.domain.model.request.UserRequest;
import com.syed.identityservice.domain.model.response.UserResponse;
import com.syed.identityservice.domain.model.response.UserV2Response;
import com.syed.identityservice.exception.ErrorConstant;
import com.syed.identityservice.exception.custom.InvalidRequestException;
import com.syed.identityservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<UserResponse> createUser(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @PathVariable Long appId,
            @RequestBody UserRequest request
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
    @GetMapping("/get-user")
    public ResponseEntity<UserV2Response> getUser(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "appName", required = false) String appName,
            @RequestParam(value = "username", required = false) String username
    ) {
        if ((userId == null && appName == null && username == null)
            || (userId == null && appName != null && username == null)
            || (userId == null && appName == null && username != null)) {
            throw new InvalidRequestException(ErrorConstant.INVALID_REQUEST.getValue());
        }

        return new ResponseEntity<>(userService.getUser(userId, appName, username), HttpStatus.OK);
    }

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.USER,
            requestType = RequestTypeEnum.READ,
            requestStatus = RequestStatusEnum.PENDING,
            log = "get user list request initiated"
    )
    @GetMapping("/get-user-list")
    public ResponseEntity<List<UserV2Response>> getUserList(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId
    ) {
        return new ResponseEntity<>(userService.getUserList(), HttpStatus.OK);
    }

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.USER,
            requestType = RequestTypeEnum.READ,
            requestStatus = RequestStatusEnum.PENDING,
            log = "get user list by app request initiated"
    )
    @GetMapping("/get-user-list-by-app")
    public ResponseEntity<List<UserV2Response>> getUserListByApp(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @RequestParam(value = "appId", required = false) Long appId,
            @RequestParam(value = "appName", required = false) String appName
    ) {
        return new ResponseEntity<>(userService.getUserListByApp(appId, appName), HttpStatus.OK);
    }

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.USER,
            requestType = RequestTypeEnum.UPDATE,
            requestStatus = RequestStatusEnum.PENDING,
            log = "update user request initiated"
    )
    @PutMapping("/update-user/{userId}")
    public ResponseEntity<UserResponse> updateUser(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @PathVariable Long userId,
            @RequestBody UserRequest request
    ) {
        return new ResponseEntity<>(userService.updateUser(userId, request), HttpStatus.OK);
    }

    @AuditRequest(
            correlationId = "#correlationId",
            process = ProcessEnum.USER,
            requestType = RequestTypeEnum.DELETE,
            requestStatus = RequestStatusEnum.PENDING,
            log = "delete user request initiated"
    )
    @DeleteMapping("/delete-user/{userId}")
    public void deleteUser(
            @RequestHeader(value = "x-correlation-id", required = true) String correlationId,
            @PathVariable Long userId
    ) {
        userService.deleteUser(userId);
    }
}
