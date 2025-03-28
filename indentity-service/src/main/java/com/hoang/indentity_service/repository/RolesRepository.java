package com.hoang.indentity_service.repository;

import com.hoang.indentity_service.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, String> {
}
