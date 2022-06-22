package com.company.dto.request;

import lombok.Data;

@Data
public class QRCodeColorConfig {
    private String pointColor;         // "#5C8B29",
    private String bgColor;           // "#FFFFFF",

    private EyeColorConfig eye1;         // "#3F6B2B", "#3F6B2B", "#3F6B2B"
    private EyeColorConfig eye2;         // "#3F6B2B", "#3F6B2B", "#3F6B2B"
    private EyeColorConfig eye3;         // "#3F6B2B", "#3F6B2B", "#3F6B2B"
}
