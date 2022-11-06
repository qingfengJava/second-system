package com.qingfeng.cms.domain.project.enums;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/11/6
 */
public enum ProjectTypeEnum {

    SCHOOL_ITEMS("校级项目"),
    INSTITUTE_ITEMS("院级项目"),
    GENERAL_ITEMS("一般项目");

    private String projectType;

    ProjectTypeEnum(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }
}
