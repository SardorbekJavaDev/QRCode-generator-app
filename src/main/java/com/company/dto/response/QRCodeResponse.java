package com.company.dto.response;

import com.company.dto.request.QRCodeColorConfig;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QRCodeResponse {

    private String id;
    private String file;
    private String url;
    private long size;
    private String status;
    private LocalDateTime createdDate;

}
