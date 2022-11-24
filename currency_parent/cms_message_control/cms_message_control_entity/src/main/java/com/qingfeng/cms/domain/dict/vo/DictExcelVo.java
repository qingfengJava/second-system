package com.qingfeng.cms.domain.dict.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/11/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@HeadRowHeight(25)
@ColumnWidth(30)
public class DictExcelVo{

    @ExcelProperty(value = "Id",index = 0)
    private Long id;

    @ExcelProperty(value = "父级Id",index = 1)
    private Long parentId;

    @ExcelProperty(value = "名称",index = 2)
    private String dictName;

    @ExcelProperty(value = "值",index = 3)
    private String dictValue;

    @ExcelProperty(value = "编码",index = 4)
    private String dictCode;

}
