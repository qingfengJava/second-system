package com.qingfeng.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2021/12/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageHelper<T> {

    /**
     * 总记录数
     */
    private int count;
    /**
     * 总页数
     */
    private int pageCount;
    /**
     * 分页数据
     */
    private List<T> list;
}
