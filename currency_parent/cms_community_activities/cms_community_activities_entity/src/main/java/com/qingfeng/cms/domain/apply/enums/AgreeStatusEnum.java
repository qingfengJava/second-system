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
@ApiModel(value = "AgreeStatusEnum", description = "活动申请状态-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AgreeStatusEnum implements BaseEnum {

    INIT("申请中"),
    IS_PASSED("已通过"),
    IS_NOT_PASSED("未通过");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static AgreeStatusEnum match(String val, AgreeStatusEnum def) {
        for (AgreeStatusEnum enm : AgreeStatusEnum.values()) {
            if (enm.name().equalsIgnoreCase(val) || enm.desc.equals(val)) {
                return enm;
            }
        }
        return def;
    }

    public static AgreeStatusEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(AgreeStatusEnum val) {
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
