package com.syed.authservice.service;

import com.syed.authservice.clients.IdentityServiceClient;
import com.syed.authservice.domain.model.UserModel;
import com.syed.authservice.domain.model.response.UserV2Response;
import com.syed.authservice.domain.model.security.SecurityUser;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IdentityServiceClient identityServiceClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Entering UserDetailsServiceImpl:loadUserByUsername");

        ResponseEntity<UserV2Response> res = null;

        try {
            res = identityServiceClient.getUser("auth-service", null, null, username);
            log.info("fetched user: {} in UserDetailsServiceImpl", res.getBody().getUsername());
        } catch (FeignException.FeignClientException ex) {
            log.error(ex.getMessage());
        }

        if (res == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        UserModel user = UserModel.builder()
                .id(res.getBody().getId())
                .username(res.getBody().getUsername())
                .password(res.getBody().getPassword())
                .email(res.getBody().getEmail())
                .phoneNumber(res.getBody().getPhoneNumber())
                .roles(res.getBody().getRoles())
                .authorities(res.getBody().getAuthorities())
                .createdAt(res.getBody().getCreatedAt())
                .build();

        return new SecurityUser(user);
    }
}
