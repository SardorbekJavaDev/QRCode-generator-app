package com.company.service;

import com.company.QRCode.QRCodeGenerator;
import com.company.config.details.EntityDetails;
import com.company.dto.request.QRCodeRequest;
import com.company.dto.response.QRCodeResponse;
import com.company.entity.QRCodeEntity;
import com.company.enums.QRCodeStatus;
import com.company.exception.WrongFileFormatException;
import com.company.repository.QRCodeRepository;
import com.company.util.PathUploadAttachUtil;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QRCService {
    private final EntityDetails entityDetails;
    private final QRCodeRepository qrCodeRepository;
    private final PathUploadAttachUtil uploadAttachUtil;

    public QRCodeResponse create(QRCodeRequest dto) {
        String extension = dto.getExtension();

        if (!extension.equals("jpg") && !extension.equals("png")) {
            throw new WrongFileFormatException("File format must be png or jpg !");
        }

        final Map<EncodeHintType, Object> encodingHints = new HashMap<>();
        encodingHints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        QRCode code = null;

        QRCodeEntity entity = new QRCodeEntity();
        entity.setData(dto.getData());
        entity.setUserId(entityDetails.getUserEntity().getId());
        entity.setAttachId(dto.getLogoAttachId());
        entity.setPath(uploadAttachUtil.getUploadFolder());
        entity.setExtension(dto.getExtension());
        entity.setStatus(QRCodeStatus.ACTIVE);
        entity.setVisible(true);
        qrCodeRepository.save(entity);

        try {
            code = Encoder.encode(dto.getData(), ErrorCorrectionLevel.H, encodingHints);
            String path = uploadAttachUtil.getUploadFolder() + uploadAttachUtil.getYMDString() + "/" + entity.getId() + "." + extension;

            BufferedImage image = QRCodeGenerator.renderQRImage(code, dto.getWidth(), dto.getHeight(), 4);

            ImageIO.write(image, extension, new File(path));

        } catch (IOException | WriterException e) {
            throw new RuntimeException(e);
        }

        return toQRCodeResponse(entity);
    }

    private QRCodeResponse toQRCodeResponse(QRCodeEntity entity) {
        QRCodeResponse dto = new QRCodeResponse();
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());
        dto.setSize(entity.getSize());
        dto.setStatus(entity.getStatus().toString());
        dto.setFile(entity.getExtension());
        dto.setUrl(uploadAttachUtil.getDownloadURL(entity.getId()));
        return dto;
    }
}
