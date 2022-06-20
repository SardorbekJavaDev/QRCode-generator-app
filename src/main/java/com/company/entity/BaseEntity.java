package com.company.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false,nullable = false)
    private String id;

    @Column
    private Boolean visible;
    @Column
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column
    private LocalDateTime updatedDate;
    @Column
    private LocalDateTime deletedDate;
}
