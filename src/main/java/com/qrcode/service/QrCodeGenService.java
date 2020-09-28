package com.qrcode.service;

import com.google.zxing.WriterException;
import org.springframework.stereotype.Service;

import java.io.IOException;
public interface QrCodeGenService {
    public byte[] generateQrCode(String inputText,int width, int height) throws WriterException, IOException;
    public byte[] generateColorQrCode(String inputText,int width, int height,String logoUrl);
}
