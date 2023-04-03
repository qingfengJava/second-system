package com.qingfeng.cms.biz.sign.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingfeng.cms.domain.sign.dto.ActiveApplySignQueryDTO;
import com.qingfeng.cms.domain.sign.entity.ActiveSignEntity;
import com.qingfeng.cms.domain.sign.vo.UserActiveSignFrontVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 活动报名表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-01-31 11:41:11
 */
@Repository
public interface ActiveSignDao extends BaseMapper<ActiveSignEntity> {

    /**
     * 根据条件查询已报名的活动信息
     * @param activeApplySignQueryDTO
     * @param userId
     * @return
     */
    Integer selectSignCount(@Param("activeApplySignQueryDTO") ActiveApplySignQueryDTO activeApplySignQueryDTO,
                            @Param("userId") Long userId);

    /**
     * 查询用户已经报名的活动信息
     * @param activeApplySignQueryDTO
     * @param userId
     * @return
     */
    List<ActiveSignEntity> selectSignList(@Param("activeApplySignQueryDTO") ActiveApplySignQueryDTO activeApplySignQueryDTO,
                                          @Param("userId") Long userId);

    /**
     * 查询学生报名的活动，按活动时间正序排序，已结束的活动排在最后
     * @param userId
     * @return
     */
    List<UserActiveSignFrontVo> findUserSignActiveForFront(Long userId);
}
