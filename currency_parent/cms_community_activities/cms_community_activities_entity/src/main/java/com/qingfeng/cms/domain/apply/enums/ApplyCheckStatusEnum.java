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
@ApiModel(value = "ApplyCheckStatusEnum", description = "活动审核状态-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ApplyCheckStatusEnum implements BaseEnum {

    INIT("待审核"),
    IS_PASSED("已通过"),
    IS_NOT_PASSED("未通过"),
    ABANDONMENT("超时废弃");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static ApplyCheckStatusEnum match(String val, ApplyCheckStatusEnum def) {
        for (ApplyCheckStatusEnum enm : ApplyCheckStatusEnum.values()) {
            if (enm.name().equalsIgnoreCase(val) || enm.desc.equals(val)) {
                return enm;
            }
        }
        return def;
    }

    public static ApplyCheckStatusEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(ApplyCheckStatusEnum val) {
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
