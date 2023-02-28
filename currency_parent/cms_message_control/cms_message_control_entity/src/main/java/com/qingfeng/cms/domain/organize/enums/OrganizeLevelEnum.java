package com.qingfeng.cms.domain.organize.enums;

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
 * @date 2023/2/2
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "OrganizeLevelEnum", description = "社团组织级别-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OrganizeLevelEnum implements BaseEnum {

    SCHOOL_LEVEL("校级组织"),
    HOSPITAL_LEVEL("院级组织");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static OrganizeLevelEnum match(String val, OrganizeLevelEnum def) {
        for (OrganizeLevelEnum enm : OrganizeLevelEnum.values()) {
            if (enm.name().equalsIgnoreCase(val) || enm.desc.equals(val)) {
                return enm;
            }
        }
        return def;
    }

    public static OrganizeLevelEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(OrganizeLevelEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码")
    public String getDesc() {
        return this.desc;
    }
}
