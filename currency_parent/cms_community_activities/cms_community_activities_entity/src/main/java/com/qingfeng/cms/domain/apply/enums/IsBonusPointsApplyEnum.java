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
@ApiModel(value = "IsBonusPointsApplyEnum", description = "是否需要主办方单独进行加分文件上传-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum IsBonusPointsApplyEnum implements BaseEnum{

    NOT("不需要"),
    IS("需要");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static IsBonusPointsApplyEnum match(String val, IsBonusPointsApplyEnum def) {
        for (IsBonusPointsApplyEnum enm : IsBonusPointsApplyEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static IsBonusPointsApplyEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(IsBonusPointsApplyEnum val) {
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
