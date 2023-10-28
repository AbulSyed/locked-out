package com.syed.identityservice.controller;

import com.syed.identityservice.domain.enums.RoleToEnum;
import com.syed.identityservice.domain.model.request.RoleRequest;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleControllerTest {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    private String correlationId;
    private RoleRequest createRoleRequest;
    private RoleResponse createRoleResponse;
    private ResponseEntity<RoleResponse> createRoleExpectedResponse;
    private List<String> getRoleListResponse;
    private ResponseEntity<List<String>> getRoleListExpectedResponse;
    private AddRoleResponse addRoleResponse;
    private ResponseEntity<AddRoleResponse> addRoleExpectedResponse;

    @BeforeEach
    void setUp() {
        correlationId = "1";

        createRoleRequest = RoleRequest.builder()
                .name("ADMIN")
                .build();
        createRoleResponse = RoleResponse.builder()
                .id(1L)
                .name("ADMIN")
                .build();
        createRoleExpectedResponse = ResponseEntity.status(HttpStatus.CREATED).body(createRoleResponse);

        getRoleListResponse = List.of("ADMIN");
        getRoleListExpectedResponse = ResponseEntity.status(HttpStatus.OK).body(getRoleListResponse);

        addRoleResponse = AddRoleResponse.builder()
                .message("Role ADMIN added to user Test")
                .build();
        addRoleExpectedResponse = ResponseEntity.status(HttpStatus.CREATED).body(addRoleResponse);
    }

    @Test
    void createRole() {
        when(roleService.createRole(any(RoleRequest.class))).thenReturn(createRoleResponse);

        ResponseEntity<RoleResponse> res = roleController.createRole(correlationId, createRoleRequest);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), createRoleExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), createRoleExpectedResponse.getBody());
    }

    @Test
    void addRole_ToUser() {
        when(roleService.addRole(any(RoleToEnum.class), any(Long.class), any(Long.class))).thenReturn(addRoleResponse);

        ResponseEntity<AddRoleResponse> res = roleController.addRole(correlationId, RoleToEnum.USER, 1L, 1L);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), addRoleExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), addRoleExpectedResponse.getBody());
    }

    @Test
    void getRoleList() {
        when(roleService.getRoleList()).thenReturn(getRoleListResponse);

        ResponseEntity<List<String>> res = roleController.getRoleList(correlationId);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getRoleListExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getRoleListExpectedResponse.getBody());
    }

    @Test
    void deleteRole_FromUser() {
        roleController.deleteRole(correlationId, RoleToEnum.USER, 1L, 1L);

        verify(roleService, times(1)).deleteRoleFrom(RoleToEnum.USER, 1L, 1L);
    }
}
