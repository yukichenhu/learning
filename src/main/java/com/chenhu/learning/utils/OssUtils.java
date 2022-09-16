package com.chenhu.learning.utils;

import com.chenhu.learning.config.OssConfig;
import io.minio.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author 陈虎
 * @description minio存储测试
 * @date 2022-09-16 9:24
 */
@Service
public class OssUtils {
    private MinioClient minioClient;
    @Resource
    private OssConfig ossConfig;
    @PostConstruct
    private void init(){
        minioClient= MinioClient
                .builder()
                .endpoint(ossConfig.getEndpoint(),ossConfig.getPort(),ossConfig.getSecure())
                .credentials(ossConfig.getAccessKey(), ossConfig.getSecretKey())
                .build();
    }

    public String upload(MultipartFile file){
        //检测bucket
        try {
            boolean find=minioClient.bucketExists(BucketExistsArgs.builder().bucket(ossConfig.getBucketName()).build());
            if(!find){
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(ossConfig.getBucketName()).build());
            }
            //上传
            ObjectWriteResponse response=minioClient.putObject(PutObjectArgs
                    .builder()
                    .bucket(ossConfig.getBucketName())
                    .object(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .stream(file.getInputStream(),file.getSize(),-1)
                    .build());
            return ossConfig.getEndpoint()+":"+ossConfig.getPort()+"/"+ossConfig.getBucketName()+"/"+response.object();
        } catch (Exception e) {
            return null;
        }
    }
}
