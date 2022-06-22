package com.company.controller;

import com.company.dto.request.QRCodeRequest;
import com.company.dto.response.QRCodeResponse;
import com.company.service.QRCService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/qrcode")
@Api("QRCode")
public class QRCodeController {
    private final QRCService qrcService;

    @ApiOperation(value = "Create Category", notes = "Method used for Create Category")
    //    @PreAuthorize("hasRole('USER')")
    @PostMapping("")
    public ResponseEntity<QRCodeResponse> create(@RequestBody @Valid QRCodeRequest dto) {
        log.info("Category Create {}{}", dto, QRCodeController.class);
        return ResponseEntity.ok(qrcService.create(dto));
    }

    // get QrCodeList active list
    // get QrCodeList paused list
    // get QrCode full info
    // get QrCode short info

}
