package com.qingfeng.cms.domain.sign.enums;

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
@ApiModel(value = "EvaluationStatusEnum", description = "评价状态-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EvaluationStatusEnum implements BaseEnum {

    INIT("待评价"),
    FINISH("已评价"),
    ABANDONMENT("超时废弃");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static EvaluationStatusEnum match(String val, EvaluationStatusEnum def) {
        for (EvaluationStatusEnum enm : EvaluationStatusEnum.values()) {
            if (enm.name().equalsIgnoreCase(val) || enm.desc.equals(val)) {
                return enm;
            }
        }
        return def;
    }

    public static EvaluationStatusEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(EvaluationStatusEnum val) {
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
