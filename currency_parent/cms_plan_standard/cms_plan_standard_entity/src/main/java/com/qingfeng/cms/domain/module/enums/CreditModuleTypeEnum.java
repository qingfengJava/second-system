package com.qingfeng.cms.domain.module.enums;

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
 * @date 2023/3/2
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "CreditModuleEnum", description = "模块类型-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CreditModuleTypeEnum  implements BaseEnum {

    IDEOLOGICAL_MORAL("思想政治与道德修养类"),
    ACADEMIC_INNOVATION("学术科技与创新创业类"),
    CULTURAL_COMMUNICATION("文化沟通与交往能力类"),
    COMMUNITY_WORK("社团活动与工作履历类"),
    SOCIAL_VOLUNTARY("社会实践与志愿服务类"),
    SKILLS_OTHER("技能培训与其他相关类");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static CreditModuleTypeEnum match(String val, CreditModuleTypeEnum def) {
        for (CreditModuleTypeEnum enm : CreditModuleTypeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static CreditModuleTypeEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(CreditModuleTypeEnum val) {
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
