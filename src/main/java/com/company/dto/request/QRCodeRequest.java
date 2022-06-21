package com.company.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QRCodeRequest {

    private String data; // https://www.qrcode-monkey.com
    private QRCodeColorConfig colorConfig;
    private int width;
    private int height;
    private boolean save;
    private String extension;
    private String logoAttachId;

}
