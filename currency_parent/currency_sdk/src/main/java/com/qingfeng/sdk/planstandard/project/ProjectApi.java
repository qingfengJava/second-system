package com.qingfeng.sdk.planstandard.project;

import com.qingfeng.cms.domain.project.dto.ProjectQueryDTO;
import com.qingfeng.cms.domain.project.entity.ProjectEntity;
import com.qingfeng.cms.domain.project.vo.ProjectListVo;
import com.qingfeng.currency.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/7
 */
@FeignClient(value = "cms-plan-standard", fallback = ProjectApiFallback.class)
@Component
public interface ProjectApi {

    /**
     * 根据模块Id查询项目学分列表
     * @param projectQueryDTO
     * @return
     */
    @PostMapping("/project/list")
    public R<List<ProjectListVo>> list(@RequestBody @Validated ProjectQueryDTO projectQueryDTO);

    /**
     * 根据项目Id集合查询项目信息
     * @param projectIds
     * @return
     */
    @PostMapping("/project/info/list")
    public R<List<ProjectEntity>> projectInfoByIds(@RequestBody List<Long> projectIds);
}
