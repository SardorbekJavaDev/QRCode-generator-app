package com.company.QRCode;

import com.company.dto.request.EyeColorConfig;
import com.company.dto.request.QRCodeColorConfig;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.QRCode;

import java.awt.*;
import java.awt.image.BufferedImage;

public class QRCodeGenerator {


    public static BufferedImage renderQRImage(QRCode code, QRCodeColorConfig colorConfig, int width, int height, int quietZone) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setBackground(HexToColor(colorConfig.getBgColor()));
        graphics.clearRect(0, 0, width, height);
        graphics.setColor(HexToColor(colorConfig.getPointColor()));

        ByteMatrix input = code.getMatrix();
        if (input == null) {
            throw new IllegalStateException();
        }
        int inputWidth = input.getWidth();
        int inputHeight = input.getHeight();
        int qrWidth = inputWidth + (quietZone * 2);
        int qrHeight = inputHeight + (quietZone * 2);
        int outputWidth = Math.max(width, qrWidth);
        int outputHeight = Math.max(height, qrHeight);

        int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);
        int leftPadding = (outputWidth - (inputWidth * multiple)) / 2;
        int topPadding = (outputHeight - (inputHeight * multiple)) / 2;
        final int FINDER_PATTERN_SIZE = 7;
        final float CIRCLE_SCALE_DOWN_FACTOR = 21f / 30f;
        int circleSize = (int) (multiple * CIRCLE_SCALE_DOWN_FACTOR);

        for (int inputY = 0, outputY = topPadding; inputY < inputHeight; inputY++, outputY += multiple) {
            for (int inputX = 0, outputX = leftPadding; inputX < inputWidth; inputX++, outputX += multiple) {
                if (input.get(inputX, inputY) == 1) {
                    if (!(inputX <= FINDER_PATTERN_SIZE && inputY <= FINDER_PATTERN_SIZE ||
                            inputX >= inputWidth - FINDER_PATTERN_SIZE && inputY <= FINDER_PATTERN_SIZE ||
                            inputX <= FINDER_PATTERN_SIZE && inputY >= inputHeight - FINDER_PATTERN_SIZE)) {
                        graphics.fillOval(outputX, outputY, circleSize, circleSize);
                    }
                }
            }
        }

        int circleDiameter = multiple * FINDER_PATTERN_SIZE;
        drawFinderPatternCircleStyle(graphics,colorConfig.getEye1(), leftPadding, topPadding, circleDiameter);
        drawFinderPatternCircleStyle(graphics, colorConfig.getEye2(), leftPadding + (inputWidth - FINDER_PATTERN_SIZE) * multiple, topPadding, circleDiameter);
        drawFinderPatternCircleStyle(graphics, colorConfig.getEye3(), leftPadding, topPadding + (inputHeight - FINDER_PATTERN_SIZE) * multiple, circleDiameter);

        return image;
    }

    public static void drawFinderPatternCircleStyle(Graphics2D graphics, EyeColorConfig colorConfig, int x, int y, int circleDiameter) {
        final int WHITE_CIRCLE_DIAMETER = circleDiameter * 5 / 7;
        final int WHITE_CIRCLE_OFFSET = circleDiameter / 7;
        final int MIDDLE_DOT_DIAMETER = circleDiameter * 3 / 7;
        final int MIDDLE_DOT_OFFSET = circleDiameter * 2 / 7;

        graphics.setColor(HexToColor(colorConfig.getOuter()));
        graphics.fillOval(x, y, circleDiameter, circleDiameter);
        graphics.setColor(HexToColor(colorConfig.getMedium()));
        graphics.fillOval(x + WHITE_CIRCLE_OFFSET, y + WHITE_CIRCLE_OFFSET, WHITE_CIRCLE_DIAMETER, WHITE_CIRCLE_DIAMETER);
        graphics.setColor(HexToColor(colorConfig.getSmall()));
        graphics.fillOval(x + MIDDLE_DOT_OFFSET, y + MIDDLE_DOT_OFFSET, MIDDLE_DOT_DIAMETER, MIDDLE_DOT_DIAMETER);
    }


    public static Color HexToColor(String hex) {
        hex = hex.replace("#", "");
        return switch (hex.length()) {
            case 6 -> new Color(
                    Integer.valueOf(hex.substring(0, 2), 16),
                    Integer.valueOf(hex.substring(2, 4), 16),
                    Integer.valueOf(hex.substring(4, 6), 16));
            case 8 -> new Color(
                    Integer.valueOf(hex.substring(0, 2), 16),
                    Integer.valueOf(hex.substring(2, 4), 16),
                    Integer.valueOf(hex.substring(4, 6), 16),
                    Integer.valueOf(hex.substring(6, 8), 16));
            default -> null;
        };
    }
}
