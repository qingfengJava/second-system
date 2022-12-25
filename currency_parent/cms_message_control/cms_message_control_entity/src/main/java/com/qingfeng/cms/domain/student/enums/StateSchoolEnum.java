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
@ApiModel(value = "StateSchoolEnum", description = "学生在校状态-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StateSchoolEnum implements BaseEnum {

    IN_SCHOOL("在籍在校"),
    LEAVING_SCHOOL("离校"),
    DROP_OUT_OF_SCHOOL("辍学");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static StateSchoolEnum match(String val, StateSchoolEnum def) {
        for (StateSchoolEnum enm : StateSchoolEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static StateSchoolEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(StateSchoolEnum val) {
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
