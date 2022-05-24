package com.qingfeng.service.impl;

import com.qingfeng.constant.ResStatus;
import com.qingfeng.dao.ScoreMapper;
import com.qingfeng.entity.Score;
import com.qingfeng.service.ScoreService;
import com.qingfeng.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/5/22
 */
@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreMapper scoreMapper;

    @Override
    public ResultVO queryScore(Integer uid) {
        Example example = new Example(Score.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userid", uid);
        return new ResultVO(ResStatus.OK,"success",scoreMapper.selectOneByExample(example));
    }
}
