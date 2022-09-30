package com.qingfeng.currency.authority.biz.service.common.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.currency.authority.biz.dao.common.OptLogMapper;
import com.qingfeng.currency.authority.biz.service.common.OptLogService;
import com.qingfeng.currency.authority.entity.common.OptLog;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.log.entity.OptLogDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统日志业务层接口
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/18
 */
@Service
@Slf4j
public class OptLogServiceImpl extends ServiceImpl<OptLogMapper, OptLog> implements OptLogService {

    @Autowired
    private DozerUtils dozerUtils;

    /**
     * 保存日志
     * @param optLogDTO
     * @return
     */
    @Override
    public boolean save(OptLogDTO optLogDTO) {
        return super.save(dozerUtils.map(optLogDTO, OptLog.class));
    }
}
