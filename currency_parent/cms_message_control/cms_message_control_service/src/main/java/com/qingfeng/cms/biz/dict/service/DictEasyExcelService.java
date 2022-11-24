package com.qingfeng.cms.biz.dict.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/11/24
 */
public interface DictEasyExcelService {

    /**
     * 导出数据字典Excel模板
     * @param response
     */
    void exportTemplate(HttpServletResponse response);

    /**
     * 导出数据字典
     * @param response
     */
    void exportDict(HttpServletResponse response);

    /**
     * 导入数据字典Excel模板
     * @param file
     */
    void importDict(MultipartFile file);
}
