package com.qingfeng.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 第二课堂，文件管理的相关服务
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/10/8
 */
@SpringBootApplication
@EnableDiscoveryClient
public class FileOssApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileOssApplication.class, args);
    }
}
