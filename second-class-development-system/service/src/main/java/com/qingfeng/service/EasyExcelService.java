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
}
