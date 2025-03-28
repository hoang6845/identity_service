package com.hoang.indentity_service.service;

import com.hoang.indentity_service.dto.request.PermissionsRequest;
import com.hoang.indentity_service.dto.response.PermissionsResponse;
import com.hoang.indentity_service.entity.Permissions;
import com.hoang.indentity_service.mapper.IPermissionsMapper;
import com.hoang.indentity_service.repository.PermissionsRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PermissionsService {

    private final PermissionsRepository permissionsRepository;

    private final IPermissionsMapper permissionsMapper;

    public PermissionsResponse createPermissions(PermissionsRequest request) {
        Permissions permissions = permissionsMapper.ToPermissions(request);
        permissions = permissionsRepository.save(permissions);
        return permissionsMapper.ToPermissionsResponse(permissions);
    }

    public List<PermissionsResponse> getAll(){
        List<Permissions> list =  permissionsRepository.findAll();
        return list.stream().map(permissionsMapper::ToPermissionsResponse).toList();
    }

    public void deletePermissions(String codePermission) {
        permissionsRepository.deleteById(codePermission);
    }
}
