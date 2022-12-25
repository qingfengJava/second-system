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
@ApiModel(value = "PoliticsStatusEnum", description = "政治面貌-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PoliticsStatusEnum implements BaseEnum {

    MASSES("群众"),
    COMMUNIST_YOUTH_LEAGUE_MEMBER("共青团员"),
    PROBATIONARY_PARTY_MEMBER("预备党员"),
    Member_Communist_Party_China("中国党员");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static PoliticsStatusEnum match(String val, PoliticsStatusEnum def) {
        for (PoliticsStatusEnum enm : PoliticsStatusEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static PoliticsStatusEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(PoliticsStatusEnum val) {
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
