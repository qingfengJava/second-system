package com.qingfeng.cms.domain.apply.enums;

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
 * @date 2023/1/31
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ActiveScaleEnum", description = "活动规模-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ActiveScaleEnum implements BaseEnum {

    SMALL("小"),
    MIDDLE("中"),
    BIG("大");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static ActiveScaleEnum match(String val, ActiveScaleEnum def) {
        for (ActiveScaleEnum enm : ActiveScaleEnum.values()) {
            if (enm.name().equalsIgnoreCase(val) || enm.desc.equals(val)) {
                return enm;
            }
        }
        return def;
    }

    public static ActiveScaleEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(ActiveScaleEnum val) {
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
