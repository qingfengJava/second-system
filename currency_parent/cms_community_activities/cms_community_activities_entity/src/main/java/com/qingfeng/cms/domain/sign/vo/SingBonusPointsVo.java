package com.qingfeng.cms.domain.sign.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/2
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@HeadRowHeight(25)
@ColumnWidth(30)
public class SingBonusPointsVo {

    @ApiModelProperty(value = "活动的名称")
    @ExcelProperty(value = "活动名",index = 0)
    private String activeName;

    @ApiModelProperty(value = "分值")
    @ExcelProperty(value = "分值",index = 1)
    private Double activeScore;

    @ApiModelProperty(value = "用户Id")
    @ExcelProperty(value = "用户Id",index = 2)
    private Long userId;

    @ApiModelProperty(value = "学号")
    @ExcelProperty(value = "学号",index = 3)
    private String studentNum;

    @ApiModelProperty(value = "学生姓名")
    @ExcelProperty(value = "学生姓名",index = 4)
    private String studentName;

    @ApiModelProperty(value = "学院")
    @ExcelProperty(value = "学院",index = 5)
    private String studentCollege;

    @ApiModelProperty(value = "专业")
    @ExcelProperty(value = "专业",index = 6)
    private String studentMajor;

    @ApiModelProperty(value = "性别")
    @ExcelProperty(value = "性别",index = 7)
    private String studentSex;

    @ApiModelProperty(value = "联系电话")
    @ExcelProperty(value = "联系电话",index = 8)
    private String studentTel;

    @ApiModelProperty(value = "QQ号")
    @ExcelProperty(value = "QQ号",index = 9)
    private String studentQq;

}
