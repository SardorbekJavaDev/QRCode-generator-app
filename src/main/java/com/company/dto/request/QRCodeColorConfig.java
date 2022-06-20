package com.company.dto.request;

import lombok.Data;

@Data
public class QRCodeColorConfig {
    private String body;              // "rounded-pointed",
    private String eye;               // "frame14",
    private String eyeBall;           // "ball16",
    private String[] erf1;            // []  "fh"/"fv"
    private String[] erf2;            // []  "fh"/"fv"
    private String[] erf3;            // []  "fh"/"fv"
    private String[] brf1;            // []  "fh"/"fv"
    private String[] brf2;            // []  "fh"/"fv"
    private String[] brf3;            // []  "fh"/"fv"
    private String bodyColor;         // "#5C8B29",
    private String bgColor;           // "#FFFFFF",
    private String eye1Color;         // "#3F6B2B",
    private String eye2Color;         // "#3F6B2B",
    private String eye3Color;         // "#3F6B2B",
    private String eyeBall1Color;     // "#60A541",
    private String eyeBall2Color;     // "#60A541",
    private String eyeBall3Color;     // "#60A541",
    private String gradientColor1;    // "#5C8B29",
    private String gradientColor2;    // "#25492F",
    private String gradientType;      // "radial",
    private String gradientOnEyes;    // false,
    private String logo;              // ""
}
