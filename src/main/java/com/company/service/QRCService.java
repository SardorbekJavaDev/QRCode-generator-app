package com.company.service;

import com.company.QRCode.QRCodeGenerator;
import com.company.dto.request.QRCodeRequest;
import com.company.dto.response.AttachResponseDTO;
import com.company.repository.QRCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QRCService {
    private QRCodeRepository qrCodeRepository;

    public AttachResponseDTO create(QRCodeRequest dto) {

        QRCodeGenerator.createNewQRCode(dto);
        return null;
    }
}
