package com.syed.identityservice.utility;

import com.syed.identityservice.data.entity.*;
import com.syed.identityservice.domain.enums.ProcessEnum;
import com.syed.identityservice.domain.enums.RequestStatusEnum;
import com.syed.identityservice.domain.enums.RequestTypeEnum;
import com.syed.identityservice.domain.model.AuthorityModel;
import com.syed.identityservice.domain.model.RoleModel;
import com.syed.identityservice.domain.model.UserModel;
import com.syed.identityservice.domain.model.request.CreateAppRequest;
import com.syed.identityservice.domain.model.response.CreateAppResponse;
import com.syed.identityservice.domain.model.response.GetAppDetailsResponse;
import com.syed.identityservice.domain.model.response.GetAppResponse;

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

        // TODO add logic to add client set to GetAppDetailsResponse

        return GetAppDetailsResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .users(userSet)
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
}
