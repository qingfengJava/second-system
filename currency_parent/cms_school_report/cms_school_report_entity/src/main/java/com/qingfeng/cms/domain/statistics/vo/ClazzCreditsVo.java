package com.qingfeng.cms.domain.statistics.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "ClazzCreditsVo", description = "学生学分修读情况")
public class ClazzCreditsVo {

    /**
     * 每一项的名字
     */
    private String name;

    /**
     * 每一项的值
     */
    private Integer y;
}
