package com.qingfeng.cms.biz.apply.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qingfeng.cms.biz.apply.dao.ApplyCheckDao;
import com.qingfeng.cms.biz.apply.enums.ApplyExceptionMsg;
import com.qingfeng.cms.biz.apply.service.ApplyCheckService;
import com.qingfeng.cms.biz.apply.service.ApplyService;
import com.qingfeng.cms.biz.mq.service.producer.RabbitSendMsg;
import com.qingfeng.cms.domain.apply.dto.ApplyCheckSaveDTO;
import com.qingfeng.cms.domain.apply.entity.ApplyCheckEntity;
import com.qingfeng.cms.domain.apply.entity.ApplyEntity;
import com.qingfeng.cms.domain.news.dto.NewsNotifySaveDTO;
import com.qingfeng.cms.domain.news.enums.IsSeeEnum;
import com.qingfeng.cms.domain.news.enums.NewsTypeEnum;
import com.qingfeng.cms.domain.organize.entity.OrganizeInfoEntity;
import com.qingfeng.currency.authority.entity.auth.User;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.exception.code.ExceptionCode;
import com.qingfeng.sdk.auth.role.UserRoleApi;
import com.qingfeng.sdk.messagecontrol.news.NewsNotifyApi;
import com.qingfeng.sdk.messagecontrol.organize.OrganizeInfoApi;
import com.qingfeng.sdk.sms.email.domain.EmailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 活动审核表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-01-31 11:41:11
 */
@Service
public class ApplyCheckServiceImpl extends ServiceImpl<ApplyCheckDao, ApplyCheckEntity> implements ApplyCheckService {

    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private ApplyService applyService;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitSendMsg rabbitSendMsg;
    @Autowired
    private UserRoleApi userRoleApi;
    @Autowired
    private OrganizeInfoApi organizeInfoApi;
    @Autowired
    private NewsNotifyApi newsNotifyApi;

    /**
     * 提交活动终审资料
     *
     * @param applyCheckSaveDTO
     */
    @Override
    public void saveApplyCheck(ApplyCheckSaveDTO applyCheckSaveDTO) {
        ApplyCheckEntity applyCheckEntity = baseMapper.selectOne(Wraps.lbQ(new ApplyCheckEntity())
                .eq(ApplyCheckEntity::getApplyId, applyCheckSaveDTO.getApplyId()));
        if (ObjectUtil.isNotEmpty(applyCheckEntity)) {
            applyCheckEntity.setCheckStatus(applyCheckSaveDTO.getCheckStatus())
                    .setActiveCheckData(applyCheckSaveDTO.getActiveCheckData())
                    .setCheckContent(applyCheckSaveDTO.getCheckContent());
            baseMapper.updateById(applyCheckEntity);
        } else {
            // 提交终审资料，给社团联发送信息提醒审核
            baseMapper.insert(dozerUtils.map2(applyCheckSaveDTO, ApplyCheckEntity.class));
        }

        //查询活动信息
        ApplyEntity apply = applyService.getById(applyCheckSaveDTO.getApplyId());
        User user = userRoleApi.findRoleInfo().getData();
        OrganizeInfoEntity organizeInfoEntity = organizeInfoApi.info().getData();


        String title = "活动《" + apply.getActiveName() + "》终审待审核通知";
        String body = "亲爱的社团联负责人：\r\n       "
                + organizeInfoEntity.getOrganizeName()
                + "活动的《" + apply.getActiveName()
                + "》的终审资料已经提交，请尽早进行审核，以免影响活动！";


        if (StrUtil.isNotBlank(user.getEmail())) {
            //有邮箱就先向邮箱中发送消息   使用消息队列进行发送  失败重试三次
            try {
                rabbitSendMsg.sendEmail(objectMapper.writeValueAsString(EmailEntity.builder()
                        .email(user.getEmail())
                        .title(title)
                        .body(body)
                        .key("apply_active.email")
                        .build()), "apply_active.email");

                // 进行消息存储
                //将消息通知写入数据库
                R r = newsNotifyApi.save(NewsNotifySaveDTO.builder()
                        .userId(user.getId())
                        .newsType(NewsTypeEnum.MAILBOX)
                        .newsTitle(title)
                        .newsContent(body)
                        .isSee(IsSeeEnum.IS_NOT_VIEWED)
                        .build());

                if (r.getIsError()) {
                    throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), ApplyExceptionMsg.NEWS_SAVE_FAILED.getMsg());
                }
            } catch (JsonProcessingException e) {
                throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), ApplyExceptionMsg.ACTIVITY_APPLICATION_APPROVAL_EXCEPTION.getMsg());
            }

        } else {
            // TODO 短信发送
        }

    }
}