package com.syed.identityservice.utility;

import com.syed.identityservice.data.entity.*;
import com.syed.identityservice.domain.enums.ProcessEnum;
import com.syed.identityservice.domain.enums.RequestStatusEnum;
import com.syed.identityservice.domain.enums.RequestTypeEnum;
import com.syed.identityservice.domain.model.AuthorityModel;
import com.syed.identityservice.domain.model.ClientModel;
import com.syed.identityservice.domain.model.RoleModel;
import com.syed.identityservice.domain.model.UserModel;
import com.syed.identityservice.domain.model.request.AppRequest;
import com.syed.identityservice.domain.model.request.ClientRequest;
import com.syed.identityservice.domain.model.request.UserRequest;
import com.syed.identityservice.domain.model.request.RoleRequest;
import com.syed.identityservice.domain.model.response.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapperUtil {

    public static AuditRequestEntity createAuditRequestEntity(String correlationId,
                                                                ProcessEnum process,
                                                                RequestTypeEnum requestType,
                                                                RequestStatusEnum requestStatus,
                                                                String log) {
        return AuditRequestEntity.builder()
                .correlationId(correlationId)
                .process(process)
                .requestType(requestType)
                .requestStatus(requestStatus)
                .log(log)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static AppEntity mapAppModelToEntity(AppRequest request) {
        return AppEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static AppResponse mapAppEntityToAppResponse(AppEntity entity) {
        return AppResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static AppV2Response mapAppEntityToAppV2Response(AppEntity entity) {
        Set<UserModel> userSet = new HashSet<>();

        for (UserEntity userEntity : entity.getUsers()) {
            UserModel user = UserModel.builder()
                    .id(userEntity.getId())
                    .username(userEntity.getUsername())
                    .password(userEntity.getPassword())
                    .email(userEntity.getEmail())
                    .phoneNumber(userEntity.getPhoneNumber())
                    .createdAt(userEntity.getCreatedAt())
                    .build();

            Set<RoleModel> roles = new HashSet<>();
            for (RoleEntity roleEntity : userEntity.getRoles()) {
                RoleModel roleModel = RoleModel.builder()
                        .id(roleEntity.getId())
                        .name(roleEntity.getName())
                        .build();

                roles.add(roleModel);
            }
            user.setRoles(roles);

            Set<AuthorityModel> authorities = new HashSet<>();
            for (AuthorityEntity authorityEntity : userEntity.getAuthorities()) {
                AuthorityModel authorityModel = AuthorityModel.builder()
                        .id(authorityEntity.getId())
                        .name(authorityEntity.getName())
                        .build();

                authorities.add(authorityModel);
            }
            user.setAuthorities(authorities);

            userSet.add(user);
        }

        Set<ClientModel> clientSet = new HashSet<>();
        for (ClientEntity clientEntity : entity.getClients()) {
            ClientModel client = ClientModel.builder()
                    .id(clientEntity.getId())
                    .clientId(clientEntity.getClientId())
                    .secret(clientEntity.getSecret())
                    .scopes(clientEntity.getScope())
                    .authMethods(clientEntity.getAuthMethod())
                    .authGrantTypes(clientEntity.getAuthGrantType())
                    .redirectUri(clientEntity.getRedirectUri())
                    .createdAt(clientEntity.getCreatedAt())
                    .build();

            Set<RoleModel> roles = new HashSet<>();
            for (RoleEntity roleEntity : clientEntity.getRoles()) {
                RoleModel roleModel = RoleModel.builder()
                        .id(roleEntity.getId())
                        .name(roleEntity.getName())
                        .build();

                roles.add(roleModel);
            }
            client.setRoles(roles);

            Set<AuthorityModel> authorities = new HashSet<>();
            for (AuthorityEntity authorityEntity : clientEntity.getAuthorities()) {
                AuthorityModel authorityModel = AuthorityModel.builder()
                        .id(authorityEntity.getId())
                        .name(authorityEntity.getName())
                        .build();

                authorities.add(authorityModel);
            }
            client.setAuthorities(authorities);

            clientSet.add(client);
        }

        return AppV2Response.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .users(userSet)
                .clients(clientSet)
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static List<AppResponse> mapAppEntityListToAppListResponse(List<AppEntity> entityList) {
        List<AppResponse> appResponseList = new ArrayList<>();

        for (AppEntity entity : entityList) {
            AppResponse appResponse = AppResponse.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .description(entity.getDescription())
                    .createdAt(entity.getCreatedAt())
                    .build();

            appResponseList.add(appResponse);
        }

        return appResponseList;
    }

    public static UserEntity mapUserModelToEntity(UserRequest request) {
        return UserEntity.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static UserResponse mapUserEntityToUserResponse(UserEntity entity) {
        return UserResponse.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static UserV2Response mapUserEntityToUserV2Response(UserEntity entity) {
        UserV2Response user = UserV2Response.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .createdAt(entity.getCreatedAt())
                .build();

        Set<RoleModel> roles = new HashSet<>();
        for (RoleEntity roleEntity : entity.getRoles()) {
            RoleModel roleModel = RoleModel.builder()
                    .id(roleEntity.getId())
                    .name(roleEntity.getName())
                    .build();

            roles.add(roleModel);
        }
        user.setRoles(roles);

        Set<AuthorityModel> authorities = new HashSet<>();
        for (AuthorityEntity authorityEntity : entity.getAuthorities()) {
            AuthorityModel authorityModel = AuthorityModel.builder()
                    .id(authorityEntity.getId())
                    .name(authorityEntity.getName())
                    .build();

            authorities.add(authorityModel);
        }
        user.setAuthorities(authorities);

        return user;
    }

    public static List<UserV2Response> mapUserEntityListToUserV2ResponseList(List<UserEntity> userEntityList) {
        List<UserV2Response> userResponseList = new ArrayList<>();

        for (UserEntity userEntity : userEntityList) {
            UserV2Response getUserResponse = UserV2Response.builder()
                    .id(userEntity.getId())
                    .username(userEntity.getUsername())
                    .password(userEntity.getPassword())
                    .email(userEntity.getEmail())
                    .phoneNumber(userEntity.getPhoneNumber())
                    .createdAt(userEntity.getCreatedAt())
                    .build();

            userResponseList.add(getUserResponse);
        }

        return userResponseList;
    }

    public static ClientEntity mapClientModelToEntity(ClientRequest request) {
        return ClientEntity.builder()
                .clientId(request.getClientId())
                .secret(request.getClientSecret())
                .authMethod(request.getAuthMethod())
                .authGrantType(request.getAuthGrantType())
                .redirectUri(request.getRedirectUri())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static ClientResponse mapClientEntityToClientResponse(ClientEntity entity) {
        return ClientResponse.builder()
                .id(entity.getId())
                .clientId(entity.getClientId())
                .clientSecret(entity.getSecret())
                .authMethod(entity.getAuthMethod())
                .authGrantType(entity.getAuthGrantType())
                .redirectUri(entity.getRedirectUri())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static List<ClientResponse> mapClientEntityListToGetClientListResponse(List<ClientEntity> entityList) {
        List<ClientResponse> clientResponseList = new ArrayList<>();

        for (ClientEntity entity : entityList) {
            ClientResponse clientResponse = ClientResponse.builder()
                    .id(entity.getId())
                    .clientId(entity.getClientId())
                    .clientSecret(entity.getSecret())
                    .authMethod(entity.getAuthMethod())
                    .authGrantType(entity.getAuthGrantType())
                    .redirectUri(entity.getRedirectUri())
                    .createdAt(entity.getCreatedAt())
                    .build();

            clientResponseList.add(clientResponse);
        }

        return clientResponseList;
    }

    public static RoleEntity mapRoleModelToEntity(RoleRequest request) {
        return RoleEntity.builder()
                .name(request.getName())
                .build();
    }

    public static RoleResponse mapRoleEntityToResponse(RoleEntity entity) {
        return RoleResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static List<String> mapRoleEntityListToStringList(List<RoleEntity> entityList) {
        List<String> roles = new ArrayList<>();

        for (RoleEntity roleEntity : entityList) {
            roles.add(roleEntity.getName());
        }
        return roles;
    }
}
