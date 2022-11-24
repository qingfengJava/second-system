package com.qingfeng.cms.domain.dict.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
 * @date 2022/11/24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@HeadRowHeight(25)
@ColumnWidth(30)
@ApiModel(value = "DictExcelVo", description = "组织架构表   数据字典实体")
public class DictExcelVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @ExcelProperty(value = "Id",index = 0)
    private Long id;

    @ApiModelProperty(value = "父级id, 默认是0")
    @ExcelProperty(value = "父级Id",index = 1)
    private Long parentId;

    @ApiModelProperty(value = "数据字典名称")
    @ExcelProperty(value = "名称",index = 2)
    private String dictName;

    @ApiModelProperty(value = "数据字典对应的值，偶尔有用")
    @ExcelProperty(value = "值",index = 3)
    private String dictValue;

    @ApiModelProperty(value = "数据字典对应的编码，用于分类")
    @ExcelProperty(value = "编码",index = 4)
    private String dictCode;

}
