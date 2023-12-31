package com.syed.authservice.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.syed.authservice.clients.IdentityServiceClient;
import com.syed.authservice.domain.model.AuthorityModel;
import com.syed.authservice.domain.model.RoleModel;
import com.syed.authservice.domain.model.enums.ScopeEnum;
import com.syed.authservice.domain.model.response.ClientResponse;
import com.syed.authservice.domain.model.response.UserV2Response;
import com.syed.authservice.filter.ClientContextHolder;
import com.syed.authservice.filter.VerifyAppClientFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.session.DisableEncodeUrlFilter;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;
import java.util.List;
import java.util.Set;
import java.util.Collection;

@Slf4j
@Configuration
public class SecurityConfig {

    @Autowired
    private IdentityServiceClient identityServiceClient;

    @Bean
    @Order(1)
    public SecurityFilterChain authServerFilterChain(HttpSecurity http) throws Exception {
        /*
         * DEFAULT ENDPOINTS
         *
         * Authorization endpoint           /oauth2/authorize
         * Token endpoint                   /oauth2/token
         * Token revocation endpoint        /oauth2/revoke
         * Introspection endpoint           /oauth2/introspect
         * JWK uri endpoint                 /oauth2/jwks
         *
         */
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        // enable OIDC - id token
        // also need to append &scope=openid in url
        // also exposes /.well-known/openid-configuration endpoint
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());

        http.addFilterBefore(new VerifyAppClientFilter(identityServiceClient), DisableEncodeUrlFilter.class);

        // redirect unauthenticated user to /login
        http.exceptionHandling(exceptions -> {
            exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));
        });

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain appFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(Customizer.withDefaults());

        http.authorizeHttpRequests(authorize -> {
            authorize.requestMatchers("/health").permitAll()
                    .anyRequest().authenticated();
        });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();

        JWKSet jwkSet = new JWKSet(rsaKey);

        return new ImmutableJWKSet<>(jwkSet);
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }

        return keyPair;
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> oAuth2TokenCustomizer(IdentityServiceClient identityServiceClient) {
        return context -> {
            log.info("Entering SecurityConfig:oAuth2TokenCustomizer");

            List<String> grantTypes = List.of("authorization_code", "refresh_token");

            // add authorities/roles to JWT obtained using either authorization_code & refresh_token
            if (grantTypes.contains(context.getAuthorizationGrantType().getValue())) {
                log.info("adding claims to JWT obtained by authorization_code or refresh_token");

                Collection<? extends GrantedAuthority> authorities = context.getPrincipal().getAuthorities();
                context.getClaims().claim("authorities", authorities.stream().map(GrantedAuthority::getAuthority).toList());

                ResponseEntity<UserV2Response> res = identityServiceClient.getUser("auth-service", null, null, context.getPrincipal().getName());

                if (res != null) {
                    Set<RoleModel> roles = res.getBody().getRoles();
                    context.getClaims().claim("roles", roles.stream().map(RoleModel::getName).toList());
                }
            } else if (context.getAuthorizationGrantType().getValue().equalsIgnoreCase("client_credentials")) {
                log.info("adding claims to JWT obtained by client_credentials");

                ClientResponse res = ClientContextHolder.getClientResponse();

                if (res != null) {
                    // have to manually add scope, authorities & roles to tokens gained using client_credentials
                    Set<ScopeEnum> scopes = res.getScopes();
                    context.getClaims().claim("scope", scopes.stream().map(ScopeEnum::getValue).toList());

                    Set<AuthorityModel> authorities = res.getAuthorities();
                    context.getClaims().claim("authorities", authorities.stream().map(AuthorityModel::getName).toList());

                    Set<RoleModel> roles = res.getRoles();
                    context.getClaims().claim("roles", roles.stream().map(RoleModel::getName).toList());
                }
            }
        };
    }
}
