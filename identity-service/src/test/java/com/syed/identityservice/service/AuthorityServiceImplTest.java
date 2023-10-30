package com.syed.identityservice.service;

import com.syed.identityservice.data.entity.AuthorityEntity;
import com.syed.identityservice.data.entity.UserEntity;
import com.syed.identityservice.data.repository.AuthorityRepository;
import com.syed.identityservice.data.repository.UserRepository;
import com.syed.identityservice.domain.enums.AuthorityToEnum;
import com.syed.identityservice.domain.model.request.AuthorityRequest;
import com.syed.identityservice.domain.model.response.MessageResponse;
import com.syed.identityservice.domain.model.response.AuthorityResponse;
import com.syed.identityservice.service.impl.AuthorityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorityServiceImplTest {

    @Mock
    private AuthorityRepository authorityRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthorityServiceImpl authorityService;

    private AuthorityEntity authorityEntity;
    private AuthorityRequest createAuthorityRequest;
    private UserEntity userEntity;
    private List<AuthorityEntity> getAuthorityEntityList;
    private Set<AuthorityEntity> authorityEntitySet;
    private UserEntity userEntity2;
    private AuthorityEntity authorityEntity2;

    @BeforeEach
    void setUp() {
        authorityEntity = AuthorityEntity.builder()
                .id(1L)
                .name("read")
                .build();
        createAuthorityRequest = AuthorityRequest.builder()
                .name("read")
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

        getAuthorityEntityList = List.of(
                authorityEntity
        );

        authorityEntitySet = new HashSet<>();
        authorityEntitySet.add(authorityEntity);

        userEntity2 = UserEntity.builder()
                .id(1L)
                .username("joe")
                .password("123")
                .email("joe@mail.com")
                .phoneNumber("079")
                .userApp(null)
                .roles(null)
                .authorities(authorityEntitySet)
                .createdAt(LocalDateTime.now())
                .build();

        authorityEntity2 = AuthorityEntity.builder()
                .id(1L)
                .name("read")
                .users(new HashSet<>())
                .clients(new HashSet<>())
                .build();
    }

    @Test
    void createAuthority() {
        when(authorityRepository.existsByName(any(String.class))).thenReturn(false);
        when(authorityRepository.save(any(AuthorityEntity.class))).thenReturn(authorityEntity);

        AuthorityResponse res = authorityService.createAuthority(createAuthorityRequest);

        assertThat(res).isNotNull()
                .hasFieldOrPropertyWithValue("name", "read");
    }

    @Test
    void addAuthority_ToUser() {
        when(authorityRepository.findById(any(Long.class))).thenReturn(Optional.of(authorityEntity));
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userEntity));

        MessageResponse res = authorityService.addAuthority(AuthorityToEnum.USER, 1L, 1L);

        assertThat(res).isNotNull()
                .hasFieldOrPropertyWithValue("message", "Authority read added to user joe");
    }

    @Test
    void getAuthorityList() {
        when(authorityRepository.findAll()).thenReturn(getAuthorityEntityList);

        List<String> res = authorityService.getAuthorityList();

        assertThat(res).isNotNull()
                .hasSize(1);
    }

    @Test
    void deleteAuthorityFrom_User() {
        // before removing authority
        assertEquals(1, userEntity2.getAuthorities().size());

        when(authorityRepository.findById(any(Long.class))).thenReturn(Optional.of(authorityEntity));
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userEntity2));

        authorityService.deleteAuthorityFrom(AuthorityToEnum.USER, 1L, 1L);

        assertThat(userEntity2).isNotNull();
        // after removing authority
        assertEquals(0, userEntity2.getAuthorities().size());
    }

    @Test
    void deleteAuthority() {
        when(authorityRepository.findById(1L)).thenReturn(Optional.of(authorityEntity2));

        authorityService.deleteAuthority(1L);

        verify(authorityRepository).delete(authorityEntity2);
    }
}
