package com.qingfeng.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/5/5
 */
public interface EasyExcelService {

    /**
     * 导出用户Excel文件
     * @param response
     * @return
     */
    void userExport(HttpServletResponse response);
}
