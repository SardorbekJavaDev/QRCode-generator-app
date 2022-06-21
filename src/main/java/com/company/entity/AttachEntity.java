package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "attach")
public class AttachEntity extends BaseEntity {

    @Column
    private String extension;

    @Column
    private String path;

    @Column(name = "origin_name")
    private String originName;

    @Column
    private Long size;

}
