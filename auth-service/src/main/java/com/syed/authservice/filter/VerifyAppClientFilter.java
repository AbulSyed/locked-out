package com.syed.authservice.filter;

import com.syed.authservice.clients.IdentityServiceClient;
import com.syed.authservice.domain.model.response.ClientResponse;
import feign.FeignException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class VerifyAppClientFilter extends OncePerRequestFilter {

    private final IdentityServiceClient identityServiceClient;

    public VerifyAppClientFilter(IdentityServiceClient identityServiceClient) {
        this.identityServiceClient = identityServiceClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("Entering VerifyAppClientFilter:doFilterInternal");

        ResponseEntity<ClientResponse> res = null;

        // 1. only enter if user is attempting to get authorization_code as we don't want this logic always being executed
        if (request.getParameter("response_type") != null && request.getParameter("response_type").equals("code")
            || request.getParameter("grant_type") != null && request.getParameter("grant_type").equals("authorization_code")
            || request.getParameter("grant_type") != null && request.getParameter("grant_type").equals("client_credentials")) {
            // 2. app null check
            if (request.getParameter("appname") == null) {
                response.getWriter().println("appname is null, you must provide it in the request params");
                return;
            }
            String appName = request.getParameter("appname");
            String clientId = request.getParameter("client_id");

            try {
                // 3. fetch client that belong to app passed
                res = identityServiceClient.getClient("auth-service", appName, clientId);
                log.info("fetched clientId: {} in VerifyAppClientFilter", res.getBody().getClientId());
            } catch (FeignException.FeignClientException ex) {
                response.getWriter().println("App " + request.getParameter("appname") + " not associated with client " + request.getParameter("client_id"));
                return;
            }
        }

        // 4. make clientResponse available to ClientDetailsServiceImpl
        if (res != null) {
            ClientResponse clientResponse = ClientResponse.builder()
                    .id(res.getBody().getId())
                    .clientId(res.getBody().getClientId())
                    .clientSecret(res.getBody().getClientSecret())
                    .roles(res.getBody().getRoles())
                    .authorities(res.getBody().getAuthorities())
                    .scopes(res.getBody().getScopes())
                    .authMethod(res.getBody().getAuthMethod())
                    .authGrantType(res.getBody().getAuthGrantType())
                    .redirectUri(res.getBody().getRedirectUri())
                    .createdAt(res.getBody().getCreatedAt())
                    .build();

            log.info("setting client to context in VerifyAppClientFilter");

            ClientContextHolder.setClientResponse(clientResponse);
        }

        filterChain.doFilter(request, response);
    }
}
