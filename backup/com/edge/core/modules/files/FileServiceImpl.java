package com.edge.core.modules.files;

import com.edge.core.config.CoreConstants;
import com.edge.core.modules.communications.CoreCommunicationSender;
import com.edge.core.security.Encrypter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileServiceImpl implements FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public static final String IMAGES_BUCKET = "images";

    @Autowired
    private Encrypter encrypter;

    @Autowired
    private CoreCommunicationSender coreCommunicationSender;

    @Autowired
    private AmazonClient amazonClient;

    @Value(value = "${property.baseDirectory}")
    private String baseDirectory;

    @Value(value = "${appName}")
    private String appName;

    public FileServiceImpl() {

    }

    public String getBaseDirectory() {
        return baseDirectory;
    }

    public void setBaseDirectory(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    private static String getFileExtension(MultipartFile file) {
        String name = file.getOriginalFilename();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }

    private static File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new File(multipart.getOriginalFilename());
        multipart.transferTo(convFile);
        return convFile;
    }

    @Override
    @Transactional
    public String uploadImage(String subBucket, String entityId, String fileType, MultipartFile file, int width) throws Exception {

        String fileExt = getFileExtension(file);

        if (fileExt == null || fileExt.trim().length() == 0) {
            fileExt = "JPG";
        }

        fileExt = fileExt.toUpperCase();

        String filePathInBucket = "";

        if (fileType.equals("identityDocument")) {
            String fileName = encrypter.encrypt(entityId);
            filePathInBucket = subBucket + File.separatorChar + "identityDocument" + File.separatorChar
                    + fileName + "." + fileExt;

        } else {
            String fileName = RandomStringUtils.randomAlphanumeric(CoreConstants.FILE_NAME_SIZE).toUpperCase();
            filePathInBucket = subBucket + File.separatorChar
                    + entityId + File.separatorChar
                    + fileName + "." + fileExt;
        }

        String localPath = baseDirectory + File.separatorChar + filePathInBucket;

        createDirectory(localPath);

        // Retrieve image from the classpath.
        InputStream is = file.getInputStream();

        // Prepare buffered image.
        BufferedImage image = ImageIO.read(is);
        ImageIO.write(Scalr.resize(image, Method.ULTRA_QUALITY, width, Scalr.OP_BRIGHTER), fileExt, new File(localPath));

        tryWatermark(fileType, fileExt, localPath);
        return tryS3Upload(filePathInBucket, localPath);

    }

    private String tryS3Upload(String filePathInBucket, String localPath) {
        String uploadPath = "";
        try {

            logger.error(" ################### Uploading File to S3 - LocalPath : " + localPath);
            logger.error(" ################### Uploading File to S3 - Bucket Path : " + filePathInBucket);

            uploadPath = amazonClient.uploadFile(new File(localPath), filePathInBucket);

            logger.error("#################### Uploading File to S3 Success : " + uploadPath);

        } catch (Throwable ex) {
            logger.error("#################### S3 Upload Failed.." + filePathInBucket);
            ex.printStackTrace();
            coreCommunicationSender.s3UploadFailure(ex);
            //throw ex;
        }
        logger.error("#################### Returning Uploading File to S3 Success : " + uploadPath);
        return uploadPath;
    }

    private void tryWatermark(String fileType, String fileExt, String localPath) {

        try {

            if (fileType.equals("identityDocument")) {
                logger.error(" ########################## Trying Big Watermark - local path " + localPath);
                addBigWatermark(new File(localPath), fileExt);
                logger.error(" ########################## Trying Big Watermark Success - local path " + localPath);
            }

            logger.error(" ########################## Trying Watermark - local path " + localPath);
            addWatermark(new File(localPath), fileExt);
            logger.error(" ########################## Trying Watermark Success - local path " + localPath);
        } catch (Throwable ex) {
            logger.error(" ########################## Error While Trying Watermark - local path " + localPath, ex);
            ex.printStackTrace();
        }

    }

    private void createDirectory(String localPath) throws IOException {
        String fileDir = localPath.substring(0, localPath.lastIndexOf("/"));

        FileUtils.deleteDirectory(new File(fileDir));

        Path pathDir = Paths.get(fileDir);
        Files.createDirectories(pathDir);
    }

    @Override
    @Transactional
    public File getFile(String entityId, String fileType, String fileName) {
        String fileDir = baseDirectory + File.separatorChar + entityId + File.separatorChar + fileType;
        String filePath = fileDir + File.separatorChar + fileName;
        return new File(filePath);
    }

    private void addBigWatermark(File imageFile, String fileExt) {
        try {
            BufferedImage image = ImageIO.read(imageFile);

            Graphics graphics1 = image.getGraphics();
            graphics1.setFont(new Font(CoreConstants.WATERMARK_FONT, Font.ITALIC, 90));
            String watermark1 = "Not to be";
            graphics1.drawString(watermark1, image.getWidth() / 2 - 180, image.getHeight() / 2 - 100);

            Graphics graphics2 = image.getGraphics();
            graphics2.setFont(new Font(CoreConstants.WATERMARK_FONT, Font.ITALIC, 90));
            String watermark2 = "used for";
            graphics1.drawString(watermark2, image.getWidth() / 2 - 180, image.getHeight() / 2 - 20);

            Graphics graphics3 = image.getGraphics();
            graphics3.setFont(new Font(CoreConstants.WATERMARK_FONT, Font.ITALIC, 90));
            String watermark3 = "any purpose";
            graphics1.drawString(watermark3, image.getWidth() / 2 - 180, image.getHeight() / 2 + 60);

            ImageIO.write(image, fileExt, imageFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addWatermark(File imageFile, String fileExt) {
        try {
            BufferedImage image = ImageIO.read(imageFile);

            Graphics graphics = image.getGraphics();
            graphics.setFont(new Font(CoreConstants.WATERMARK_FONT, Font.PLAIN, 20));
            String watermark = "\u00a9 " + appName;

            graphics.drawString(watermark, image.getWidth() - 180, image.getHeight() - 20);
            ImageIO.write(image, fileExt, imageFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static File compress(File file, String fileExt, int width) throws Exception {

        // Prepare buffered image.
        BufferedImage image = ImageIO.read(file);
        ImageIO.write(Scalr.resize(image, Method.ULTRA_QUALITY, width, Scalr.OP_BRIGHTER), fileExt, file);
        return file;
    }


    @Override
    public void deleteFile(String fileUrl) {

        amazonClient.deleteFileFromS3Bucket(fileUrl);
    }

}


