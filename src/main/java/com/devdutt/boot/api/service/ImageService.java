package com.devdutt.boot.api.service;

import com.devdutt.boot.api.entity.ImageEntity;
import com.devdutt.boot.api.repository.ImageRepository;
import com.devdutt.boot.api.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public String uploadImage(MultipartFile multipartFile) throws IOException {
        String result = null;
        ImageEntity imageEntity = imageRepository.save(ImageEntity.builder()
                .name(multipartFile.getOriginalFilename())
                .type(multipartFile.getContentType())
                .imageData(ImageUtils.compressImage(multipartFile.getBytes())).build());

        if (imageEntity != null) {
            result = "File upload successfully: " + multipartFile.getOriginalFilename();
        } else {
            result = "File not upload successfully...!";
        }
        return result;
    }

    public byte[] downloadImage(String fileName) {
        Optional<ImageEntity> dbImageData = imageRepository.findByName(fileName);
        byte[] images = ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }
}
