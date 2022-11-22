package com.qingfeng.cms.domain.dict.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.currency.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 组织架构表   数据字典
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "DictEntity", description = "组织架构表   数据字典实体")
@TableName("mc_dict")
public class DictEntity extends Entity<Long> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "父级id, 默认是0")
	private Long parentId;

	@ApiModelProperty(value = "数据字典名称")
	private String dictName;

	@ApiModelProperty(value = "数据字典对应的值，偶尔有用")
	private String dictValue;

	@ApiModelProperty(value = "数据字典对应的编码，用于分类")
	private String dictCode;

	public DictEntity(Long id, LocalDateTime createTime, Long createUser,
					  LocalDateTime updateTime, Long updateUser,
					  Long parentId, String dictName, String dictValue,
					  String dictCode) {
		super(id, createTime, createUser, updateTime, updateUser);
		this.parentId = parentId;
		this.dictName = dictName;
		this.dictValue = dictValue;
		this.dictCode = dictCode;
	}
}
