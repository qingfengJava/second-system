package com.qingfeng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 第二课堂管理系统
 *
 * @MapperScan("com.qingfeng.mapper") 指定dao接口所在的包
 *
 * @author 清风学Java
 */
@SpringBootApplication
@MapperScan("com.qingfeng.mapper")
public class SecondManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecondManagementSystemApplication.class, args);
    }

}
