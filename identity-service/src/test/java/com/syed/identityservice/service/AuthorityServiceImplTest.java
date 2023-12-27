package com.syed.identityservice.service;

import com.syed.identityservice.BaseTest;
import com.syed.identityservice.data.entity.AuthorityEntity;
import com.syed.identityservice.data.entity.UserEntity;
import com.syed.identityservice.data.repository.AuthorityRepository;
import com.syed.identityservice.data.repository.UserRepository;
import com.syed.identityservice.domain.enums.AuthorityToEnum;
import com.syed.identityservice.domain.model.request.AlterAuthorityRequest;
import com.syed.identityservice.domain.model.request.AuthorityRequest;
import com.syed.identityservice.domain.model.response.MessageResponse;
import com.syed.identityservice.domain.model.response.AuthorityResponse;
import com.syed.identityservice.service.impl.AuthorityServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorityServiceImplTest extends BaseTest<Object> {

    @Mock
    private AuthorityRepository authorityRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthorityServiceImpl authorityService;

    @Test
    void createAuthority() {
        AuthorityEntity authorityEntity = createAuthorityEntity(1L, "read", Collections.emptySet(), Collections.emptySet());
        AuthorityRequest createAuthorityRequest = createAuthorityRequest("read");

        when(authorityRepository.existsByName(any(String.class))).thenReturn(false);
        when(authorityRepository.save(any(AuthorityEntity.class))).thenReturn(authorityEntity);

        AuthorityResponse res = authorityService.createAuthority(createAuthorityRequest);

        assertThat(res).isNotNull()
                .hasFieldOrPropertyWithValue("name", "read");
    }

    @Test
    void alterAuthority_OfUser() {
        AlterAuthorityRequest alterAuthorityRequest = createAlterAuthorityRequest(1L, List.of(1L));
        AuthorityEntity authorityEntity = createAuthorityEntity(1L, "write", Collections.emptySet(), Collections.emptySet());
        UserEntity userEntity = createUserEntity(1L, "harry", "123", "harry@mail.com", "079",
                new HashSet<>(), new HashSet<>(), LocalDateTime.now());

        when(authorityRepository.findByIdIn(any())).thenReturn(Set.of(authorityEntity));
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userEntity));

        MessageResponse res = authorityService.alterAuthority(AuthorityToEnum.USER, alterAuthorityRequest);

        assertThat(res).isNotNull()
                .hasFieldOrPropertyWithValue("message", "Authority/s added to user harry");
    }

    @Test
    void getAuthorityList() {
        AuthorityEntity authorityEntity = createAuthorityEntity(1L, "read", Collections.emptySet(), Collections.emptySet());
        List<AuthorityEntity> getAuthorityEntityList = List.of(authorityEntity);

        when(authorityRepository.findAll()).thenReturn(getAuthorityEntityList);

        List<String> res = authorityService.getAuthorityList();

        assertThat(res).isNotNull()
                .hasSize(1);
    }

    @Test
    void deleteAuthorityFrom_User() {
        AuthorityEntity authorityEntity = createAuthorityEntity(1L, "read", Collections.emptySet(), Collections.emptySet());
        Set<AuthorityEntity> authorityEntitySet = new HashSet<>();
        authorityEntitySet.add(authorityEntity);
        UserEntity userEntity = createUserEntity(1L, "joe", "123", "joe@mail.com", "079",
            null, null, authorityEntitySet, LocalDateTime.now());

        // before removing authority
        assertEquals(1, userEntity.getAuthorities().size());

        when(authorityRepository.findById(any(Long.class))).thenReturn(Optional.of(authorityEntity));
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userEntity));

        authorityService.deleteAuthorityFrom(AuthorityToEnum.USER, 1L, 1L);

        assertThat(userEntity).isNotNull();
        // after removing authority
        assertEquals(0, userEntity.getAuthorities().size());
    }

    @Test
    void deleteAuthority() {
        AuthorityEntity authorityEntity = createAuthorityEntity(1L, "read", Collections.emptySet(), Collections.emptySet());

        when(authorityRepository.findById(1L)).thenReturn(Optional.of(authorityEntity));

        authorityService.deleteAuthority(1L);

        verify(authorityRepository).delete(authorityEntity);
    }
}
