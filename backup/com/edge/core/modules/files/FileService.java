package com.edge.core.modules.files;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;


public interface FileService {

    String uploadImage(String subBucket, String entityId, String fileType, MultipartFile file, int width) throws Exception;

    File getFile(String entityId, String fileType, String fileName);

    void deleteFile(String fileUrl);
}