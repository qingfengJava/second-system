package com.qingfeng.cms.domain.project.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qingfeng.currency.base.BaseEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/11/6
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "projectTypeEnum", description = "项目级别-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ProjectTypeEnum implements BaseEnum {

    SCHOOL_ITEMS("校级项目"),
    INSTITUTE_ITEMS("院级项目"),
    GENERAL_ITEMS("一般项目");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static ProjectTypeEnum match(String val, ProjectTypeEnum def) {
        for (ProjectTypeEnum enm : ProjectTypeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static ProjectTypeEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(ProjectTypeEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "SCHOOL_ITEMS,INSTITUTE_ITEMS,GENERAL_ITEMS", example = "SCHOOL_ITEMS")
    public String getDesc() {
        return this.name();
    }
}
