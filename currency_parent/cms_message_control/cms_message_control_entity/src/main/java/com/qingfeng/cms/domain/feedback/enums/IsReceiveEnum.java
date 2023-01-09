package com.qingfeng.cms.domain.feedback.enums;

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
 * @date 2023/1/9
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "IsReceiveEnum", description = "是否回复-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum IsReceiveEnum  implements BaseEnum {

    INIT("待回复"),
    COMPLETE("已回复");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static IsReceiveEnum match(String val, IsReceiveEnum def) {
        for (IsReceiveEnum enm : IsReceiveEnum.values()) {
            if (enm.name().equalsIgnoreCase(val) || enm.desc.contains(val) || val.contains(enm.desc)) {
                return enm;
            }
        }
        return def;
    }

    public static IsReceiveEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(IsReceiveEnum val) {
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
