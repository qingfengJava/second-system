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
 * @date 2022/11/6
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "RuleCheckEnum", description = "学分审核-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RuleCheckEnum implements BaseEnum {

    INIT("待审核"),
    IS_FINISHED("已审核"),
    FAILED("未通过");

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
    @ApiModelProperty(value = "编码", allowableValues = "INIT,IS_FINISHED,FAILED", example = "INIT")
    public String getDesc() {
        return this.name();
    }
}
