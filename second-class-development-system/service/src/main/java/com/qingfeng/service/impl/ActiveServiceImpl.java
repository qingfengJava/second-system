package com.qingfeng.service.impl;

import com.qingfeng.constant.ResStatus;
import com.qingfeng.constant.UserStatus;
import com.qingfeng.dao.ApplyMapper;
import com.qingfeng.dao.RegistMapper;
import com.qingfeng.dto.RegistrationActive;
import com.qingfeng.entity.Apply;
import com.qingfeng.entity.Regist;
import com.qingfeng.service.ActiveService;
import com.qingfeng.utils.PageHelper;
import com.qingfeng.vo.ApplyVo;
import com.qingfeng.vo.RegistVo;
import com.qingfeng.vo.ResultVO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/2/14
 */
@Service
public class ActiveServiceImpl implements ActiveService {

    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private RegistMapper registMapper;

    /**
     * 查询学生已报名的活动列表
     * @param uid
     * @param participate
     * @param pageNum
     * @param limit
     * @return
     */
    @Override
    @Cacheable(value = "registrationActive",keyGenerator = "keyGenerator")
    public ResultVO checkRegistration(String uid, int participate, int pageNum, int limit, RegistrationActive registrationActive) {
        try {
            //分页查询
            int start = (pageNum - 1) * limit;
            //根据用户id调用持久层分页查询学生报名待参与的活动
            List<RegistVo> registVoList = null;
            Integer count = 0;
            if(registrationActive == null){
                registVoList = registMapper.checkRegistration(Integer.parseInt(uid),participate,start,limit);
                //查询总记录数
                Example example = new Example(Regist.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andLike("userId","%"+uid+"%");
                if (participate == 0) {
                    criteria.andEqualTo("isSign", 0);
                }
                criteria.andEqualTo("isDelete",0);
                count = registMapper.selectCountByExample(example);
            }else {
                //如果有条件就按条件查询
                registVoList = registMapper.queryRegistration(Integer.parseInt(uid),participate,start,limit,registrationActive);
                count = registMapper.queryCountRegistration(Integer.parseInt(uid),participate,registrationActive);
            }

            //计算总页数
            int pageCount = count % limit == 0 ? count / limit : count / limit + 1;
            //封装数据
            PageHelper<RegistVo> pageHelper = new PageHelper<>(count, pageCount, registVoList);

            //不管查没查到直接返回数据
            return new ResultVO(ResStatus.OK,"success",pageHelper);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            //防止查询出现异常情况
            return new ResultVO(ResStatus.NO,"fail",null);
        }
    }

    @Override
    @Cacheable(value = "newActive",keyGenerator = "keyGenerator")
    public ResultVO queryApply(Integer uid, int pageNum, int limit) {
        try {
            //新活动查询的注意事项   在有效时间范围内   学生没有报名的新活动
            //分页查询
            int start = (pageNum - 1) * limit;
            List<Apply> applyList = applyMapper.queryApply(uid,start,limit);
            //查询总记录数
            int count = applyMapper.selectApplyCount(uid,start,limit);
            //计算总页数
            int pageCount = count % limit == 0 ? count / limit : count / limit + 1;
            //封装数据
            PageHelper<Apply> pageHelper = new PageHelper<>(count, pageCount, applyList);

            //不管查没查到直接返回数据
            return new ResultVO(ResStatus.OK,"success",pageHelper);
        } catch (Exception e) {
            e.printStackTrace();
            //防止查询出现异常情况
            return new ResultVO(ResStatus.NO,"fail",null);
        }
    }

    @Override
    public ResultVO queryApplyById(String applyId) {
        Example example = new Example(Apply.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("applyId",applyId);
        List<Apply> applyList = applyMapper.selectByExample(example);
        if (applyList.size() > 0){
            return new ResultVO(ResStatus.OK,"success",applyList.get(0));
        }else {
            return new ResultVO(ResStatus.NO,"网络异常，信息不存在！",null);
        }
    }

    @Override
    public ResultVO queryApplyDetails(Integer applyId) {
        //首先要根据活动申请Id查询对应的信息
        ApplyVo applyVo = applyMapper.selectApplyById(applyId);
        if (applyVo != null){
            //说明查到了数据
            return new ResultVO(ResStatus.OK,"success",applyVo);
        }
        return new ResultVO(ResStatus.NO,"fail",null);
    }

    @Override
    public ResultVO queryRegistActiveDetails(Integer uid,Integer applyId) {
        RegistVo registVo = registMapper.queryRegistActiveDetails(uid,applyId);
        if (registVo != null){
            //说明查到了数据
            return new ResultVO(ResStatus.OK,"success",registVo);
        }
        return new ResultVO(ResStatus.NO,"fail",null);
    }

    @Override
    public ResultVO queryRegistCount(Integer applyId) {
        //主键Id   并且报名信息没有删除的  报名成功的
        Example example = new Example(Regist.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("applyId",applyId);
        criteria.andEqualTo("isSuccess",1);
        criteria.andEqualTo("isDelete",0);
        int i = registMapper.selectCountByExample(example);
        if (i >= 0){
            return new ResultVO(ResStatus.OK,"success",i);
        }
        return new ResultVO(ResStatus.NO,"fail",0);
    }

    /**
     * 分页查询所有的活动：
     * 查询过程中，根据用户身份不同，封装的查询条件不同
     * @param isAdmin
     * @param uid
     * @param pageNum
     * @param limit
     * @param schoolYear
     * @param activeType
     * @param activeName
     * @param type
     * @return
     */
    @Override
    @Cacheable(value = "active",keyGenerator = "keyGenerator")
    public ResultVO queryActive(Integer isAdmin,Integer uid,  Integer pageNum, Integer limit, String schoolYear, Integer activeType, String activeName,String type) {
        try {
            //分页条件查询
            int start = (pageNum - 1) * limit;
            RowBounds rowBounds = new RowBounds(start, limit);
            Example example = new Example(Apply.class);
            Example.Criteria criteria = example.createCriteria();
            if (schoolYear != null && !"".equals(schoolYear)){
                criteria.andLike("schoolYear","%"+schoolYear+"%");
            }
            if (activeType != null && activeType > 0){
                criteria.andEqualTo("activeType",activeType);
            }
            if (activeName != null && !"".equals(activeName)){
                criteria.andLike("activeName","%"+activeName+"%");
            }
            if (isAdmin == UserStatus.CLUB_CONSTANTS || isAdmin == UserStatus.STUDENTS_UNION_CONSTANTS){
                criteria.andEqualTo("applyUserId",uid);
                switch (type){
                    case "申请中":
                        criteria.andEqualTo("isAgree",0);
                        criteria.andEqualTo("isCheck",0);
                        criteria.andEqualTo("isEnd",0);
                        break;
                    case "申请未通过":
                        criteria.andEqualTo("isAgree",2);
                        criteria.andEqualTo("isCheck",0);
                        criteria.andEqualTo("isEnd",0);
                        break;
                    case "待审核":
                        criteria.andEqualTo("isAgree",1);
                        criteria.andEqualTo("isCheck",0);
                        criteria.andEqualTo("isEnd",0);
                        break;
                    case "审核未通过":
                        criteria.andEqualTo("isAgree",1);
                        criteria.andEqualTo("isCheck",2);
                        criteria.andEqualTo("isEnd",0);
                        break;
                    case "已结束":
                        criteria.andEqualTo("isAgree",1);
                        criteria.andEqualTo("isCheck",1);
                        criteria.andEqualTo("isEnd",1);
                        break;
                    default:
                        break;
                }
            }
            criteria.andEqualTo("isDelete",0);
            List<Apply> applyList = applyMapper.selectByExampleAndRowBounds(example, rowBounds);
            //查询总数
            int count = applyMapper.selectCountByExample(example);
            //计算总页数
            int pageCount = count % limit == 0 ? count / limit : count / limit + 1;
            //封装数据
            PageHelper<Apply> pageHelper = new PageHelper<>(count, pageCount, applyList);
            //不管查没查到直接返回数据
            return new ResultVO(ResStatus.OK, "success", pageHelper);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVO(ResStatus.NO, "fail", null);
        }
    }

}
