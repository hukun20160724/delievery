package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: OSSConfig
 * Package: com.sky.config
 * Description:
 * Config OSS
 *
 * @Author Kun Hu
 * @Create 6/6/24 14:27
 * @Version 1.0
 */
@Configuration
@Slf4j
public class OSSConfig {

    @Bean
    @ConditionalOnMissingBean
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties) {
        log.info("start create oss upload tools........");
        AliOssUtil aliOssUtil = new AliOssUtil(aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName());
        return aliOssUtil;
    }
}
