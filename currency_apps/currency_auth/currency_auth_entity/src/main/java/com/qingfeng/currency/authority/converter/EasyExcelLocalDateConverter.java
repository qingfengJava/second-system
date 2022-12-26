package com.qingfeng.currency.authority.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.qingfeng.currency.exception.BizException;
import org.apache.poi.ss.usermodel.DateUtil;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 自定义Excel支持LocalDate类型日期的转换器
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/26
 */
public class EasyExcelLocalDateConverter implements Converter<LocalDate> {

    @Override
    public Class<LocalDate> supportJavaTypeKey() {
        return LocalDate.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDate convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
            if(null==cellData) {
                return null;
            }
            LocalDate result=null;
            if(cellData.getType()==CellDataTypeEnum.NUMBER) {
                if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
                    Date date= DateUtil.getJavaDate(cellData.getNumberValue().doubleValue(),
                            globalConfiguration.getUse1904windowing(), null);
                    result =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                } else {
                    Date date=  DateUtil.getJavaDate(cellData.getNumberValue().doubleValue(),
                            contentProperty.getDateTimeFormatProperty().getUse1904windowing(), null);
                    result =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                }
            }
            if(cellData.getType()==CellDataTypeEnum.STRING) {
                String value=cellData.getStringValue();
                if(value.contains("-")) {
                    try {
                        result=  LocalDate.parse(cellData.getStringValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(value.contains("/")) {
                    try {
                        result=  LocalDate.parse(cellData.getStringValue(), DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(null==result) {
                    throw new BizException("日期格式错误，日期格式只支持 yyyy-MM-dd 和 yyyy/MM/dd");
                }
            }

        return result;
    }

    @Override
    public CellData<LocalDate> convertToExcelData(LocalDate value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData<>(value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}

