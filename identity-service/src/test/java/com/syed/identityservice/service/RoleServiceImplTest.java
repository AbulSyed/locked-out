package com.syed.identityservice.service;

import com.syed.identityservice.data.entity.RoleEntity;
import com.syed.identityservice.data.entity.UserEntity;
import com.syed.identityservice.data.repository.RoleRepository;
import com.syed.identityservice.data.repository.UserRepository;
import com.syed.identityservice.domain.enums.RoleToEnum;
import com.syed.identityservice.domain.model.request.RoleRequest;
import com.syed.identityservice.domain.model.response.AddRoleResponse;
import com.syed.identityservice.domain.model.response.RoleResponse;
import com.syed.identityservice.exception.custom.FieldAlreadyExistsException;
import com.syed.identityservice.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private RoleEntity roleEntity;
    private RoleRequest createRoleRequest;
    private UserEntity userEntity;
    private UserEntity userEntity2;
    private Set<RoleEntity> roleEntitySet;

    @BeforeEach
    void setUp() {
        roleEntity = RoleEntity.builder()
                .id(1L)
                .name("ADMIN")
                .build();
        createRoleRequest = RoleRequest.builder()
                .name("ADMIN")
                .build();

        userEntity = UserEntity.builder()
                .id(1L)
                .username("joe")
                .password("123")
                .email("joe@mail.com")
                .phoneNumber("079")
                .userApp(null)
                .roles(new HashSet<>())
                .authorities(new HashSet<>())
                .createdAt(LocalDateTime.now())
                .build();

        roleEntitySet = new HashSet<>();
        roleEntitySet.add(roleEntity);

        userEntity2 = UserEntity.builder()
                .id(1L)
                .username("joe")
                .password("123")
                .email("joe@mail.com")
                .phoneNumber("079")
                .userApp(null)
                .roles(roleEntitySet)
                .authorities(new HashSet<>())
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void createRole() {
        when(roleRepository.existsByName(any(String.class))).thenReturn(false);
        when(roleRepository.save(any(RoleEntity.class))).thenReturn(roleEntity);

        RoleResponse res = roleService.createRole(createRoleRequest);

        assertThat(res).isNotNull()
                .hasFieldOrPropertyWithValue("name", "ADMIN");
    }

    @Test
    void createRole_throwsFieldAlreadyExistsException() {
        when(roleRepository.existsByName(any(String.class))).thenReturn(true);

        Throwable throwable = assertThrows(FieldAlreadyExistsException.class, () -> roleService.createRole(createRoleRequest));

        assertEquals("Name field is already in use, please try another name", throwable.getMessage());
    }

    @Test
    void addRole_ToUser() {
        when(roleRepository.findById(any(Long.class))).thenReturn(Optional.of(roleEntity));
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userEntity));

        AddRoleResponse res = roleService.addRole(RoleToEnum.USER, 1L, 1L);

        assertThat(res).isNotNull()
                .hasFieldOrPropertyWithValue("message", "Role ADMIN added to user joe");
    }

    @Test
    void deleteRole_FromUser() {
        // before removing role
        assertEquals(1, userEntity2.getRoles().size());

        when(roleRepository.findById(any(Long.class))).thenReturn(Optional.of(roleEntity));
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userEntity2));

        roleService.deleteRoleFrom(RoleToEnum.USER, 1L, 1L);

        assertThat(userEntity2).isNotNull();
        // after removing role
        assertEquals(0, userEntity2.getRoles().size());
    }
}
