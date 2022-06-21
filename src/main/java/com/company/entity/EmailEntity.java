package com.company.entity;

import com.company.enums.EmailType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "email_history")
@Getter
@Setter
public class EmailEntity extends BaseEntity {
    @Column
    private String toEmail;
    @Column
    @Enumerated(EnumType.STRING)
    private EmailType type;
}
