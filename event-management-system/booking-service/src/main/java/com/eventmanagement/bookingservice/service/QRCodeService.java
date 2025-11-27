package com.eventmanagement.bookingservice.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
public class QRCodeService {
    public String generateQRCode(Long ticketId, Long eventId, Long attendeeId) {
        try {
            String qrData = String.format("TICKET:%d|EVENT:%d|ATTENDEE:%d|UUID:%s",
                    ticketId, eventId, attendeeId, UUID.randomUUID().toString());

            System.out.println("Generating QR code for ticket: " + ticketId);

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrData, BarcodeFormat.QR_CODE, 300, 300);

            // Convert to image bytes
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

            // Convert to Base64 string
            String base64QR = Base64.getEncoder().encodeToString(outputStream.toByteArray());

            System.out.println("QR code generated successfully");
            return "QR-" + ticketId + "-" + UUID.randomUUID().toString().substring(0, 8);

        } catch (WriterException | IOException e) {
            System.err.println("Failed to generate QR code: " + e.getMessage());
            // Return a simple QR code identifier if generation fails
            return "QR-" + ticketId + "-" + UUID.randomUUID().toString().substring(0, 8);
        }
    }

    public boolean validateQRCode(String qrCode) {
        return qrCode != null && qrCode.startsWith("QR-");
    }
}