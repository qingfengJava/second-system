package com.qingfeng.currency.authority.config.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKeyBuild;
import com.alibaba.excel.converters.DefaultConverterLoader;
import com.qingfeng.currency.converter.EasyExcelLocalDateConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 注册自定义的excel转换器
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/26
 */
@Configuration
public class CustomConverterLoader {

    @Bean
    public DefaultConverterLoader defaultConverterLoader() {
        DefaultConverterLoader converters = new DefaultConverterLoader();
        EasyExcelLocalDateConverter localDateConverter = new EasyExcelLocalDateConverter();
        Map<String, Converter> defaultWriteConverter = converters.loadDefaultWriteConverter();
        defaultWriteConverter.put(ConverterKeyBuild.buildKey(localDateConverter.supportJavaTypeKey()), localDateConverter);
        Map<String, Converter> allConverter = converters.loadAllConverter();
        allConverter.put(ConverterKeyBuild.buildKey(localDateConverter.supportJavaTypeKey(), localDateConverter.supportExcelTypeKey()), localDateConverter);
        return converters;
    }
}

