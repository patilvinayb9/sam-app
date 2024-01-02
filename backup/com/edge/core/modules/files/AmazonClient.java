package com.edge.core.modules.files;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;

@Service
public class AmazonClient {

    private AmazonS3 s3client;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonProperties.endpointCdnUrl}")
    private String endpointCdnUrl;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(credentials);

        this.s3client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(this.endpointUrl, "sgp1")
                ).withCredentials(
                        awsStaticCredentialsProvider
                ).build();


    }

    private void uploadFileTos3bucket(File file, String filePathInBucket) {
        s3client.putObject(new PutObjectRequest(bucketName, filePathInBucket, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String uploadFile(File file, String filePathInBucket) {

        String fileUrl = "";
        try {
            fileUrl = getCdnPrefix() + filePathInBucket;
            uploadFileTos3bucket(file, filePathInBucket);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    private String getCdnPrefix() {
        return "https://" + bucketName + "." + endpointCdnUrl + "/";
    }

    public String deleteFileFromS3Bucket(String fileUrl) {
        if (fileUrl == null || fileUrl.trim().length() == 0) {
            return "No File";
        }
        try {
            String toReplace = getCdnPrefix();
            String filePathInBucket = fileUrl.replace(toReplace, "");
            s3client.deleteObject(new DeleteObjectRequest(bucketName, filePathInBucket));
            return "Successfully deleted";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "Error While Deleting";
    }
}