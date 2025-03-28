package com.hoang.indentity_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Permissions {
    @Id
    private String codePermission;
    private String namePermission;
    private String description;

    @ManyToMany(mappedBy = "permissions")
    Set<Roles> roles;

}
