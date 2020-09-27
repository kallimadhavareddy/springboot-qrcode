package com.qrcode.resource;

import com.google.zxing.WriterException;
import com.qrcode.service.QrCodeGenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class QrCodeResource {
    @Autowired
    QrCodeGenService qrCodeGenService;

    @GetMapping("/test")
    public String generateQrCode(){
        try {
            qrCodeGenService.generateQrCode("http://madhavareddy.net", 300, 300);
            qrCodeGenService.generateColorQrCode();
        }catch(WriterException we){
            we.printStackTrace();
        }catch(IOException ie){
            ie.printStackTrace();
        }
        return "Generate Qr Code";
    }
}
