package com.qingfeng.currency.authority.biz.service.auth.listener;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qingfeng.currency.authority.biz.service.auth.UserService;
import com.qingfeng.currency.authority.biz.service.mq.producer.RabbitSendMsg;
import com.qingfeng.currency.authority.config.mq.RabbitMqConfig;
import com.qingfeng.currency.authority.entity.auth.User;
import com.qingfeng.currency.authority.entity.auth.vo.UserReadVo;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import lombok.SneakyThrows;

import java.util.Map;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/23
 */
public class UserVoExcelListener  extends AnalysisEventListener<UserReadVo> {

    private UserService userService;
    private RabbitSendMsg rabbitSendMsg;
    private ObjectMapper objectMapper;

    public UserVoExcelListener(UserService userService, RabbitSendMsg rabbitSendMsg,
                               ObjectMapper objectMapper) {
        this.userService = userService;
        this.rabbitSendMsg = rabbitSendMsg;
        this.objectMapper = objectMapper;
    }

    /**
     * 一行一行读取Excel内容，从第二行读取，第一行是表头不读取
     *
     * @param userReadVo
     * @param analysisContext
     */
    @SneakyThrows
    @Override
    public void invoke(UserReadVo userReadVo, AnalysisContext analysisContext) {
        //排除已经存在的用户
        User user = userService.getOne(Wraps.lbQ(new User())
                .eq(User::getAccount, userReadVo.getAccount()));
        System.out.println(userReadVo);
        if (ObjectUtil.isNotEmpty(user)){
            // 由于数据量可能会比较多的情况下，采用消息队列进行辅助处理
            rabbitSendMsg.sendEmail(objectMapper.writeValueAsString(userReadVo), RabbitMqConfig.ROUTINGKEY_USER_INFO);
        }

    }


    /**
     * 读取Excel表头第一行的数据
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {

    }

    /**
     * 读取Excel结束后调用
     *
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
