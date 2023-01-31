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
@ApiModel(value = "ActiveTypeEnum", description = "活动类型-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ActiveTypeEnum implements BaseEnum {

    IDEOLOGY_MORALITY("思想政治与道德修养"),
    ACADEMIC_INNOVATION("学术科技与创新创业"),
    CULTURAL_EXCHANGES("文化沟通与交往能力"),
    COMMUNITY_WORK("社团活动与工作履历"),
    SOCIAL_VOLUNTARY("社会实践与志愿服务"),
    SKILLS_OTHERS("技能培训与其他相关");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static ActiveTypeEnum match(String val, ActiveTypeEnum def) {
        for (ActiveTypeEnum enm : ActiveTypeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val) || enm.desc.equals(val)) {
                return enm;
            }
        }
        return def;
    }

    public static ActiveTypeEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(ActiveTypeEnum val) {
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
