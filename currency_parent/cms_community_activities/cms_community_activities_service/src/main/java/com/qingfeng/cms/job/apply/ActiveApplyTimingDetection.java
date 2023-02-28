package com.qingfeng.cms.job.apply;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qingfeng.cms.biz.apply.dao.ApplyDao;
import com.qingfeng.cms.biz.apply.enums.ApplyExceptionMsg;
import com.qingfeng.cms.biz.apply.service.ApplyService;
import com.qingfeng.cms.biz.mq.service.producer.RabbitSendMsg;
import com.qingfeng.cms.domain.apply.entity.ApplyEntity;
import com.qingfeng.cms.domain.apply.enums.ActiveStatusEnum;
import com.qingfeng.cms.domain.apply.enums.AgreeStatusEnum;
import com.qingfeng.cms.domain.apply.enums.IsReleaseEnum;
import com.qingfeng.cms.domain.news.dto.NewsNotifySaveDTO;
import com.qingfeng.cms.domain.news.enums.IsSeeEnum;
import com.qingfeng.cms.domain.news.enums.NewsTypeEnum;
import com.qingfeng.cms.domain.organize.entity.OrganizeInfoEntity;
import com.qingfeng.currency.authority.entity.auth.User;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.exception.code.ExceptionCode;
import com.qingfeng.sdk.auth.role.UserRoleApi;
import com.qingfeng.sdk.auth.user.UserApi;
import com.qingfeng.sdk.messagecontrol.news.NewsNotifyApi;
import com.qingfeng.sdk.messagecontrol.organize.OrganizeInfoApi;
import com.qingfeng.sdk.sms.email.domain.EmailEntity;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 检测活动申请过程中的一些情况
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/2/18
 */
@Component
@Slf4j
public class ActiveApplyTimingDetection {

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
    @Autowired
    private UserApi userApi;
    @Autowired
    private ApplyDao applyDao;

    /**
     * 检测申请后一直未审核（给审核方发送消息）
     *
     * @return
     */
    @XxlJob(value = "expeditingReview")
    public ReturnT expeditingReview() {
        // 活动申请或者修改之后一直进行审核的活动，给审核方发送审核信息
        try {
            List<ApplyEntity> applyEntityList = applyService.list(Wraps.lbQ(new ApplyEntity())
                    .eq(ApplyEntity::getAgreeStatus, AgreeStatusEnum.INIT)
                    .eq(ApplyEntity::getIsRelease, IsReleaseEnum.INIT)
                    .ne(ApplyEntity::getActiveStatus, ActiveStatusEnum.ABANDONMENT)
                    .le(ApplyEntity::getUpdateTime, LocalDateTime.now().minusDays(2)));

            if (CollUtil.isNotEmpty(applyEntityList)) {
                //进行活动
                applyEntityList.stream()
                        .forEach(applyEntity -> CompletableFuture.runAsync(() -> {
                            User user = userRoleApi.findRoleInfo().getData();
                            OrganizeInfoEntity organizeInfoEntity = organizeInfoApi.info().getData();

                            String title = "";
                            String body = "";
                            if (ObjectUtil.isNotEmpty(applyEntity.getId())) {
                                title = "活动《" + applyEntity.getActiveName() + "》修改待审核通知";
                                body = "亲爱的社团联负责人：\r\n       "
                                        + organizeInfoEntity.getOrganizeName()
                                        + "申请的《" + applyEntity.getActiveName()
                                        + "》活动已经修改，并申请成功，请尽早进行审核，以免影响活动进度！";
                            } else {
                                title = "活动《" + applyEntity.getActiveName() + "》申请待审核通知";
                                body = "亲爱的社团联负责人：\r\n       "
                                        + organizeInfoEntity.getOrganizeName()
                                        + "申请的《" + applyEntity.getActiveName()
                                        + "》活动已经申请成功，请尽早进行审核，以免影响活动进度！";
                            }

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
                        }));
            }
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            return ReturnT.FAIL;
        }
    }

    /**
     * 检测活动审核之后超时2以上未处理，但未过期的活动，给申请方发送通知
     *
     * @return
     */
    @XxlJob(value = "remindApplicant")
    public ReturnT remindApplicant() {
        List<ApplyEntity> applyEntityList = applyService.list(Wraps.lbQ(new ApplyEntity())
                .eq(ApplyEntity::getAgreeStatus, AgreeStatusEnum.IS_NOT_PASSED)
                .eq(ApplyEntity::getIsRelease, IsReleaseEnum.INIT)
                .ne(ApplyEntity::getActiveStatus, ActiveStatusEnum.ABANDONMENT)
                .le(ApplyEntity::getUpdateTime, LocalDateTime.now().minusDays(2)));

        if (CollUtil.isNotEmpty(applyEntityList)) {
            applyEntityList.stream()
                    .forEach(applyEntity ->
                            CompletableFuture.runAsync(() -> {
                                User user = userApi.get(applyEntity.getApplyUserId()).getData();
                                OrganizeInfoEntity organizeInfoEntity = organizeInfoApi.info(user.getId()).getData();

                                String title = "活动《" + applyEntity.getActiveName() + "》申请审核通知";
                                String body = "亲爱的：\r\n       "
                                        + organizeInfoEntity.getOrganizeName()
                                        + "，您申请的《" + applyEntity.getActiveName()
                                        + "》活动【审核不通过】，请尽早修改，避免影响活动正常进行！";

                                if (StrUtil.isNotBlank(user.getEmail())) {
                                    //有邮箱就先向邮箱中发送消息   使用消息队列进行发送  失败重试三次
                                    try {
                                        rabbitSendMsg.sendEmail(objectMapper.writeValueAsString(EmailEntity.builder()
                                                .email(user.getEmail())
                                                .title(title)
                                                .body(body)
                                                .key("apply_active.check.email")
                                                .build()), "apply_active.check.email");

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
                            }));
        }

        return ReturnT.SUCCESS;
    }

    /**
     * 活动开始前，检测活动申请通过，用户并没有手动进行发布活动的情况，进行活动的一键发布
     *
     * @return
     */
    @XxlJob(value = "activeApplyRelease")
    public ReturnT activeApplyRelease() {
        // 查询出所有符合条件的活动   查询活动申请通过超过两天，并且活动还未开始的
        try {
            List<ApplyEntity> applyEntityList = applyService.list(Wraps.lbQ(new ApplyEntity())
                    .eq(ApplyEntity::getAgreeStatus, AgreeStatusEnum.IS_PASSED)
                    .eq(ApplyEntity::getIsRelease, IsReleaseEnum.INIT)
                    .ne(ApplyEntity::getActiveStatus, ActiveStatusEnum.ABANDONMENT)
                    .le(ApplyEntity::getUpdateTime, LocalDateTime.now().minusDays(2)));

            if (CollUtil.isNotEmpty(applyEntityList)) {
                //进行活动
                applyEntityList.forEach(a -> {
                    a.setIsRelease(IsReleaseEnum.FINISH);
                    a.setActiveStatus(ActiveStatusEnum.INIT);
                });
            }

            return ReturnT.SUCCESS;
        } catch (Exception e) {
            return ReturnT.FAIL;
        }
    }

    /**
     * 检测活动状态 主要是修改为进行中
     *
     * @return
     */
    @XxlJob(value = "modificationInProgress")
    public ReturnT modificationInProgress() {
        // 查询即将开始的活动，修改活动状态为进行中
        List<ApplyEntity> applyEntityList = applyService.list(Wraps.lbQ(new ApplyEntity())
                .eq(ApplyEntity::getAgreeStatus, AgreeStatusEnum.IS_PASSED)
                .eq(ApplyEntity::getIsRelease, IsReleaseEnum.FINISH)
                .ne(ApplyEntity::getActiveStatus, ActiveStatusEnum.INIT)
                .eq(ApplyEntity::getActiveStartTime, LocalDate.now()));
        // 将活动状态修改为进行中
        applyEntityList.forEach(a -> a.setActiveStatus(ActiveStatusEnum.HAVING));
        applyService.updateBatchById(applyEntityList);
        return ReturnT.SUCCESS;
    }

    /**
     * 检测待结束的活动
     *
     * @return
     */
    @XxlJob(value = "modificationEnded")
    public ReturnT modificationEnded() {
        // 查询进行中即将结束的活动，修改活动状态为进行中
        List<ApplyEntity> applyEntityList = applyService.list(Wraps.lbQ(new ApplyEntity())
                .eq(ApplyEntity::getAgreeStatus, AgreeStatusEnum.IS_PASSED)
                .eq(ApplyEntity::getIsRelease, IsReleaseEnum.FINISH)
                .ne(ApplyEntity::getActiveStatus, ActiveStatusEnum.HAVING)
                .eq(ApplyEntity::getActiveEndTime, LocalDate.now()));
        // 将活动状态修改为进行中
        applyEntityList.forEach(a -> a.setActiveStatus(ActiveStatusEnum.COMPLETE));
        applyService.updateBatchById(applyEntityList);
        return ReturnT.SUCCESS;
    }

    /**
     * 检测已过期废弃的活动
     * (申请一直未通过的活动，未处理，就过期。
     * 申请的活动超时未审核过期)
     *
     * @return
     */
    @XxlJob(value = "QueryObsolescence")
    public ReturnT queryObsolescence() {
        // 申请一直未通过的活动，未处理，就过期。 申请的活动超时未审核过期
        List<ApplyEntity> applyNotPassList = applyService.list(Wraps.lbQ(new ApplyEntity())
                .in(ApplyEntity::getAgreeStatus, Arrays.asList(
                        AgreeStatusEnum.INIT,
                        AgreeStatusEnum.IS_NOT_PASSED)
                )
                .eq(ApplyEntity::getIsRelease, IsReleaseEnum.INIT)
                .ge(ApplyEntity::getActiveStartTime, LocalDate.now()));

        // 修改活动状态为废弃
        applyNotPassList.forEach(a -> a.setActiveStatus(ActiveStatusEnum.ABANDONMENT));

        applyService.updateBatchById(applyNotPassList);

        return ReturnT.SUCCESS;
    }


}
