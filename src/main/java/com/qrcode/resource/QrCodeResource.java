package com.qrcode.resource;

import com.google.zxing.WriterException;
import com.qrcode.service.QrCodeGenService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class QrCodeResource {
    @Autowired
    QrCodeGenService qrCodeGenService;

    @GetMapping("qr/plain")
    public  Resource generatePlainQrCode(){
        try {
             return new ByteArrayResource(qrCodeGenService.generateQrCode("http://madhavareddy.net", 300, 300), "test");
        }catch(WriterException we){
            we.printStackTrace();
        }catch(IOException ie){
            ie.printStackTrace();
        }
        return null;
    }

    @GetMapping("qr/color")
    public byte[] generateColorQrCode(){
        try {
            //return qrCodeGenService.generateColorQrCode("http://madhavareddy.net", 300, 300,"https://facebookbrand.com/wp-content/uploads/2019/04/f_logo_RGB-Hex-Blue_512.png?w=112&h=112");
            return qrCodeGenService.generateColorQrCode("http://madhavareddy.net", 300, 300,"http://madhavareddy.net/wp-content/uploads/2020/06/Madhava-Reddy-Kalli.png?w=112&h=112");
        }catch(Exception ie){
            ie.printStackTrace();
        }
        return new byte[0];
    }
}
