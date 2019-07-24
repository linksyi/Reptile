package com.reptile.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: Mr.ZuoY
 * @Description:
 * @Date: 2019/2/20 17:21
 */

@Component
@ConfigurationProperties(prefix = "front.aliyun")
@Data
public class ALiYunConfig {
    private String id;
    private String key;
}
