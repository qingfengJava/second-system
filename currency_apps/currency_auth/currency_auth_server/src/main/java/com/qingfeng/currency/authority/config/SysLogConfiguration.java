package com.qingfeng.currency.authority.config;

import com.qingfeng.currency.authority.biz.service.common.OptLogService;
import com.qingfeng.currency.log.entity.OptLogDTO;
import com.qingfeng.currency.log.event.SysLogListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.function.Consumer;

/**
 * 系统操作日志的配置类
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/18
 */
@EnableAsync
@Configuration
public class SysLogConfiguration {

    /**
     * 创建日志记录的监听器对象
     * @param optLogService
     * @return
     */
    @Bean
    public SysLogListener sysLogListener(OptLogService optLogService) {
        Consumer<OptLogDTO> consumer = (optLog) -> optLogService.save(optLog);
        return new SysLogListener(consumer);
    }
}
