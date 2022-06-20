package com.company.entity;

import com.company.enums.UserRole;
import com.company.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity{

    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String phone;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String companyName;
    @Column
    private String street;
    @Column
    private String zipCode;
    @Column
    private String country;
    @Column
    private String website;
    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column
    @Enumerated(EnumType.STRING)
    private UserStatus status;

}
