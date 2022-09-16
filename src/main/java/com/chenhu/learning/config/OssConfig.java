package com.chenhu.learning.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 陈虎
 * @description
 * @date 2022-09-16 9:49
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class OssConfig {
    private String endpoint;
    private Integer port;
    private String accessKey;
    private String secretKey;
    private Boolean secure;
    private String bucketName;
    private Long imageSize;
    private Long fileSize;
}
