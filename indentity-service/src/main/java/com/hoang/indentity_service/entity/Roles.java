package com.hoang.indentity_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @OneToMany(mappedBy = "role") //chỉ tới trưởng role trong userEntity
    @JsonBackReference
    Set<UserEntity> users;

    public Roles(String codeRole, String nameRole) {
        this.codeRole=codeRole;
        this.nameRole=nameRole;
    }
}
