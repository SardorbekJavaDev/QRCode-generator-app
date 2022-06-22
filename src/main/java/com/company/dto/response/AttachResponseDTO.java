package com.company.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachResponseDTO {

    private String id;
    private String origenName;
    private String url;
    private long size;
    private LocalDateTime createdDate;

}
