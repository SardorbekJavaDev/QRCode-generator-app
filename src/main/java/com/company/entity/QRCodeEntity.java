package com.company.entity;

import com.company.enums.QRCodeStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Entity
@Table(name = "QR_code")
public class QRCodeEntity extends BaseEntity {

    @Column
    private String data;
    @Column
    private Integer scans;
    @Column
    private QRCodeStatus status;
    @Column
    private String extension;
    @Column
    private String path;

    @Column
    private Long size;

    // user
    @Column(name = "user_id")
    private String userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",updatable = false,insertable = false)
    private UserEntity user;

    // doc, mp3, mp4, ... attach
    @Column(name = "attach_id")
    private String attachId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id",updatable = false,insertable = false)
    private AttachEntity attach;
}
