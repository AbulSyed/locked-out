package com.syed.identityservice.data.entity;

import com.syed.identityservice.domain.enums.AuthGrantTypeEnum;
import com.syed.identityservice.domain.enums.AuthMethodEnum;
import com.syed.identityservice.domain.enums.ScopeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "client")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private String clientId;

    @Column(name = "secret", nullable = false)
    private String secret;

    @ManyToOne(fetch = FetchType.LAZY
//            , cascade = CascadeType.ALL
    )
    @JoinColumn(name = "fk_app_id")
    private AppEntity userApp;

    @ManyToMany(fetch = FetchType.EAGER
//            , cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "client_role",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "client_authority",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<AuthorityEntity> authorities;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "client_scope", joinColumns = @JoinColumn(name = "fk_client_id"))
    @Column(name = "scope")
    private Set<ScopeEnum> scope;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "client_auth_method", joinColumns = @JoinColumn(name = "fk_client_id"))
    @Column(name = "auth_method")
    private Set<AuthMethodEnum> authMethod;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "client_auth_grant_type", joinColumns = @JoinColumn(name = "fk_client_id"))
    @Column(name = "auth_grant_type")
    private Set<AuthGrantTypeEnum> authGrantType;

    @Column(name = "redirect_uri", nullable = false)
    private String redirectUri;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
