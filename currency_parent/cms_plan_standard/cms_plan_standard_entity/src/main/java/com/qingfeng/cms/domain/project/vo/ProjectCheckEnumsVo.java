package com.qingfeng.cms.domain.project.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/11/21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ProjectCheckEnumsVo",description = "项目审核枚举实体")
public class ProjectCheckEnumsVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String label;
    private String value;
}
