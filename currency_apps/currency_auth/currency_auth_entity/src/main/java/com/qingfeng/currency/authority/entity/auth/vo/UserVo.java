package com.qingfeng.currency.authority.entity.auth.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
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
@Accessors(chain = true)
@ToString(callSuper = true)
@Builder
@HeadRowHeight(25)
@ColumnWidth(30)
@ApiModel(value = "UserVo", description = "学生用户信息实体")
public class UserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账号(学号)")
    @ExcelProperty(value = "账号(学号)",index = 0)
    private String account;

    @ApiModelProperty(value = "姓名")
    @ExcelProperty(value = "姓名",index = 1)
    private String name;

    @ApiModelProperty(value = "邮箱")
    @ExcelProperty(value = "邮箱",index = 2)
    private String email;

    @ApiModelProperty(value = "手机")
    @ExcelProperty(value = "手机",index = 3)
    private String mobile;

    @ApiModelProperty(value = "性别 Sex{W:女;M:男;N:未知}")
    @ExcelProperty(value = "性别（男，女，未知）",index = 4)
    private String sex;

    @ApiModelProperty(value = "启用状态 1启用 0禁用")
    @ExcelProperty(value = "启用状态 1启用 0禁用",index = 5)
    private Boolean status;

    @ApiModelProperty(value = "密码，默认取学号后六位")
    @ExcelProperty(value = "密码，默认取学号后六位",index = 6)
    private String password;

    /**
     * 详情信息开始
     */
    @ApiModelProperty(value = "学号，防止账号变更")
    @ExcelProperty(value = "学号",index = 7)
    private String studentNum;

    @ApiModelProperty(value = "出生日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ExcelProperty(value = "出生日期（格式：xxxx-xx-xx）",index = 8)
    private LocalDate birth;

    @ApiModelProperty(value = "民族")
    @ExcelProperty(value = "名族",index = 9)
    private String nation;

    @ApiModelProperty(value = "政治面貌")
    @ExcelProperty(value = "政治面貌",index = 10)
    private String politicsStatus;

    @ApiModelProperty(value = "入学时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ExcelProperty(value = "入学时间（格式：xxxx-xx-xx）",index = 11)
    private LocalDate enterTime;

    @ApiModelProperty(value = "毕业时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ExcelProperty(value = "毕业时间（格式：xxxx-xx-xx）",index = 12)
    private LocalDate graduateTime;

    @ApiModelProperty(value = "身份证号")
    @ExcelProperty(value = "身份证号",index = 13)
    private String idCard;

    @ApiModelProperty(value = "户口类型")
    @ExcelProperty(value = "户口类型",index = 14)
    private String hukou;

    @ApiModelProperty(value = "QQ号")
    @ExcelProperty(value = "QQ号",index = 15)
    private String qq;

    @ApiModelProperty(value = "微信号")
    @ExcelProperty(value = "微信号",index = 16)
    private String weChat;

    @ApiModelProperty(value = "籍贯")
    @ExcelProperty(value = "籍贯",index = 17)
    private String nativePlace;

    @ApiModelProperty(value = "家庭地址")
    @ExcelProperty(value = "家庭地址",index = 18)
    private String address;

    @ApiModelProperty(value = "在校状态")
    @ExcelProperty(value = "在校状态",index = 19)
    private String stateSchool;

    @ApiModelProperty(value = "学生类型")
    @ExcelProperty(value = "学生类型",index = 20)
    private String type;

    @ApiModelProperty(value = "院系（根据学院查询组织和岗位）")
    @ExcelProperty(value = "院系（全名）",index = 21)
    private String department;

    @ApiModelProperty(value = "专业")
    @ExcelProperty(value = "专业",index = 22)
    private String major;

    @ApiModelProperty(value = "年级")
    @ExcelProperty(value = "年级",index = 23)
    private String grade;

    @ApiModelProperty(value = "班级")
    @ExcelProperty(value = "班级",index = 24)
    private String clazz;

    @ApiModelProperty(value = "学制")
    @ExcelProperty(value = "学制",index = 25)
    private String educationalSystem;

    @ApiModelProperty(value = "个人描述")
    @ExcelProperty(value = "个人描述",index = 26)
    private String hobyDes;

    /**
     * 封装一个当前操作人Id，用于进行消息通知
     */
    @ApiModelProperty(value = "当前操作人Id")
    @ExcelIgnore
    private Long userId;
}
