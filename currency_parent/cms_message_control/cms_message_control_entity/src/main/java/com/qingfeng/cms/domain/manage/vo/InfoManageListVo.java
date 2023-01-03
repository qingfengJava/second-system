package com.qingfeng.cms.domain.manage.vo;

import com.qingfeng.cms.domain.manage.entity.InfoManageEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/1/3
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "InfoManageListVo", description = "信息管理分页数据实体")
public class InfoManageListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer total;

    private List<InfoManageEntity> infoManageEntityList;

}
