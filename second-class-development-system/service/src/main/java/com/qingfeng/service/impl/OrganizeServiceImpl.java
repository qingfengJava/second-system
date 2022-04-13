package com.qingfeng.service.impl;

import com.qingfeng.dao.OrganizeMapper;
import com.qingfeng.entity.Organize;
import com.qingfeng.vo.OrganizeVo;
import com.qingfeng.service.OrganizeService;
import com.qingfeng.utils.PageHelper;
import com.qingfeng.constant.ResStatus;
import com.qingfeng.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/3/5
 */
@Service
public class OrganizeServiceImpl implements OrganizeService {

    @Autowired
    private OrganizeMapper organizeMapper;

    @Override
    public ResultVO updateOrganizeInfo(Integer uid, Organize organize) {
        //首先要根据uid查询社团组织对应的相亲信息是否存在
        //先根据uid查询详情表是否有该校领导的详情记录
        Example example = new Example(Organize.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",uid);
        Organize oldOrganize = organizeMapper.selectOneByExample(example);
        //定义一个记录数
        int count = 0;
        organize.setUserId(uid);
        organize.setUpdateTime(new Date());
        if (oldOrganize == null){
            //说明没有记录，要进行添加操作
            organize.setCreateTime(new Date());
            organize.setIsDelete(0);
            //封装完信息进行保存操作
            count = organizeMapper.insertUseGeneratedKeys(organize);
        }else{
            //说明是进行信息更新操作
            organize.setOrganizeId(oldOrganize.getOrganizeId());
            //更新
            count = organizeMapper.updateByPrimaryKeySelective(organize);
        }
        if (count > 0) {
            //说明添加或更新信息成功
            return new ResultVO(ResStatus.OK, "信息保存成功！", organize);
        }else {
            return new ResultVO(ResStatus.NO,"网络超时，信息保存失败！",null);
        }
    }

    @Override
    public ResultVO checkOrganizeInfo(Integer uid) {
        OrganizeVo organizeVo = organizeMapper.checkOrganizeInfo(uid);
        if (organizeVo != null){
            //说明查询成功
            return new ResultVO(ResStatus.OK,"success",organizeVo);
        }
        return new ResultVO(ResStatus.NO,"fail",null);
    }


    @Override
    public ResultVO queryOrganize(Integer pageNum, Integer limit) {
        try {
            //分页查询
            int start = (pageNum - 1) * limit;
            List<Organize> organizeList = organizeMapper.queryOrganize(start, limit);
            //查询总记录数
            Example example = new Example(Organize.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("isDelete",0);
            int count = organizeMapper.selectCountByExample(example);
            //计算总页数
            int pageCount = count % limit == 0 ? count / limit : count / limit + 1;
            //封装数据
            PageHelper<Organize> pageHelper = new PageHelper<>(count, pageCount, organizeList);
            //不管查没查到直接返回数据
            return new ResultVO(ResStatus.OK,"success",pageHelper);
        } catch (Exception e) {
            e.printStackTrace();
            //防止查询出现异常情况
            return new ResultVO(ResStatus.NO,"fail",null);
        }
    }
}
