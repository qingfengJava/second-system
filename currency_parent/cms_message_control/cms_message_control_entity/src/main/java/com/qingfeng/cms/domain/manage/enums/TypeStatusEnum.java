package com.qingfeng.cms.domain.manage.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qingfeng.currency.base.BaseEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 任务状态枚举
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/30
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "TypeStatusEnum", description = "任务状态-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TypeStatusEnum implements BaseEnum {

    INIT("待处理"),
    PROCESS("处理中"),
    COMPLETED("已完成"),
    OBSOLETE("已废弃");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static TypeStatusEnum match(String val, TypeStatusEnum def) {
        for (TypeStatusEnum enm : TypeStatusEnum.values()) {
            if (enm.name().equalsIgnoreCase(val) || enm.desc.contains(val) || val.contains(enm.desc)) {
                return enm;
            }
        }
        return def;
    }

    public static TypeStatusEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(TypeStatusEnum val) {
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
