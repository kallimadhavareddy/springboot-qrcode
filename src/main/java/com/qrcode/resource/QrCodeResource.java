package com.qrcode.resource;

import com.qrcode.service.QrCodeGenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class QrCodeResource {
    @Autowired
    QrCodeGenService qrCodeGenService;

    @GetMapping("qrcode")
    public  Resource generatePlainQrCode(){
             return new ByteArrayResource(qrCodeGenService.generateQrCode("http://madhavareddy.net",
                     300, 300));

    }
}
