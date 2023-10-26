package com.syed.identityservice.controller;

import com.syed.identityservice.domain.model.request.CreateRoleRequest;
import com.syed.identityservice.domain.model.response.*;
import com.syed.identityservice.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleControllerTest {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    private String correlationId;
    private CreateRoleRequest createRoleRequest;
    private CreateRoleResponse createRoleResponse;
    private ResponseEntity<CreateRoleResponse> createRoleExpectedResponse;

    @BeforeEach
    void setUp() {
        correlationId = "1";

        createRoleRequest = CreateRoleRequest.builder()
                .name("admin")
                .build();
        createRoleResponse = CreateRoleResponse.builder()
                .id(1L)
                .name("admin")
                .build();
        createRoleExpectedResponse = ResponseEntity.status(HttpStatus.CREATED).body(createRoleResponse);
    }

    @Test
    void createRole() {
        when(roleService.createRole(any(CreateRoleRequest.class))).thenReturn(createRoleResponse);

        ResponseEntity<CreateRoleResponse> res = roleController.createRole(correlationId, createRoleRequest);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), createRoleExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), createRoleExpectedResponse.getBody());
    }
}
