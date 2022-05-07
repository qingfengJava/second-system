package com.qingfeng.entity;

import javax.persistence.*;

public class Nation {
    /**
     * 名族的主键Id
     */
    @Id
    private Integer id;

    /**
     * 民族的名称
     */
    @Column(name = "nation_name")
    private String nationName;

    /**
     * 获取名族的主键Id
     *
     * @return id - 名族的主键Id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置名族的主键Id
     *
     * @param id 名族的主键Id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取民族的名称
     *
     * @return nation_name - 民族的名称
     */
    public String getNationName() {
        return nationName;
    }

    /**
     * 设置民族的名称
     *
     * @param nationName 民族的名称
     */
    public void setNationName(String nationName) {
        this.nationName = nationName;
    }
}