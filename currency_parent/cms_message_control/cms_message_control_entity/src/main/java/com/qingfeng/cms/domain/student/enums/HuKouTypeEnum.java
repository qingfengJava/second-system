package com.qingfeng.cms.domain.student.enums;

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
 * @date 2022/12/24
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "HuKouTypeEnum", description = "户口类型-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum HuKouTypeEnum implements BaseEnum {

    COUNTRYSIDE("农村"),
    city("城市");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static HuKouTypeEnum match(String val, HuKouTypeEnum def) {
        for (HuKouTypeEnum enm : HuKouTypeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static HuKouTypeEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(HuKouTypeEnum val) {
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
