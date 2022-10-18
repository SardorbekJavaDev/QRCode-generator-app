package com.company.controller;

import com.company.dto.request.QRCodeRequest;
import com.company.dto.response.QRCodeResponse;
import com.company.service.QRCService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/qrcode")
@Api("QRCode")
public class QRCodeController {
    private final QRCService qrcService;

    @ApiOperation(value = "Create QRCode", notes = "Method used for Create QRCode")
    @PostMapping("/")
    public ResponseEntity<QRCodeResponse> create(@RequestBody @Valid QRCodeRequest dto) {
        log.info("QRCode Create {}{}", dto, QRCodeController.class);
        return ResponseEntity.ok(qrcService.create(dto));
    }

    @ApiOperation(value = "Open QRCode", notes = "Method used for Open QRCode by id")
    @GetMapping(value = "/open-general/{id}", produces = MediaType.ALL_VALUE)
    public byte[] open_general(@PathVariable("id") String key) {
        log.info("QRCode open_general {}{}", key, QRCodeController.class);
        return qrcService.openGeneral(key);
    }

    @ApiOperation(value = "delete by key", notes = "Method used for delete QRCode by key")
    @DeleteMapping("/delete/{key}")
    public ResponseEntity<Boolean> delete(@PathVariable("key") String key) {
        log.warn("QRCode delete {}{}", key, QRCodeController.class);
        return ResponseEntity.ok(qrcService.delete(key));
    }

    @ApiOperation(value = "download by key", notes = "Method used for download QRCode by key")
    @GetMapping("/download/{key}")
    public ResponseEntity<Resource> download(@PathVariable("key") String key) {
        log.info("QRCode download {}{}", key, QRCodeController.class);
        return qrcService.download(key);
    }

    // get QrCodeList active list
    // get QrCodeList paused list

    // get QrCode full info

    // get QrCode short info

}
