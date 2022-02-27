package com.qingfeng.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "organize_img")
public class OrganizeImg {
    /**
     * 轮播图主键Id
     */
    @Id
    @Column(name = "img_id")
    private Integer imgId;

    /**
     * 外键，社团部门Id
     */
    @Column(name = "organize_id")
    private Integer organizeId;

    /**
     * 轮播图路径
     */
    @Column(name = "img_url")
    private String imgUrl;

    /**
     * 状态，是否删除
     */
    private Integer status;

    /**
     * 创建的时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 获取轮播图主键Id
     *
     * @return img_id - 轮播图主键Id
     */
    public Integer getImgId() {
        return imgId;
    }

    /**
     * 设置轮播图主键Id
     *
     * @param imgId 轮播图主键Id
     */
    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }

    /**
     * 获取外键，社团部门Id
     *
     * @return organize_id - 外键，社团部门Id
     */
    public Integer getOrganizeId() {
        return organizeId;
    }

    /**
     * 设置外键，社团部门Id
     *
     * @param organizeId 外键，社团部门Id
     */
    public void setOrganizeId(Integer organizeId) {
        this.organizeId = organizeId;
    }

    /**
     * 获取轮播图路径
     *
     * @return img_url - 轮播图路径
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * 设置轮播图路径
     *
     * @param imgUrl 轮播图路径
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * 获取状态，是否删除
     *
     * @return status - 状态，是否删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态，是否删除
     *
     * @param status 状态，是否删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取创建的时间
     *
     * @return create_time - 创建的时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建的时间
     *
     * @param createTime 创建的时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}