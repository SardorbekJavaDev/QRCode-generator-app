package com.company.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class AttachResponseDTO {

    private String id;
    private String origenName;
    private String url;
    private long size;
    private LocalDateTime createdDate;

}
