package com.hoang.indentity_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column
    String username;
    @Column
    String password;
    @Column
    String firstName;
    @Column
    String lastName;
    @Column
    LocalDate birthDate;
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "codeRole", nullable = false) //khoa ngoai trong bang userEntity, coderole giong ten khoa chinh trong userEntity
//    @JsonManagedReference
//    Roles role;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "codeRole"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    Set<Roles> roles;
}
