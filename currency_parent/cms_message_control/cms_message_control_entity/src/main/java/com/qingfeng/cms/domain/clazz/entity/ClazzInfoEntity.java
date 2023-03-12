package com.qingfeng.cms.domain.clazz.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingfeng.cms.domain.dict.enums.DictDepartmentEnum;
import com.qingfeng.cms.domain.student.enums.EducationalSystemEnum;
import com.qingfeng.currency.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 班级信息
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-12 22:37:25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "ClazzInfoEntity", description = "班级信息")
@TableName("mc_clazz_info")
public class ClazzInfoEntity extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户表外键，关联用户基础信息")
    private Long userId;

    @ApiModelProperty(value = "院系信息，枚举值")
    private DictDepartmentEnum department;

    @ApiModelProperty(value = "专业信息，枚举值")
    private String major;

    @ApiModelProperty(value = "班级信息")
    private String clazz;

    @ApiModelProperty(value = "年级")
    private String grade;

    @ApiModelProperty(value = "学制")
    private EducationalSystemEnum educationalSystem;

    @ApiModelProperty(value = "班长名")
    private String clazzMonitor;

    @ApiModelProperty(value = "辅导员名")
    private String assistant;

    @ApiModelProperty(value = "班级描述")
    private String describe;

    @Builder
    public ClazzInfoEntity(Long id, LocalDateTime createTime, Long createUser,
                           LocalDateTime updateTime, Long updateUser, Long userId,
                           DictDepartmentEnum department, String major, String clazz, String grade,
                           EducationalSystemEnum educationalSystem, String clazzMonitor,
                           String assistant, String describe) {
        super(id, createTime, createUser, updateTime, updateUser);
        this.userId = userId;
        this.department = department;
        this.major = major;
        this.clazz = clazz;
        this.grade = grade;
        this.educationalSystem = educationalSystem;
        this.clazzMonitor = clazzMonitor;
        this.assistant = assistant;
        this.describe = describe;
    }
}
