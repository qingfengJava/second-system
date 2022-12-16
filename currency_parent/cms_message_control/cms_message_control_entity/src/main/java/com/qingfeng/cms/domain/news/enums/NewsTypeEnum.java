package com.qingfeng.cms.domain.news.enums;

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
 * @date 2022/12/7
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "NewsTypeEnum", description = "消息类型-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum NewsTypeEnum implements BaseEnum {

    MAILBOX("邮箱"),
    SHORT_MESSAGE("短信");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static NewsTypeEnum match(String val, NewsTypeEnum def) {
        for (NewsTypeEnum enm : NewsTypeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static NewsTypeEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(NewsTypeEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "MAILBOX,SHORT_MESSAGE", example = "SHORT_MESSAGE")
    public String getDesc() {
        return this.desc;
    }
}
