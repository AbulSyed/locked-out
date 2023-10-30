package com.syed.authservice.service;

import com.syed.authservice.domain.model.UserModel;
import com.syed.authservice.domain.model.security.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = UserModel.builder()
                .id(1L)
                .username("test")
                .password("test")
                .email("test@mail.com")
                .phoneNumber("079")
                .roles(Collections.emptySet())
                .authorities(Collections.emptySet())
                .createdAt(LocalDateTime.now())
                .build();

        return new SecurityUser(user);
    }
}
