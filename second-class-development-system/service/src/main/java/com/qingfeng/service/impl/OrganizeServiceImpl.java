package com.qingfeng.service.impl;

import com.qingfeng.constant.ResStatus;
import com.qingfeng.dao.OrganizeImgMapper;
import com.qingfeng.dao.OrganizeMapper;
import com.qingfeng.entity.Organize;
import com.qingfeng.entity.OrganizeImg;
import com.qingfeng.service.OrganizeService;
import com.qingfeng.utils.PageHelper;
import com.qingfeng.vo.OrganizeVo;
import com.qingfeng.vo.ResultVO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
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
    @Autowired
    private OrganizeImgMapper organizeImgMapper;

    @Override
    public ResultVO addOrganizeInfo(Integer uid, Organize organize) {
        //首先要根据uid查询社团组织对应的相亲信息是否存在
        Example example = new Example(Organize.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", uid);
        Organize oldOrganize = organizeMapper.selectOneByExample(example);
        //定义一个记录数
        int count = 0;
        if (oldOrganize == null) {
            //说明没有记录，要进行添加操作
            organize.setUserId(uid);
            organize.setUpdateTime(new Date());
            organize.setCreateTime(new Date());
            organize.setUpdateTime(new Date());
            organize.setIsDelete(0);
            //封装完信息进行保存操作
            count = organizeMapper.insertUseGeneratedKeys(organize);
            if (count > 0) {
                //说明添加或更新信息成功
                return new ResultVO(ResStatus.OK, "信息保存成功！", organize);
            }
        } else {
            return new ResultVO(ResStatus.NO, "用户已经存在！", null);
        }
        return new ResultVO(ResStatus.NO, "网络超时，信息保存失败！", null);
    }

    @Override
    public ResultVO checkOrganizeInfo(Integer uid) {
        OrganizeVo organizeVo = organizeMapper.checkOrganizeInfo(uid);
        if (organizeVo != null) {
            //说明查询成功
            return new ResultVO(ResStatus.OK, "success", organizeVo);
        }
        return new ResultVO(ResStatus.NO, "fail", null);
    }


    @Override
    public ResultVO queryOrganize(Integer pageNum, Integer limit, String organizeName) {
        try {
            //分页查询
            int start = (pageNum - 1) * limit;
            RowBounds rowBounds = new RowBounds(start, limit);
            Example example = new Example(Organize.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("isDelete", 0);
            criteria.andLike("organizeName", "%" + organizeName + "%");
            List<Organize> organizeList = organizeMapper.selectByExampleAndRowBounds(example, rowBounds);
            //查询总记录数
            int count = organizeMapper.selectCountByExample(example);
            //计算总页数
            int pageCount = count % limit == 0 ? count / limit : count / limit + 1;
            //封装数据
            PageHelper<Organize> pageHelper = new PageHelper<>(count, pageCount, organizeList);
            //不管查没查到直接返回数据
            return new ResultVO(ResStatus.OK, "success", pageHelper);
        } catch (Exception e) {
            e.printStackTrace();
            //防止查询出现异常情况
            return new ResultVO(ResStatus.NO, "fail", null);
        }
    }

    @Override
    @Transactional
    public ResultVO deleteById(Integer organizeId) {
        Organize organize = new Organize();
        organize.setOrganizeId(organizeId);
        organize.setIsDelete(1);
        int i = organizeMapper.updateByPrimaryKeySelective(organize);
        if (i > 0){
            Example example = new Example(OrganizeImg.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("organizeId", organizeId);
            organizeImgMapper.deleteByExample(example);
            return new ResultVO(ResStatus.OK, "删除成功", null);
        }
        return new ResultVO(ResStatus.NO, "删除失败", null);
    }

    @Override
    public ResultVO deleteBatch(Integer[] organizeIds) {
        Organize organize = new Organize();
        organize.setIsDelete(1);
        Example example = new Example(Organize.class);
        example.createCriteria().andIn("organizeId", Arrays.asList(organizeIds));
        int i = organizeMapper.updateByExampleSelective(organize, example);
        if (i > 0){
            //将社团照片一并删除
            Example example1 = new Example(OrganizeImg.class);
            example1.createCriteria().andIn("organizeId", Arrays.asList(organizeIds));
            organizeImgMapper.deleteByExample(example1);
            return new ResultVO(ResStatus.OK, "删除成功", null);
        }
        return new ResultVO(ResStatus.NO, "删除失败", null);
    }
}
