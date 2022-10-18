package com.company;

import com.company.QRCode.QRCodeGenerator;
import com.company.config.details.EntityDetails;
import com.company.dto.request.EyeColorConfig;
import com.company.dto.request.QRCodeColorConfig;
import com.company.dto.request.QRCodeRequest;
import com.company.dto.response.QRCodeResponse;
import com.company.entity.QRCodeEntity;
import com.company.enums.QRCodeStatus;
import com.company.exception.AppBadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.exception.WrongFileFormatException;
import com.company.repository.QRCodeRepository;
import com.company.service.AttachService;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main {
//    public static void main(String[] args) {
//
//
//        for (int i = 1; i < 10; i++) {
//            for (int j = 1; j < 10; j++) {
//                System.out.println(i + " * " + j + " = " + i * j);
//            }
//            System.out.println("\n");
//        }
//
//
//    }
    public static void main(String[] args) {
        QRCodeRequest dto = new QRCodeRequest();
        dto.setAttachId("");
        dto.setSave(true);
        dto.setData("Assalomu Alaykum");
        dto.setHeight(400);
        dto.setWidth(400);
        dto.setExtension("png");
        QRCodeColorConfig config = new QRCodeColorConfig();
        config.setPointColor("#ffffff");
        config.setBgColor("#696969");
        EyeColorConfig eyeColorConfig = new EyeColorConfig();
        eyeColorConfig.setMedium("#000000");
        eyeColorConfig.setOuter("#ffffff");
        eyeColorConfig.setSmall("#ffffff");
        config.setEye1(eyeColorConfig);
        config.setEye2(eyeColorConfig);
        config.setEye3(eyeColorConfig);

        dto.setColorConfig(config);
        create(dto);
    }


    public static void create(QRCodeRequest dto) {
        String extension = dto.getExtension();

        if (!extension.equals("jpg") && !extension.equals("png")) {
            throw new WrongFileFormatException("File format must be png or jpg !");
        }

        final Map<EncodeHintType, Object> encodingHints = new HashMap<>();
        encodingHints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        QRCode code = null;

//            QRCodeEntity entity = new QRCodeEntity();
//            entity.setData(dto.getData());
//            entity.setUserId(entityDetails.getUserEntity().getId());
//            entity.setAttachId(dto.getAttachId());
//            entity.setPath(uploadFolder);
//            entity.setExtension(dto.getExtension());
//            entity.setStatus(QRCodeStatus.ACTIVE);
//            entity.setVisible(true);
//            qrCodeRepository.save(entity);

        try {
            code = Encoder.encode(dto.getData(), ErrorCorrectionLevel.H, encodingHints);
            String path = "src/main/resources/uploads/2022/10/14/f2a348f3-0a6e-4a9c-ad5b-f55febdb48a8" + "." + extension;
            File file = new File(path);
            file.mkdirs();
//            if (!file.mkdirs()) {
//                throw new AppBadRequestException("The file could not be created !");
//            }
//            path += "f2a348f3-0a6e-4a9c-ad5b-f55febdb48a8" + "." + extension;

            BufferedImage image = QRCodeGenerator.renderQRImage(code, dto.getColorConfig(), dto.getWidth(), dto.getHeight(), 4);
            ImageIO.write(image, extension, file);

        } catch (IOException | WriterException e) {
            throw new RuntimeException(e);
        }

//            return toQRCodeResponse(entity);
    }


}
