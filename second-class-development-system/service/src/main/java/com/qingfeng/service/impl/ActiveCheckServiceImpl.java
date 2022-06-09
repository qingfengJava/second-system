package com.qingfeng.service.impl;

import com.qingfeng.constant.ResStatus;
import com.qingfeng.dao.CheckMapper;
import com.qingfeng.entity.Check;
import com.qingfeng.service.ActiveCheckService;
import com.qingfeng.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/6/7
 */
@Service
public class ActiveCheckServiceImpl implements ActiveCheckService {

    @Autowired
    private CheckMapper checkMapper;

    /**
     * 根据活动Id查询活动审核的详情信息
     *
     * @param activeId
     * @return
     */
    @Override
    public ResultVO selectActiveDetailsById(Integer activeId) {
        Example example = new Example(Check.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("activeid", activeId);
        criteria.andEqualTo("isDelete", 0);
        Check check = checkMapper.selectOneByExample(example);
        return new ResultVO(ResStatus.OK, "success", check);
    }

    /**
     * 上传活动图片
     *
     * @param applyId
     * @param newFileName
     */
    @Override
    public void uploadActiveImg(Integer applyId, String newFileName) {
        //上传活动图片，首先要根据Id查询信息
        Example example = new Example(Check.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("activeid", applyId);
        criteria.andEqualTo("isDelete", 0);
        Check check = checkMapper.selectOneByExample(example);
        if (check == null) {
            //没有信息，我们可以直接进行添加
            Check check1 = new Check();
            check1.setActiveid(applyId);
            check1.setActiveImg(newFileName);
            check1.setIsPass(0);
            check1.setIsDelete(0);
            checkMapper.insertUseGeneratedKeys(check1);
        } else {
            //判断是否有图片信息
            if (check.getActiveImg() == null || "".equals(check.getActiveImg())) {
                //没有图片信息，我们可以直接进行添加
                check.setActiveImg(newFileName);
            } else {
                //有图片信息，我们可以直接进行更新
                check.setActiveImg(check.getActiveImg() + "," + newFileName);
            }
            check.setIsPass(0);
            checkMapper.updateByPrimaryKeySelective(check);
        }
    }

    /**
     * 删除上传的活动图片
     *
     * @param applyId
     * @param fileName
     * @return
     */
    @Override
    public ResultVO deleteActiveImg(Integer applyId, String fileName) {
        Check check = (Check) this.selectActiveDetailsById(applyId).getData();
        String[] split = check.getActiveImg().split(",");
        StringBuffer stringBuffer = new StringBuffer();
        for(int i = 0; i < split.length; i++) {
            if (!split[i].equals(fileName)) {
                if (i == split.length - 1) {
                    stringBuffer.append(split[i]);
                } else {
                    stringBuffer.append(split[i] + ",");
                }
            }
        }
        check.setActiveImg(stringBuffer.toString());
        check.setIsPass(0);
        int i = checkMapper.updateByPrimaryKeySelective(check);
        if (i > 0) {
            return new ResultVO(ResStatus.OK, "success",check);
        } else {
            return new ResultVO(ResStatus.NO, "error",null);
        }
    }

    @Override
    public ResultVO submitCheck(Check check) {
        int result = 0;
        if(check.getCheckid() == null) {
            check.setIsPass(0);
            check.setIsDelete(0);
            result = checkMapper.insertUseGeneratedKeys(check);
        } else {
            result = checkMapper.updateByPrimaryKeySelective(check);
        }

        if (result > 0) {
            return new ResultVO(ResStatus.OK, "success",check);
        } else {
            return new ResultVO(ResStatus.NO, "error",null);
        }
    }
}
