package com.qingfeng.cms.domain.check.enums;

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
 * @date 2023/3/7
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "checkStatusEnums", description = "项目加分审核状态-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CheckStatusEnums implements BaseEnum {

    INIT("待审核"),
    COMPLETE("审核通过"),
    FAIL("审核不通过");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static CheckStatusEnums match(String val, CheckStatusEnums def) {
        for (CheckStatusEnums enm : CheckStatusEnums.values()) {
            if (enm.name().equalsIgnoreCase(val) || enm.desc.equals(val)) {
                return enm;
            }
        }
        return def;
    }

    public static CheckStatusEnums get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(CheckStatusEnums val) {
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
