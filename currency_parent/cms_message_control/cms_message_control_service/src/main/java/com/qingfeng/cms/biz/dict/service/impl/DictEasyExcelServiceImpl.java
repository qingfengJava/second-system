package com.qingfeng.cms.biz.dict.service.impl;

import com.alibaba.excel.EasyExcel;
import com.qingfeng.cms.biz.dict.enums.DictServiceExceptionMsg;
import com.qingfeng.cms.biz.dict.listener.DictExcelListener;
import com.qingfeng.cms.biz.dict.service.DictEasyExcelService;
import com.qingfeng.cms.biz.dict.service.DictService;
import com.qingfeng.cms.domain.dict.vo.DictExcelVo;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.exception.code.ExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/11/24
 */
@Service
public class DictEasyExcelServiceImpl implements DictEasyExcelService {

    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private DictService dictService;

    /**
     * 导出数据字典Excel模板
     *
     * @param response
     */
    @Override
    public void exportTemplate(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            //设置URLEncoder.encode可以解决中文乱码问题   这个和EasyExcel没有关系
            response.setHeader("Content-Disposition",
                    "attachment;filename=" +
                            URLEncoder.encode("数据字典列表模板", "UTF-8") +
                            ".xlsx");

            // 调用方法实现写操作
            EasyExcel.write(response.getOutputStream(), DictExcelVo.class)
                    .sheet("数据字典列表")
                    .doWrite(Collections.emptyList());
        } catch (Exception e) {
            throw new BizException(ExceptionCode.OPERATION_EX.getCode(), DictServiceExceptionMsg.EXPORT_TEMPLATE_FAILD.getMsg());
        }
    }

    /**
     * 导出数据字典
     * @param response
     */
    @Override
    public void exportDict(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            //设置URLEncoder.encode可以解决中文乱码问题   这个和EasyExcel没有关系
            response.setHeader("Content-Disposition",
                    "attachment;filename=" +
                            URLEncoder.encode("数据字典列表", "UTF-8") +
                            ".xlsx");

            //查询dict列表
            List<DictExcelVo> dictExcelVos = dozerUtils.mapList(dictService.list(), DictExcelVo.class);

            // 调用方法实现写操作
            EasyExcel.write(response.getOutputStream(), DictExcelVo.class)
                    .sheet("数据字典列表")
                    .doWrite(dictExcelVos);
        } catch (Exception e) {
            throw new BizException(ExceptionCode.OPERATION_EX.getCode(), DictServiceExceptionMsg.EXPORT_FAILD.getMsg());
        }
    }

    /**
     * 导入数据字典Excel模板
     * @param file
     */
    @Override
    public void importDict(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), DictExcelVo.class, new DictExcelListener(dictService,dozerUtils)).sheet().doRead();
        } catch (IOException e) {
            throw new BizException(ExceptionCode.OPERATION_EX.getCode(), DictServiceExceptionMsg.IMPORT_FAILD.getMsg());
        }
    }
}
