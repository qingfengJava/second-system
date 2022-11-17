package com.qingfeng.cms.domain.level.vo;

import com.qingfeng.cms.domain.level.enums.LevelCheckEnum;
import com.qingfeng.cms.domain.rule.vo.CreditRulesVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 项目等级表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-10-08 19:44:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LevelListVo",description = "等级列表模块实体")
public class LevelListVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "关联的项目id")
	private Long projectId;

	@ApiModelProperty(value = "等级的名字或者是具体内容，没有就写无")
	private String levelContent;

	@ApiModelProperty(value = "是否审核通过，只有学院下的需要审核")
	private LevelCheckEnum isCheck;

	@ApiModelProperty(value = "审核的详情，不需要审核的就写无")
	private String checkDetail;

	private List<CreditRulesVo> creditRulesVoList;
}
