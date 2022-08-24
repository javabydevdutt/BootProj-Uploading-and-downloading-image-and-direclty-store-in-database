package com.devdutt.boot.api.utils;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtils {

    // compress the image bytes before storing it in the database
    public static byte[] compressImage(byte[] imageData) {
        Deflater deflater = new Deflater();
        deflater.setInput(imageData);
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageData.length);
        byte[] buffer = new byte[4 * 1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(buffer);
            outputStream.write(buffer, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception e) {

        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    // decompress the image bytes from the database
    public static byte[] decompressImage(byte[] imageData) {
        Inflater inflater = new Inflater();
        inflater.setInput(imageData);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageData.length);
        byte[] temp = new byte[4 * 1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(temp);
                outputStream.write(temp, 0, count);
            }
            outputStream.close();
        } catch (Exception e) {

        }
        return outputStream.toByteArray();
    }
}
