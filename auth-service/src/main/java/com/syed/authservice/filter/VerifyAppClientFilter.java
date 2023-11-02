package com.syed.authservice.filter;

import com.syed.authservice.clients.IdentityServiceClient;
import com.syed.authservice.domain.model.response.ClientResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

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

        // 1. only enter if user is attempting to get authorization_code as we don't want this logic always being executed
        if (request.getParameter("response_type") != null && request.getParameter("response_type").equals("code")) {
            System.out.println("start main VerifyAppFilter logic");
            // 2. app null check
            if (request.getParameter("appname") == null) {
                response.getWriter().println("appname is null");
                return;
            }
            // 3. fetch list of clients that belong to app passed
            System.out.println("App is " + request.getParameter("appname"));
            System.out.println("Client is " + request.getParameter("client_id"));
            ResponseEntity<List<ClientResponse>> res = identityServiceClient.getClientListByApp("auth-service", null, request.getParameter("appname"));

            // 4. verify if client associated with app
            if (Objects.requireNonNull(res.getBody()).stream().anyMatch(el -> el.getClientId().equals(request.getParameter("client_id")))) {
                System.out.println("verification successful");
            } else {
                response.getWriter().println("App " + request.getParameter("appname") + " not associated with client " + request.getParameter("client_id"));
                return;
            }
        }

        filterChain.doFilter(request, response);

        System.out.println("after VerifyAppFilter");
    }
}
