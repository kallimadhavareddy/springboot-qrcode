package com.qrcode.service;

import com.google.zxing.WriterException;
import org.springframework.stereotype.Service;

import java.io.IOException;
public interface QrCodeGenService {
     byte[] generateQrCode(String inputText,int width, int height);
     //void generatePdf(byte[] qrCodeByte);
}
