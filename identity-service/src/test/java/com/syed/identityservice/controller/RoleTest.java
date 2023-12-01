package com.syed.identityservice.controller;

import com.syed.identityservice.BaseTest;
import com.syed.identityservice.domain.enums.RoleToEnum;
import com.syed.identityservice.domain.model.request.RoleRequest;
import com.syed.identityservice.domain.model.response.*;
import com.syed.identityservice.service.RoleService;
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
class RoleTest extends BaseTest<Object> {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    @Test
    void createRole() {
        RoleRequest createRoleRequest = createRoleRequest("ADMIN");
        RoleResponse createRoleResponse = createRoleResponse(1L, "ADMIN");
        ResponseEntity<Object> createRoleExpectedResponse = createExpectedResponse(HttpStatus.CREATED, createRoleResponse);

        when(roleService.createRole(any(RoleRequest.class))).thenReturn(createRoleResponse);

        ResponseEntity<RoleResponse> res = roleController.createRole(correlationId, createRoleRequest);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), createRoleExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), createRoleExpectedResponse.getBody());
    }

    @Test
    void addRole_ToUser() {
        MessageResponse addRoleResponse = createMessageResponse("Role ADMIN added to user Test");
        ResponseEntity<Object> addRoleExpectedResponse = createExpectedResponse(HttpStatus.CREATED, addRoleResponse);

        when(roleService.addRole(any(RoleToEnum.class), any(Long.class), any(Long.class))).thenReturn(addRoleResponse);

        ResponseEntity<MessageResponse> res = roleController.addRole(correlationId, RoleToEnum.USER, 1L, 1L);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), addRoleExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), addRoleExpectedResponse.getBody());
    }

    @Test
    void getRoleList() {
        List<String> getRoleListResponse = List.of("ADMIN");
        ResponseEntity<Object> getRoleListExpectedResponse = createExpectedResponse(HttpStatus.OK, getRoleListResponse);

        when(roleService.getRoleList()).thenReturn(getRoleListResponse);

        ResponseEntity<List<String>> res = roleController.getRoleList(correlationId);

        assertNotNull(res);
        assertEquals(res.getStatusCode(), getRoleListExpectedResponse.getStatusCode());
        assertEquals(res.getBody(), getRoleListExpectedResponse.getBody());
    }

    @Test
    void deleteRoleFrom_User() {
        roleController.deleteRoleFrom(correlationId, RoleToEnum.USER, 1L, 1L);

        verify(roleService, times(1)).deleteRoleFrom(RoleToEnum.USER, 1L, 1L);
    }

    @Test
    void deleteRole() {
        roleController.deleteRole(correlationId, 1L);

        verify(roleService, times(1)).deleteRole(1L);
    }
}
