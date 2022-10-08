package com.qingfeng.cms.plan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 学分修读方案模块
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/10/8
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PlanStandardApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlanStandardApplication.class, args);
    }
}
