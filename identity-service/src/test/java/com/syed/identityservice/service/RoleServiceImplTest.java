package com.syed.identityservice.service;

import com.syed.identityservice.data.entity.RoleEntity;
import com.syed.identityservice.data.repository.RoleRepository;
import com.syed.identityservice.domain.model.request.RoleRequest;
import com.syed.identityservice.domain.model.response.RoleResponse;
import com.syed.identityservice.exception.custom.FieldAlreadyExistsException;
import com.syed.identityservice.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private RoleEntity roleEntity;
    private RoleRequest createRoleRequest;

    @BeforeEach
    void setUp() {
        roleEntity = RoleEntity.builder()
                .id(1L)
                .name("admin")
                .build();
        createRoleRequest = RoleRequest.builder()
                .name("admin")
                .build();
    }

    @Test
    void createRole() {
        when(roleRepository.existsByName(any(String.class))).thenReturn(false);
        when(roleRepository.save(any(RoleEntity.class))).thenReturn(roleEntity);

        RoleResponse res = roleService.createRole(createRoleRequest);

        assertThat(res).isNotNull()
                .hasFieldOrPropertyWithValue("name", "admin");
    }

    @Test
    void createRole_throwsFieldAlreadyExistsException() {
        when(roleRepository.existsByName(any(String.class))).thenReturn(true);

        Throwable throwable = assertThrows(FieldAlreadyExistsException.class, () -> roleService.createRole(createRoleRequest));

        assertEquals("Name field is already in use, please try another name", throwable.getMessage());
    }
}
