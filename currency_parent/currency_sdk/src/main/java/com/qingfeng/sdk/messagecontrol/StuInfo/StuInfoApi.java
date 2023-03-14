package com.qingfeng.sdk.messagecontrol.StuInfo;

import com.qingfeng.cms.domain.student.dto.StuInfoSaveDTO;
import com.qingfeng.cms.domain.student.entity.StuInfoEntity;
import com.qingfeng.currency.base.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/23
 */
@FeignClient(value = "cms-message-control", fallback = StuInfoApiFallback.class)
@Component
public interface StuInfoApi {

    /**
     * 根据学生用户Id查询学生用户信息详情信息
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据学生用户Id查询学生用户信息详情信息", notes = "根据学生用户Id查询学生用户信息详情信息")
    @GetMapping("/stuinfo/info/{userId}")
    public R<StuInfoEntity> info(@PathVariable("userId") Long userId);

    /**
     * 保存用户信息实体
     * @param stuInfoSaveDTO
     * @return
     */
    @ApiOperation(value = "保存用户详情信息", notes = "保存用户详情信息")
    @PostMapping("/stuinfo/save")
    public R save(@RequestBody @Validated StuInfoSaveDTO stuInfoSaveDTO);

    /**
     * 根据用户Id集合查询用户信息
     * @param userIds
     * @return
     */
    @PostMapping("/stuinfo/list")
    public R<List<StuInfoEntity>> stuInfoList(@RequestBody List<Long> userIds);

}
