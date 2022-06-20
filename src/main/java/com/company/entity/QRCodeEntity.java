package com.company.entity;

import com.company.enums.QRCodeStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Setter
@Getter
@Entity
@Table(name = "QR_code")
public class QRCodeEntity extends BaseEntity {
    @Column
    private String data;
    private Integer scans;
    private QRCodeStatus status;

    // doc, mp3, mp4, ... attach
    private String attachId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id",updatable = false,insertable = false)
    private AttachEntity attach;
    // QRC attach
    private String QRCAttachId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QRC_attach_id",updatable = false,insertable = false)
    private AttachEntity QRCAttach;

}
