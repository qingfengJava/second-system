package com.qingfeng.currency.authority.biz.service.common;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.currency.authority.entity.common.OptLog;
import com.qingfeng.currency.log.entity.OptLogDTO;

/**
 * 系统日志业务层接口
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/18
 */
public interface OptLogService extends IService<OptLog> {

    /**
     * 保存日志
     * @param optLogDTO
     * @return
     */
    boolean save(OptLogDTO optLogDTO);
}
