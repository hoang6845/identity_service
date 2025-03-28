package com.hoang.indentity_service.repository;

import com.hoang.indentity_service.entity.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, String> {
}
