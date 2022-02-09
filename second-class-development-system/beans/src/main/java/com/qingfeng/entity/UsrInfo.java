package com.qingfeng.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "usr_info")
public class UsrInfo {
    /**
     * 主键，无实意
     */
    @Id
    private Integer bid;

    /**
     * 学生表的外键，关联学生表信息
     */
    private Integer uid;

    /**
     * 学号
     */
    @Column(name = "student_num")
    private String studentNum;

    /**
     * 姓名
     */
    @Column(name = "stu_name")
    private String stuName;

    /**
     * 性别    0-男  1-女
     */
    @Column(name = "stu_sex")
    private Boolean stuSex;

    /**
     * 出生日期
     */
    private Date birth;

    /**
     * 民族
     */
    private String nation;

    /**
     * 政治面貌
     */
    @Column(name = "politics_status")
    private String politicsStatus;

    /**
     * 入学时间
     */
    @Column(name = "enter_time")
    private Date enterTime;

    /**
     * 毕业时间
     */
    @Column(name = "graduate_time")
    private Date graduateTime;

    /**
     * 身份证号
     */
    @Column(name = "id_card")
    private String idCard;

    /**
     * 户口类型   0-农村   1-城市
     */
    private Boolean hukou;

    /**
     * 电话号码
     */
    private String telphone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * QQ号
     */
    private String qq;

    /**
     * 微信号
     */
    @Column(name = "we_chat")
    private String weChat;

    /**
     * 籍贯
     */
    @Column(name = "native_place")
    private String nativePlace;

    /**
     * 家庭地址
     */
    private String address;

    /**
     * 在校状态
     */
    @Column(name = "state_school")
    private String stateSchool;

    /**
     * 院系
     */
    private String department;

    /**
     * 专业
     */
    private String major;

    /**
     * 年级
     */
    private String grade;

    /**
     * 班级
     */
    private String clazz;

    /**
     * 兴趣描述
     */
    @Column(name = "hoby_des")
    private String hobyDes;

    /**
     * 是否可以修改 0-不可以修改  1-可以修改
     */
    @Column(name = "is_change")
    private Boolean isChange;

    /**
     * 获取主键，无实意
     *
     * @return bid - 主键，无实意
     */
    public Integer getBid() {
        return bid;
    }

    /**
     * 设置主键，无实意
     *
     * @param bid 主键，无实意
     */
    public void setBid(Integer bid) {
        this.bid = bid;
    }

    /**
     * 获取学生表的外键，关联学生表信息
     *
     * @return uid - 学生表的外键，关联学生表信息
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * 设置学生表的外键，关联学生表信息
     *
     * @param uid 学生表的外键，关联学生表信息
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * 获取学号
     *
     * @return student_num - 学号
     */
    public String getStudentNum() {
        return studentNum;
    }

    /**
     * 设置学号
     *
     * @param studentNum 学号
     */
    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    /**
     * 获取姓名
     *
     * @return stu_name - 姓名
     */
    public String getStuName() {
        return stuName;
    }

    /**
     * 设置姓名
     *
     * @param stuName 姓名
     */
    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    /**
     * 获取性别    0-男  1-女
     *
     * @return stu_sex - 性别    0-男  1-女
     */
    public Boolean getStuSex() {
        return stuSex;
    }

    /**
     * 设置性别    0-男  1-女
     *
     * @param stuSex 性别    0-男  1-女
     */
    public void setStuSex(Boolean stuSex) {
        this.stuSex = stuSex;
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
     * 获取民族
     *
     * @return nation - 民族
     */
    public String getNation() {
        return nation;
    }

    /**
     * 设置民族
     *
     * @param nation 民族
     */
    public void setNation(String nation) {
        this.nation = nation;
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
     * 获取入学时间
     *
     * @return enter_time - 入学时间
     */
    public Date getEnterTime() {
        return enterTime;
    }

    /**
     * 设置入学时间
     *
     * @param enterTime 入学时间
     */
    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    /**
     * 获取毕业时间
     *
     * @return graduate_time - 毕业时间
     */
    public Date getGraduateTime() {
        return graduateTime;
    }

    /**
     * 设置毕业时间
     *
     * @param graduateTime 毕业时间
     */
    public void setGraduateTime(Date graduateTime) {
        this.graduateTime = graduateTime;
    }

    /**
     * 获取身份证号
     *
     * @return id_card - 身份证号
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * 设置身份证号
     *
     * @param idCard 身份证号
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * 获取户口类型   0-农村   1-城市
     *
     * @return hukou - 户口类型   0-农村   1-城市
     */
    public Boolean getHukou() {
        return hukou;
    }

    /**
     * 设置户口类型   0-农村   1-城市
     *
     * @param hukou 户口类型   0-农村   1-城市
     */
    public void setHukou(Boolean hukou) {
        this.hukou = hukou;
    }

    /**
     * 获取电话号码
     *
     * @return telphone - 电话号码
     */
    public String getTelphone() {
        return telphone;
    }

    /**
     * 设置电话号码
     *
     * @param telphone 电话号码
     */
    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    /**
     * 获取电子邮箱
     *
     * @return email - 电子邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置电子邮箱
     *
     * @param email 电子邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取QQ号
     *
     * @return qq - QQ号
     */
    public String getQq() {
        return qq;
    }

    /**
     * 设置QQ号
     *
     * @param qq QQ号
     */
    public void setQq(String qq) {
        this.qq = qq;
    }

    /**
     * 获取微信号
     *
     * @return we_chat - 微信号
     */
    public String getWeChat() {
        return weChat;
    }

    /**
     * 设置微信号
     *
     * @param weChat 微信号
     */
    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    /**
     * 获取籍贯
     *
     * @return native_place - 籍贯
     */
    public String getNativePlace() {
        return nativePlace;
    }

    /**
     * 设置籍贯
     *
     * @param nativePlace 籍贯
     */
    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    /**
     * 获取家庭地址
     *
     * @return address - 家庭地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置家庭地址
     *
     * @param address 家庭地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取在校状态
     *
     * @return state_school - 在校状态
     */
    public String getStateSchool() {
        return stateSchool;
    }

    /**
     * 设置在校状态
     *
     * @param stateSchool 在校状态
     */
    public void setStateSchool(String stateSchool) {
        this.stateSchool = stateSchool;
    }

    /**
     * 获取院系
     *
     * @return department - 院系
     */
    public String getDepartment() {
        return department;
    }

    /**
     * 设置院系
     *
     * @param department 院系
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * 获取专业
     *
     * @return major - 专业
     */
    public String getMajor() {
        return major;
    }

    /**
     * 设置专业
     *
     * @param major 专业
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * 获取年级
     *
     * @return grade - 年级
     */
    public String getGrade() {
        return grade;
    }

    /**
     * 设置年级
     *
     * @param grade 年级
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * 获取班级
     *
     * @return class - 班级
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * 设置班级
     *
     * @param clazz 班级
     */
    public void setClass(String clazz) {
        this.clazz = clazz;
    }

    /**
     * 获取兴趣描述
     *
     * @return hoby_des - 兴趣描述
     */
    public String getHobyDes() {
        return hobyDes;
    }

    /**
     * 设置兴趣描述
     *
     * @param hobyDes 兴趣描述
     */
    public void setHobyDes(String hobyDes) {
        this.hobyDes = hobyDes;
    }

    /**
     * 获取是否可以修改 0-不可以修改  1-可以修改
     *
     * @return is_change - 是否可以修改 0-不可以修改  1-可以修改
     */
    public Boolean getIsChange() {
        return isChange;
    }

    /**
     * 设置是否可以修改 0-不可以修改  1-可以修改
     *
     * @param isChange 是否可以修改 0-不可以修改  1-可以修改
     */
    public void setIsChange(Boolean isChange) {
        this.isChange = isChange;
    }
}