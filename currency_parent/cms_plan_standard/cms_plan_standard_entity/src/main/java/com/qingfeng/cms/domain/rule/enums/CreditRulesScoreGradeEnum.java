package com.qingfeng.cms.domain.rule.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qingfeng.cms.domain.level.enums.LevelCheckEnum;
import com.qingfeng.currency.base.BaseEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/11/16
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "CreditRulesScoreGradeEnum", description = "学分等级-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CreditRulesScoreGradeEnum implements BaseEnum {

    DEPARTMENT_LEVEL("院级"),
    SCHOOL_LEVEL("校级"),
    MUNICIPAL_LEVEL("市级"),
    PROVINCIAL_LEVEL("省级"),
    NATIONAL_LEVEL("国家级");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static LevelCheckEnum match(String val, LevelCheckEnum def) {
        for (LevelCheckEnum enm : LevelCheckEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static LevelCheckEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(LevelCheckEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码")
    public String getDesc() {
        return this.name();
    }
}
