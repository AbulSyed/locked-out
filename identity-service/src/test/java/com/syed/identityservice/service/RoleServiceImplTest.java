package com.syed.identityservice.service;

import com.syed.identityservice.BaseTest;
import com.syed.identityservice.data.entity.RoleEntity;
import com.syed.identityservice.data.entity.UserEntity;
import com.syed.identityservice.data.repository.RoleRepository;
import com.syed.identityservice.data.repository.UserRepository;
import com.syed.identityservice.domain.enums.RoleToEnum;
import com.syed.identityservice.domain.model.request.AlterRoleRequest;
import com.syed.identityservice.domain.model.request.RoleRequest;
import com.syed.identityservice.domain.model.response.MessageResponse;
import com.syed.identityservice.domain.model.response.RoleResponse;
import com.syed.identityservice.exception.custom.FieldAlreadyExistsException;
import com.syed.identityservice.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest extends BaseTest<Object> {

    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void createRole() {
        RoleEntity roleEntity = createRoleEntity(1L, "ADMIN", new HashSet<>(), new HashSet<>());
        RoleRequest createRoleRequest = createRoleRequest("ADMIN");

        when(roleRepository.existsByName(any(String.class))).thenReturn(false);
        when(roleRepository.save(any(RoleEntity.class))).thenReturn(roleEntity);

        RoleResponse res = roleService.createRole(createRoleRequest);

        assertThat(res).isNotNull()
                .hasFieldOrPropertyWithValue("name", "ADMIN");
    }

    @Test
    void createRole_throwsFieldAlreadyExistsException() {
        RoleRequest createRoleRequest = createRoleRequest("ADMIN");

        when(roleRepository.existsByName(any(String.class))).thenReturn(true);

        Throwable throwable = assertThrows(FieldAlreadyExistsException.class, () -> roleService.createRole(createRoleRequest));

        assertEquals("Name field is already in use, please try another value", throwable.getMessage());
    }

    @Test
    void alterRoles_OfUser() {
        AlterRoleRequest alterRoleRequest = createAlterRoleRequest(1L, List.of(1L));
        RoleEntity roleEntity = createRoleEntity(1L, "ADMIN", new HashSet<>(), new HashSet<>());
        UserEntity userEntity = createUserEntity(1L, "abul", "123", "abul@mail.com", "079",
                new HashSet<>(), new HashSet<>(), LocalDateTime.now());

        when(roleRepository.findByIdIn(any())).thenReturn(Set.of(roleEntity));
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userEntity));

        MessageResponse res = roleService.alterRoles(RoleToEnum.USER, alterRoleRequest);

        assertThat(res).isNotNull()
                .hasFieldOrPropertyWithValue("message", "Role/s added to user abul");
    }

    @Test
    void getRoleList() {
        List<RoleEntity> getRoleEntityList = List.of(
                createRoleEntity(1L, "ADMIN", new HashSet<>(), new HashSet<>())
        );

        when(roleRepository.findAll()).thenReturn(getRoleEntityList);

        List<String> res = roleService.getRoleList();

        assertThat(res).isNotNull()
                .hasSize(1);
    }

    @Test
    void deleteRoleFrom_User() {
        RoleEntity roleEntity = createRoleEntity(1L, "MANAGER", Collections.emptySet(), Collections.emptySet());
        Set<RoleEntity> roleEntitySet = new HashSet<>();
        roleEntitySet.add(roleEntity);
        UserEntity userEntity = createUserEntity(1L, "joe", "123", "joe@mail.com", "079",
                null, roleEntitySet, null, LocalDateTime.now());

        // before removing role
        assertEquals(1, userEntity.getRoles().size());

        when(roleRepository.findById(any(Long.class))).thenReturn(Optional.of(roleEntity));
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userEntity));

        roleService.deleteRoleFrom(RoleToEnum.USER, 1L, 1L);

        assertThat(userEntity).isNotNull();
        // after removing role
        assertEquals(0, userEntity.getRoles().size());
    }

    @Test
    void deleteRole() {
        RoleEntity roleEntity = createRoleEntity(1L, "ADMIN", new HashSet<>(), new HashSet<>());

        when(roleRepository.findById(1L)).thenReturn(Optional.of(roleEntity));

        roleService.deleteRole(1L);

        verify(roleRepository).delete(roleEntity);
    }
}
