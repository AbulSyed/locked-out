package com.syed.identityservice.utility;

import com.syed.identityservice.data.entity.*;
import com.syed.identityservice.domain.enums.ProcessEnum;
import com.syed.identityservice.domain.enums.RequestStatusEnum;
import com.syed.identityservice.domain.enums.RequestTypeEnum;
import com.syed.identityservice.domain.model.AuthorityModel;
import com.syed.identityservice.domain.model.ClientModel;
import com.syed.identityservice.domain.model.RoleModel;
import com.syed.identityservice.domain.model.UserModel;
import com.syed.identityservice.domain.model.request.CreateAppRequest;
import com.syed.identityservice.domain.model.request.CreateUserRequest;
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

    public static AppEntity mapAppModelToEntity(CreateAppRequest request) {
        return AppEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static CreateAppResponse mapAppEntityToCreateAppResponse(AppEntity entity) {
        return CreateAppResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static GetAppResponse mapAppEntityToGetAppResponse(AppEntity entity) {
        return GetAppResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static GetAppDetailsResponse mapAppEntityToGetAppDetailsResponse(AppEntity entity) {
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

        return GetAppDetailsResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .users(userSet)
                .clients(clientSet)
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static List<GetAppResponse> mapAppEntityListToGetAppResponseList(List<AppEntity> entityList) {
        List<GetAppResponse> appResponseList = new ArrayList<>();

        for (AppEntity entity : entityList) {
            GetAppResponse appResponse = GetAppResponse.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .description(entity.getDescription())
                    .createdAt(entity.getCreatedAt())
                    .build();

            appResponseList.add(appResponse);
        }

        return appResponseList;
    }

    public static UpdateAppResponse mapAppEntityToUpdateAppResponse(AppEntity entity) {
        return UpdateAppResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static UserEntity mapUserModelToEntity(CreateUserRequest request) {
        return UserEntity.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static CreateUserResponse mapUserEntityToCreateAppResponse(UserEntity entity) {
        return CreateUserResponse.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static GetUserResponse mapUserEntitytoGetUserResponse(UserEntity entity) {
        return GetUserResponse.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static List<GetUserResponse> mapUserEntityListToGetUserResponseList(List<UserEntity> userEntityList) {
        List<GetUserResponse> userResponseList = new ArrayList<>();

        for (UserEntity userEntity : userEntityList) {
            GetUserResponse getUserResponse = GetUserResponse.builder()
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

    public static UpdateUserResponse mapUserEntitytoUpdateUserResponse(UserEntity entity) {
        return UpdateUserResponse.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
