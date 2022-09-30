package com.qingfeng.currency.authority.biz.service.core;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.currency.authority.dto.core.StationPageDTO;
import com.qingfeng.currency.authority.entity.core.Station;

/**
 * 业务接口
 * 岗位
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/18
 */
public interface StationService extends IService<Station> {

    /**
     * 分页
     *
     * @param page
     * @param data
     */
    IPage<Station> findStationPage(Page page, StationPageDTO data);
}
