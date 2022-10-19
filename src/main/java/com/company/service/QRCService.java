package com.company.service;

import com.company.QRCode.QRCodeGenerator;
import com.company.config.details.EntityDetails;
import com.company.dto.request.QRCodeRequest;
import com.company.dto.response.QRCodeResponse;
import com.company.entity.QRCodeEntity;
import com.company.enums.QRCodeStatus;
import com.company.exception.AppBadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.exception.WrongFileFormatException;
import com.company.repository.QRCodeRepository;
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
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QRCService {
    @Value("${attach.upload.folder}")
    private String uploadFolder;
    private final EntityDetails entityDetails;
    private final QRCodeRepository qrCodeRepository;
    private final AttachService attachService;

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
        entity.setAttachId(dto.getAttachId());
        entity.setPath(uploadFolder);
        entity.setExtension(dto.getExtension());
        entity.setStatus(QRCodeStatus.ACTIVE);
        entity.setVisible(true);
        qrCodeRepository.save(entity);

        try {
            code = Encoder.encode(dto.getData(), ErrorCorrectionLevel.H, encodingHints);
            String path = uploadFolder + attachService.getYMDString() + "/" + entity.getId() + "." + extension;
            File file = new File(path);
            file.mkdirs();
            BufferedImage image = QRCodeGenerator.renderQRImage(code, dto.getColorConfig(), dto.getWidth(), dto.getHeight(), 4);
            ImageIO.write(image, extension, file);
//            entity.setSize(file.getUsableSpace());
            entity.setSize(file.length());
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
        dto.setUrl(attachService.getDownloadURL(entity.getId()));
        return dto;
    }


    public byte[] openGeneral(String id) {
        byte[] data;
        try {
            QRCodeEntity entity = getById(id);
            String path = entity.getPath() + doTimeFormat(entity.getCreatedDate()) + "/" + id + "." + entity.getExtension();
            Path file = Paths.get(path);
            System.out.println(path);
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public ResponseEntity<Resource> download(String id) {
        try {
            QRCodeEntity entity = getById(id);
            String path = entity.getPath() + doTimeFormat(entity.getCreatedDate()) + "/" + id + "." + entity.getExtension();
            Path file = Paths.get(path);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" + entity.getId() + "." + entity.getExtension() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean delete(String key) {
        QRCodeEntity entity = getById(key);
        File file = new File(entity.getPath() + doTimeFormat(entity.getCreatedDate()) +
                "/" + entity.getId() + "." + entity.getExtension());

        if (file.delete()) {
            qrCodeRepository.updateVisible(false, key);
            return true;
        } else throw new ItemNotFoundException("Not found !");
    }

    private String doTimeFormat(LocalDateTime createdDate) {
        return createdDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

    private QRCodeEntity getById(String id) {
        return qrCodeRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("QRCode not found !");
        });
    }

}
