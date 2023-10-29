package com.syed.identityservice.service.impl;

import com.syed.identityservice.data.entity.AuthorityEntity;
import com.syed.identityservice.data.repository.AuthorityRepository;
import com.syed.identityservice.domain.model.request.AuthorityRequest;
import com.syed.identityservice.domain.model.response.AuthorityResponse;
import com.syed.identityservice.exception.ErrorConstant;
import com.syed.identityservice.exception.custom.FieldAlreadyExistsException;
import com.syed.identityservice.service.AuthorityService;
import com.syed.identityservice.utility.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Override
    public AuthorityResponse createAuthority(AuthorityRequest request) {
        if (authorityRepository.existsByName(request.getName())) {
            throw new FieldAlreadyExistsException(ErrorConstant.FIELD_ALREADY_USED.formatMessage("Name"));
        }

        AuthorityEntity authority = authorityRepository.save(MapperUtil.mapAuthorityModelToEntity(request));

        return MapperUtil.mapAuthorityEntityToResponse(authority);
    }
}
