package com.qingfeng.entity;

import javax.persistence.*;

public class Organize {
    /**
     * 社团组织部表id 
     */
    @Id
    @Column(name = "organize_id")
    private Integer organizeId;

    /**
     * 社团用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 社团名称
     */
    @Column(name = "organize_name")
    private String organizeName;

    /**
     * 社团所属部分
     */
    @Column(name = "organize_department")
    private String organizeDepartment;

    /**
     * 社团介绍
     */
    @Column(name = "organize_inroduce")
    private String organizeInroduce;

    /**
     * 社团照片
     */
    private String img;

    /**
     * 是否删除（0：未删除   1：已删除）
     */
    @Column(name = "is_delete")
    private Integer isDelete;

    /**
     * 获取社团组织部表id 
     *
     * @return organize_id - 社团组织部表id 
     */
    public Integer getOrganizeId() {
        return organizeId;
    }

    /**
     * 设置社团组织部表id 
     *
     * @param organizeId 社团组织部表id 
     */
    public void setOrganizeId(Integer organizeId) {
        this.organizeId = organizeId;
    }

    /**
     * 获取社团用户id
     *
     * @return user_id - 社团用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置社团用户id
     *
     * @param userId 社团用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取社团名称
     *
     * @return organize_name - 社团名称
     */
    public String getOrganizeName() {
        return organizeName;
    }

    /**
     * 设置社团名称
     *
     * @param organizeName 社团名称
     */
    public void setOrganizeName(String organizeName) {
        this.organizeName = organizeName;
    }

    /**
     * 获取社团所属部分
     *
     * @return organize_department - 社团所属部分
     */
    public String getOrganizeDepartment() {
        return organizeDepartment;
    }

    /**
     * 设置社团所属部分
     *
     * @param organizeDepartment 社团所属部分
     */
    public void setOrganizeDepartment(String organizeDepartment) {
        this.organizeDepartment = organizeDepartment;
    }

    /**
     * 获取社团介绍
     *
     * @return organize_inroduce - 社团介绍
     */
    public String getOrganizeInroduce() {
        return organizeInroduce;
    }

    /**
     * 设置社团介绍
     *
     * @param organizeInroduce 社团介绍
     */
    public void setOrganizeInroduce(String organizeInroduce) {
        this.organizeInroduce = organizeInroduce;
    }

    /**
     * 获取社团照片
     *
     * @return img - 社团照片
     */
    public String getImg() {
        return img;
    }

    /**
     * 设置社团照片
     *
     * @param img 社团照片
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * 获取是否删除（0：未删除   1：已删除）
     *
     * @return is_delete - 是否删除（0：未删除   1：已删除）
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除（0：未删除   1：已删除）
     *
     * @param isDelete 是否删除（0：未删除   1：已删除）
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}