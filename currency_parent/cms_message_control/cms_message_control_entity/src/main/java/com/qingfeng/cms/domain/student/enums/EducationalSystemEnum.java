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
@ApiModel(value = "EducationalSystemEnum", description = "学制类型-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EducationalSystemEnum implements BaseEnum {

    TWO_YEAR_SYSTEM("两年制"),
    THREE_YEAR_SYSTEM("三年制"),
    FOUR_YEAR_SYSTEM("四年制"),
    FIVE_YEAR_SYSTEM("五年制");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static EducationalSystemEnum match(String val, EducationalSystemEnum def) {
        for (EducationalSystemEnum enm : EducationalSystemEnum.values()) {
            if (enm.name().equalsIgnoreCase(val) || enm.desc.equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static EducationalSystemEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(EducationalSystemEnum val) {
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
