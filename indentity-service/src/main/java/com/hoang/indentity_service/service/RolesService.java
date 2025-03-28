package com.hoang.indentity_service.service;

import com.hoang.indentity_service.dto.request.RolesRequest;
import com.hoang.indentity_service.dto.response.RolesResponse;
import com.hoang.indentity_service.entity.Permissions;
import com.hoang.indentity_service.entity.Roles;
import com.hoang.indentity_service.mapper.IPermissionsMapper;
import com.hoang.indentity_service.mapper.IRolesMapper;
import com.hoang.indentity_service.repository.PermissionsRepository;
import com.hoang.indentity_service.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RolesService {
    private final RolesRepository rolesRepository;
    private final PermissionsRepository permissionsRepository;
    private final IRolesMapper rolesMapper;

    public RolesResponse create(RolesRequest rolesRequest) {
        Roles roles = rolesMapper.toRoles(rolesRequest);
        List<Permissions> listPer = new ArrayList<>(permissionsRepository.findAllById(rolesRequest.getCodePermissions()));
        Set<Permissions> setPer = new HashSet<>(listPer);
        roles.setPermissions(setPer);
        System.out.println(roles.getCodeRole());
        roles = rolesRepository.save(roles);
        return rolesMapper.toRolesResponse(roles);
    }

    public List<RolesResponse> findAll() {
        return rolesRepository.findAll()
                .stream()
                .map(rolesMapper::toRolesResponse)
                .toList();
    }

    public void delete(String codeRoles) {
        rolesRepository.deleteById(codeRoles);
    }

}
