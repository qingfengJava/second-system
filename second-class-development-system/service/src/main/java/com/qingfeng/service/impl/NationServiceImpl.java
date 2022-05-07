package com.qingfeng.service.impl;

import com.qingfeng.constant.ResStatus;
import com.qingfeng.dao.NationMapper;
import com.qingfeng.entity.Nation;
import com.qingfeng.service.NationService;
import com.qingfeng.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public ResultVO findAll() {
        List<Nation> nations = nationMapper.selectAll();
        return new ResultVO(ResStatus.OK,"success",nations);
    }
}
