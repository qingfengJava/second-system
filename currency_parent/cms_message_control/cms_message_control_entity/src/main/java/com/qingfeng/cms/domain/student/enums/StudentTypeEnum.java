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
@ApiModel(value = "StudentTypeEnum", description = "学生类型-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StudentTypeEnum implements BaseEnum {

    SPECIALTY("专科"),
    UNDERGRADUATE_FOR_FOUR_YEARS("本科四年"),
    UNDERGRADUATE_FOR_FIVE_YEARS("本科五年"),
    GRADUATE_STUDENT("研究生");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static StudentTypeEnum match(String val, StudentTypeEnum def) {
        for (StudentTypeEnum enm : StudentTypeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static StudentTypeEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(StudentTypeEnum val) {
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
