package com.qingfeng.service.impl;

import com.qingfeng.dao.NationMapper;
import com.qingfeng.entity.Nation;
import com.qingfeng.service.NationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/5/8
 */
@Service
public class NationServiceImpl implements NationService {

    @Autowired
    private NationMapper nationMapper;

    /**
     * 使用缓存存储名族数据
     * @Cacheable  value:缓存的首名称  keyGenerator：生成名称的规则
     * @return
     */
    @Override
    @Cacheable(value = "nation", keyGenerator = "keyGenerator")
    public List<Nation> findAll() {
        List<Nation> nations = nationMapper.selectAll();
        return nations;
    }
}
