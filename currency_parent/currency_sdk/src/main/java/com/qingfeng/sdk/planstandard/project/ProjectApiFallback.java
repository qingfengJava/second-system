package com.qingfeng.sdk.planstandard.project;

import com.qingfeng.cms.domain.project.dto.ProjectQueryDTO;
import com.qingfeng.cms.domain.project.entity.ProjectEntity;
import com.qingfeng.cms.domain.project.vo.ProjectListVo;
import com.qingfeng.currency.base.R;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/7
 */
public class ProjectApiFallback implements ProjectApi {

    @Override
    public R<List<ProjectListVo>> list(ProjectQueryDTO projectQueryDTO) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }

    @Override
    public R<List<ProjectEntity>> projectInfoByIds(List<Long> projectIds) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
