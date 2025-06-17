package com.profport.lms.content.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("resource_type", "auto",
                            "type", "upload"));

            System.out.println("Upload Result: " + uploadResult);

            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }
}
