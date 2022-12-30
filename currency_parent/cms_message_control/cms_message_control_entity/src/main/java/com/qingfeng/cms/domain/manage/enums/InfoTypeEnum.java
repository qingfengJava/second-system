package com.qingfeng.cms.domain.manage.enums;

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
 * @date 2022/12/30
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "DictParentEnum", description = "数据字典一级类别-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum InfoTypeEnum  implements BaseEnum {

    STUDENT("学生"),
    TEACHER("教师"),
    MANAGER("管理员");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static InfoTypeEnum match(String val, InfoTypeEnum def) {
        for (InfoTypeEnum enm : InfoTypeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val) || enm.desc.contains(val) || val.contains(enm.desc)) {
                return enm;
            }
        }
        return def;
    }

    public static InfoTypeEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(InfoTypeEnum val) {
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
