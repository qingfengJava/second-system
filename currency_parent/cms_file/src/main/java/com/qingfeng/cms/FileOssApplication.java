package com.qingfeng.cms;

import com.qingfeng.currency.auth.server.EnableAuthServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 第二课堂，文件管理的相关服务
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/10/8
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(value = {"com.qingfeng.sdk"})
@EnableTransactionManagement
@Slf4j
@EnableAuthServer
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class FileOssApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileOssApplication.class, args);
    }
}
