package com.qingfeng.cms.domain.news.enums;

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
 * @date 2022/12/7
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "IsSeeEnum", description = "消息类型-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum IsSeeEnum implements BaseEnum {

    IS_VIEWED("已查看"),
    IS_NOT_VIEWED("未查看");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static IsSeeEnum match(String val, IsSeeEnum def) {
        for (IsSeeEnum enm : IsSeeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static IsSeeEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(IsSeeEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "IS_VIEWED,IS_NOT_VIEWED", example = "IS_NOT_VIEWED")
    public String getDesc() {
        return this.desc;
    }
}
