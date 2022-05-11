package com.qingfeng.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/5/5
 */
public interface EasyExcelService {

    /**
     * 导出用户信息列表Excel文件
     * @param response
     * @return
     */
    void userExport(HttpServletResponse response);

    /**
     * 导入用户信息列表Excel文件
     * @param file
     * @return
     */
    void userImport(MultipartFile file);

    /**
     * 社团信息列表导出
     * @param response
     */
    void clubExport(HttpServletResponse response);

    /**
     * 社团信息列表导入
     * @param file
     */
    void clubImport(MultipartFile file);

    /**
     * 学生学籍信息批量导出
     * @param response
     */
    void stuInfoExport(HttpServletResponse response);

    /**
     * 学生学籍信息批量导入
     * @param file
     */
    void stuInfoImport(MultipartFile file);

    /**
     * 校领导/老师信息批量导出
     * @param response
     */
    void leaderExport(HttpServletResponse response);

    /**
     * 校领导/老师信息批量导入
     * @param file
     */
    void leaderImport(MultipartFile file);
}
