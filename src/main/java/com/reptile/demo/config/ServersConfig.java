package com.reptile.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: Mr.ZuoY
 * @Description:
 * @Date: 2019/2/18 18:08
 */

@Component
@ConfigurationProperties(prefix = "front")
@Data
public class ServersConfig {

    private String url;
    private Queue<String> urlQueue = new LinkedList<>();

    public Queue<String> getUrlQueue() {
        return urlQueue;
    }

}
