package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "attach")
public class AttachEntity extends BaseEntity {
    private String id;
    private String path;
    private String extension;
    private String origenName;
    private Long size;
}
