package com.qingfeng.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 活动质量实体类
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/5/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ActiveQuality {

    /**
     * 优秀
     */
    private Integer excellent;

    /**
     * 良好
     */
    private Integer good;

    /**
     * 差
     */
    private Integer poor;
}
