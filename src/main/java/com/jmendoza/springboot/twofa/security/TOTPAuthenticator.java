package com.jmendoza.springboot.twofa.security;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.commons.codec.binary.Base32;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Created by pablocaif on 16/09/15
 * Modified by jmendoza  on 18/09/19
 */
@Component
public class TOTPAuthenticator {

    public boolean verifyCode(String secret, int code, int variance)
            throws InvalidKeyException, NoSuchAlgorithmException {
        long timeIndex = System.currentTimeMillis() / 1000 / 30;
        byte[] secretBytes = new Base32().decode(secret);
        for (int i = -variance; i <= variance; i++) {
            long calculatedCode = getCode(secretBytes, timeIndex + i);
            if (calculatedCode == code) {
                return true;
            }
        }
        return false;
    }

    private long getCode(byte[] secret, long timeIndex)
            throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signKey = new SecretKeySpec(secret, "HmacSHA1");
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(timeIndex);
        byte[] timeBytes = buffer.array();
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(timeBytes);
        int offset = hash[19] & 0xf;
        long truncatedHash = hash[offset] & 0x7f;
        for (int i = 1; i < 4; i++) {
            truncatedHash <<= 8;
            truncatedHash |= hash[offset + i] & 0xff;
        }
        return truncatedHash % 1000000;
    }

    public String generateSecret() {
        byte[] buffer = new byte[10];
        new SecureRandom().nextBytes(buffer);
        return new String(new Base32().encode(buffer));
    }

    public String generateQRCode(String otpProtocol) throws Throwable {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BitMatrix matrix = writer.encode(otpProtocol, BarcodeFormat.QR_CODE, 250, 250);
            MatrixToImageWriter.writeToStream(matrix, "PNG", byteArrayOutputStream);
            return new String(Base64.getEncoder().encode(byteArrayOutputStream.toByteArray()));
        } catch (Exception e) {
            throw new Throwable(e);
        }
    }
}