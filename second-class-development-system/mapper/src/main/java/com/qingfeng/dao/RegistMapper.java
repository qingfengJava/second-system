package com.qingfeng.dao;

import com.qingfeng.entity.Regist;
import com.qingfeng.entity.RegistVo;
import com.qingfeng.generaldao.GeneralDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 活动报名表持久层接口
 *
 * @author 清风学Java
 */
@Repository
public interface RegistMapper extends GeneralDao<Regist> {

    /**
     * 根据用户id，关联查询用户报名待参与(参与)的活动列表
     * @param uid
     * @param start
     * @param limit
     * @return
     */
    List<RegistVo> checkRegistration(@Param("uid") Integer uid,
                                     @Param("participate") Integer participate,
                                     @Param("start") int start,
                                     @Param("limit") int limit);

    /**
     * 根据用户报名表的主键Id指定修改用户报名的信息
     * @param regist
     * @return
     */
    int updateRegistById(Regist regist);

    /**
     * 根据申请表Id查询学生报名活动的详情信息
     * @param uid
     * @param applyId
     * @return
     */
    RegistVo queryRegistActiveDetails(Integer uid,Integer applyId);

}