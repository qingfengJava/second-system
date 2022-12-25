package com.qingfeng.cms.domain.dict.enums;

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
 * @date 2022/11/30
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "DictDepartmentEnum", description = "院系-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DictDepartmentEnum  implements BaseEnum {

    PZHU("攀枝花学院"),
    SJ("数学与计算机学院（大数据学院）"),
    ZNZZ("智能制造学院"),
    JG("经济与管理学院");
    // TODO 学院信息待完善

    @ApiModelProperty(value = "描述")
    private String desc;

    public static DictDepartmentEnum match(String val, DictDepartmentEnum def) {
        for (DictDepartmentEnum enm : DictDepartmentEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static DictDepartmentEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(DictDepartmentEnum val) {
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
