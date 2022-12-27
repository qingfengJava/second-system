package com.qingfeng.currency.authority.entity.auth.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qingfeng.currency.authority.converter.EasyExcelLocalDateConverter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

/**
 * 封装学生用户的相关信息
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@HeadRowHeight(25)
@ColumnWidth(30)
public class UserReadVo {

    @ExcelProperty(value = "账号(学号)",index = 0)
    private String account;

    @ExcelProperty(value = "姓名",index = 1)
    private String name;

    @ExcelProperty(value = "邮箱",index = 2)
    private String email;

    @ExcelProperty(value = "手机",index = 3)
    private String mobile;

    @ExcelProperty(value = "性别（男，女，未知）",index = 4)
    private String sex;

    @ExcelProperty(value = "启用状态 1启用 0禁用",index = 5)
    private Boolean status;

    @ExcelProperty(value = "密码，默认取学号后六位",index = 6)
    private String password;

    /**
     * 详情信息开始
     */
    @ExcelProperty(value = "学号",index = 7)
    private String studentNum;

    @ApiModelProperty(value = "出生日期")
    @DateTimeFormat(value = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ExcelProperty(value = "出生日期（格式：xxxx-xx-xx）",index = 8, converter = EasyExcelLocalDateConverter.class)
    private LocalDate birth;

    @ExcelProperty(value = "名族",index = 9)
    private String nation;

    @ExcelProperty(value = "政治面貌",index = 10)
    private String politicsStatus;

    @ApiModelProperty(value = "入学时间")
    @DateTimeFormat(value = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ExcelProperty(value = "入学时间（格式：xxxx-xx-xx）",index = 11, converter = EasyExcelLocalDateConverter.class)
    private LocalDate enterTime;

    @ApiModelProperty(value = "毕业时间")
    @DateTimeFormat(value = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ExcelProperty(value = "毕业时间（格式：xxxx-xx-xx）",index = 12, converter = EasyExcelLocalDateConverter.class)
    private LocalDate graduateTime;

    @ExcelProperty(value = "身份证号",index = 13)
    private String idCard;

    @ExcelProperty(value = "户口类型",index = 14)
    private String hukou;

    @ExcelProperty(value = "QQ号",index = 15)
    private String qq;

    @ExcelProperty(value = "微信号",index = 16)
    private String weChat;

    @ExcelProperty(value = "籍贯",index = 17)
    private String nativePlace;

    @ExcelProperty(value = "家庭地址",index = 18)
    private String address;

    @ExcelProperty(value = "在校状态",index = 19)
    private String stateSchool;

    @ExcelProperty(value = "学生类型",index = 20)
    private String type;

    @ExcelProperty(value = "院系（全名）",index = 21)
    private String department;

    @ExcelProperty(value = "专业",index = 22)
    private String major;

    @ExcelProperty(value = "年级",index = 23)
    private String grade;

    @ExcelProperty(value = "班级",index = 24)
    private String clazz;

    @ExcelProperty(value = "学制",index = 25)
    private String educationalSystem;

    @ExcelProperty(value = "个人描述",index = 26)
    private String hobyDes;
}
