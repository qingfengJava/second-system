package com.qingfeng.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "teacher_info")
public class TeacherInfo {
    /**
     * 学校领导详情信息表主键Id
     */
    @Id
    @Column(name = "teacher_info_id")
    private Integer teacherInfoId;

    /**
     * 出生日期
     */
    private Date birth;

    /**
     * 用户外键Id
     */
    private Integer uid;

    /**
     * 名族
     */
    private String nation;

    /**
     * 性别（1：男   2：女）
     */
    private Boolean sex;

    /**
     * 政治面貌
     */
    @Column(name = "politics_status")
    private String politicsStatus;

    /**
     * 所在学院
     */
    private String department;

    /**
     * 学历
     */
    private String education;

    /**
     * 职称（1：讲师  2：副教授  3：教授）
     */
    @Column(name = "professional_title")
    private Boolean professionalTitle;

    /**
     * 职务
     */
    private String duty;

    /**
     * 研究方向
     */
    @Column(name = "research_orientation")
    private String researchOrientation;

    /**
     * 通讯地址
     */
    @Column(name = "postal_address")
    private String postalAddress;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 手机
     */
    @Column(name = "cell_phone")
    private String cellPhone;

    /**
     * 个人简介
     */
    @Column(name = "individual_resume")
    private String individualResume;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否删除（0：未删除    1：已删除）
     */
    @Column(name = "is_delete")
    private Boolean isDelete;

    /**
     * 获取学校领导详情信息表主键Id
     *
     * @return teacher_info_id - 学校领导详情信息表主键Id
     */
    public Integer getTeacherInfoId() {
        return teacherInfoId;
    }

    /**
     * 设置学校领导详情信息表主键Id
     *
     * @param teacherInfoId 学校领导详情信息表主键Id
     */
    public void setTeacherInfoId(Integer teacherInfoId) {
        this.teacherInfoId = teacherInfoId;
    }

    /**
     * 获取出生日期
     *
     * @return birth - 出生日期
     */
    public Date getBirth() {
        return birth;
    }

    /**
     * 设置出生日期
     *
     * @param birth 出生日期
     */
    public void setBirth(Date birth) {
        this.birth = birth;
    }

    /**
     * 获取用户外键Id
     *
     * @return uid - 用户外键Id
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * 设置用户外键Id
     *
     * @param uid 用户外键Id
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * 获取名族
     *
     * @return nation - 名族
     */
    public String getNation() {
        return nation;
    }

    /**
     * 设置名族
     *
     * @param nation 名族
     */
    public void setNation(String nation) {
        this.nation = nation;
    }

    /**
     * 获取性别（1：男   2：女）
     *
     * @return sex - 性别（1：男   2：女）
     */
    public Boolean getSex() {
        return sex;
    }

    /**
     * 设置性别（1：男   2：女）
     *
     * @param sex 性别（1：男   2：女）
     */
    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    /**
     * 获取政治面貌
     *
     * @return politics_status - 政治面貌
     */
    public String getPoliticsStatus() {
        return politicsStatus;
    }

    /**
     * 设置政治面貌
     *
     * @param politicsStatus 政治面貌
     */
    public void setPoliticsStatus(String politicsStatus) {
        this.politicsStatus = politicsStatus;
    }

    /**
     * 获取所在学院
     *
     * @return department - 所在学院
     */
    public String getDepartment() {
        return department;
    }

    /**
     * 设置所在学院
     *
     * @param department 所在学院
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * 获取学历
     *
     * @return education - 学历
     */
    public String getEducation() {
        return education;
    }

    /**
     * 设置学历
     *
     * @param education 学历
     */
    public void setEducation(String education) {
        this.education = education;
    }

    /**
     * 获取职称（1：讲师  2：副教授  3：教授）
     *
     * @return professional_title - 职称（1：讲师  2：副教授  3：教授）
     */
    public Boolean getProfessionalTitle() {
        return professionalTitle;
    }

    /**
     * 设置职称（1：讲师  2：副教授  3：教授）
     *
     * @param professionalTitle 职称（1：讲师  2：副教授  3：教授）
     */
    public void setProfessionalTitle(Boolean professionalTitle) {
        this.professionalTitle = professionalTitle;
    }

    /**
     * 获取职务
     *
     * @return duty - 职务
     */
    public String getDuty() {
        return duty;
    }

    /**
     * 设置职务
     *
     * @param duty 职务
     */
    public void setDuty(String duty) {
        this.duty = duty;
    }

    /**
     * 获取研究方向
     *
     * @return research_orientation - 研究方向
     */
    public String getResearchOrientation() {
        return researchOrientation;
    }

    /**
     * 设置研究方向
     *
     * @param researchOrientation 研究方向
     */
    public void setResearchOrientation(String researchOrientation) {
        this.researchOrientation = researchOrientation;
    }

    /**
     * 获取通讯地址
     *
     * @return postal_address - 通讯地址
     */
    public String getPostalAddress() {
        return postalAddress;
    }

    /**
     * 设置通讯地址
     *
     * @param postalAddress 通讯地址
     */
    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取电话
     *
     * @return phone - 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话
     *
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取手机
     *
     * @return cell_phone - 手机
     */
    public String getCellPhone() {
        return cellPhone;
    }

    /**
     * 设置手机
     *
     * @param cellPhone 手机
     */
    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    /**
     * 获取个人简介
     *
     * @return individual_resume - 个人简介
     */
    public String getIndividualResume() {
        return individualResume;
    }

    /**
     * 设置个人简介
     *
     * @param individualResume 个人简介
     */
    public void setIndividualResume(String individualResume) {
        this.individualResume = individualResume;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
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

    /**
     * 获取是否删除（0：未删除    1：已删除）
     *
     * @return is_delete - 是否删除（0：未删除    1：已删除）
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除（0：未删除    1：已删除）
     *
     * @param isDelete 是否删除（0：未删除    1：已删除）
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}