package com.company.service;

import com.company.dto.response.AttachResponseDTO;
import com.company.entity.AttachEntity;
import com.company.exception.AppBadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.AttachRepository;
import com.company.util.PathUploadAttachUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
@RequiredArgsConstructor
public class AttachService {
    private final AttachRepository attachRepository;
    private final PathUploadAttachUtil uploadAttachUtil;


    public AttachResponseDTO upload(MultipartFile file) {
        String pathFolder = uploadAttachUtil.getYMDString();
        File folder = new File(uploadAttachUtil.getUploadFolder() + pathFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String extension = getExtension(file.getOriginalFilename());
        AttachEntity entity = saveAttach(pathFolder, extension, file);
        AttachResponseDTO dto = toDTO(entity);

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadAttachUtil.getUploadFolder() + pathFolder + "/" + entity.getId() + "." + extension);
            Files.write(path, bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dto;
    }

    private AttachEntity saveAttach(String pathFolder, String extension, MultipartFile file) {
        AttachEntity entity = new AttachEntity();
        entity.setPath(pathFolder);
        entity.setOriginName(file.getOriginalFilename());
        entity.setExtension(extension);
        entity.setSize(file.getSize());
        attachRepository.save(entity);
        return entity;
    }

    public byte[] openGeneral(String id) {
        byte[] data;
        try {
            AttachEntity entity = getById(id);
            String path = entity.getPath() + "/" + id + "." + entity.getExtension();
            Path file = Paths.get(uploadAttachUtil.getUploadFolder() + path);
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public ResponseEntity<Resource> download(String id) {
        try {
            AttachEntity entity = getById(id);
            String path = entity.getPath() + "/" + id + "." + entity.getExtension();
            Path file = Paths.get(uploadAttachUtil.getUploadFolder() + path);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" + entity.getOriginName() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean delete(String key) {
        AttachEntity entity = getById(key);

        File file = new File(uploadAttachUtil.getUploadFolder() + entity.getPath() +
                "/" + entity.getId() + "." + entity.getExtension());

        if (file.delete()) {
            attachRepository.deleteById(key);
            return true;
        } else throw new AppBadRequestException("Could not read the file!");
    }


    private AttachEntity getById(String id) {
        return attachRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Attach not found");
        });
    }

    private String getExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    private AttachResponseDTO toDTO(AttachEntity entity) {
        AttachResponseDTO dto = new AttachResponseDTO();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setOrigenName(entity.getOriginName());
        dto.setUrl(uploadAttachUtil.getDownloadURL(entity.getId()));
        dto.setSize(entity.getSize());
        return dto;
    }

    public String toOpenURL(String id) {
        return uploadAttachUtil.getOpenURL(id);
    }



}
