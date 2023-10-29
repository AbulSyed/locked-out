package com.syed.identityservice.service;

import com.syed.identityservice.data.entity.AuthorityEntity;
import com.syed.identityservice.data.repository.AuthorityRepository;
import com.syed.identityservice.data.repository.UserRepository;
import com.syed.identityservice.domain.model.request.AuthorityRequest;
import com.syed.identityservice.domain.model.response.AuthorityResponse;
import com.syed.identityservice.service.impl.AuthorityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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

    @BeforeEach
    void setUp() {
        authorityEntity = AuthorityEntity.builder()
                .id(1L)
                .name("read")
                .build();
        createAuthorityRequest = AuthorityRequest.builder()
                .name("read")
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
}
