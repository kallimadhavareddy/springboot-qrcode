package com.qrcode.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.qrcode.service.QrCodeGenService;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class QrCodeGenServiceImpl implements QrCodeGenService {
    @Override
    public byte[] generateQrCode(String inputText, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(inputText, BarcodeFormat.QR_CODE, width, height);
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, getMatrixConfig());
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "PNG", os);
            generateFile(os,"plain");
            return os.toByteArray();
        }catch (WriterException|IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public byte[] generateColorQrCode(String inputText, int width, int height,String logoUrl){
        Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        QRCodeWriter writer = new QRCodeWriter();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            BitMatrix bitMatrix = writer.encode(inputText, BarcodeFormat.QR_CODE, width, height, hints);
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, getMatrixConfig());
            BufferedImage overly = getOverly(logoUrl);
            int deltaHeight = qrImage.getHeight() - overly.getHeight();
            int deltaWidth = qrImage.getWidth() - overly.getWidth();
            BufferedImage combined = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) combined.getGraphics();
            g.drawImage(qrImage, 0, 0, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            g.drawImage(overly, (int) Math.round(deltaWidth / 2), (int) Math.round(deltaHeight / 2), null);
            ImageIO.write(combined, "PNG", os);
            generateFile(os,"Color");
            return os.toByteArray();
        } catch (WriterException|IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    private BufferedImage getOverly(String LOGO) throws IOException {
        URL url = new URL(LOGO);
        return ImageIO.read(url);
    }
    private MatrixToImageConfig getMatrixConfig() {
        return new MatrixToImageConfig(QrCodeGenServiceImpl.Colors.WHITE.getArgb(), QrCodeGenServiceImpl.Colors.RED.getArgb());
    }
    private void generateFile(ByteArrayOutputStream os ,String type) throws IOException {
        String DIR="C:\\DEV\\QR-Code\\";
        Files.copy( new ByteArrayInputStream(os.toByteArray()), Paths.get(DIR + generateRandoTitle(new Random(), 9) +type+".png"), StandardCopyOption.REPLACE_EXISTING);
    }
    private String generateRandoTitle(Random random, int length) {
        return random.ints(39, 631)
                .filter(i -> (i < 7 || i > 15) && (i < 190 || i > 197))
                .mapToObj(i -> (char) i)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
    public enum Colors {
        BLUE(0xFF40BAD0),
        RED(0xFFE91C43),
        PURPLE(0xFF8A4F9E),
        ORANGE(0xFFF4B13D),
        WHITE(0xFFFFFFFF),
        BLACK(0xFF000000);
        private final int argb;
        Colors(final int argb){
            this.argb = argb;
        }
        public int getArgb(){
            return argb;
        }
    }
}

