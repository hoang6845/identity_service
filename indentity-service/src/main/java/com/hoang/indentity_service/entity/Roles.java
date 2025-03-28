package com.hoang.indentity_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Roles {
    @Id
    String codeRole;
    @Column
    String nameRole;

//    @OneToMany(mappedBy = "role") //chỉ tới trưởng role trong userEntity -> userEntity se quan ly moi quan he nay
//    @JsonBackReference
//    Set<UserEntity> users;
    @ManyToMany(mappedBy = "roles")
    Set<UserEntity> users;


    @ManyToMany
    @JoinTable(
            name = "roles_permissions",
            joinColumns = @JoinColumn(name = "codePermission"),
            inverseJoinColumns = @JoinColumn(name = "codeRole")
    )
    Set<Permissions> permissions;

    public Roles(String codeRole, String nameRole) {
        this.codeRole=codeRole;
        this.nameRole=nameRole;
    }

    @Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $codeRole = this.getCodeRole();
        result = result * 59 + ($codeRole == null ? 43 : $codeRole.hashCode());
        Object $nameRole = this.getNameRole();
        result = result * 59 + ($nameRole == null ? 43 : $nameRole.hashCode());
        return result;
    }
}
