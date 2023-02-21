package com.qingfeng.cms.biz.apply.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qingfeng.cms.biz.apply.dao.ApplyDao;
import com.qingfeng.cms.biz.apply.enums.ApplyExceptionMsg;
import com.qingfeng.cms.biz.apply.service.ApplyService;
import com.qingfeng.cms.biz.mq.service.producer.RabbitSendMsg;
import com.qingfeng.cms.domain.apply.dto.ApplyQueryDTO;
import com.qingfeng.cms.domain.apply.dto.ApplySaveDTO;
import com.qingfeng.cms.domain.apply.dto.ApplyUpdateDTO;
import com.qingfeng.cms.domain.apply.entity.ApplyEntity;
import com.qingfeng.cms.domain.apply.enums.ActiveStatusEnum;
import com.qingfeng.cms.domain.apply.enums.ActiveTypeEnum;
import com.qingfeng.cms.domain.apply.enums.AgreeStatusEnum;
import com.qingfeng.cms.domain.apply.enums.IsReleaseEnum;
import com.qingfeng.cms.domain.apply.ro.ActiveApplyCheckRo;
import com.qingfeng.cms.domain.apply.ro.ActiveReleaseRo;
import com.qingfeng.cms.domain.apply.vo.ApplyListVo;
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
import com.qingfeng.sdk.auth.user.UserApi;
import com.qingfeng.sdk.messagecontrol.news.NewsNotifyApi;
import com.qingfeng.sdk.messagecontrol.organize.OrganizeInfoApi;
import com.qingfeng.sdk.oss.file.FileOssApi;
import com.qingfeng.sdk.sms.email.domain.EmailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * 社团活动申请表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-01-31 11:41:11
 */
@Service
public class ApplyServiceImpl extends ServiceImpl<ApplyDao, ApplyEntity> implements ApplyService {

    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private ApplyDao applyDao;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FileOssApi fileOssApi;

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

    /**
     * 活动申请信息保存
     *
     * @param applySaveDTO
     * @param userId
     */
    @Override
    public void saveApply(ApplySaveDTO applySaveDTO, Long userId) {
        //同一学期，同一活动不能重复申请
        List<ApplyEntity> applyEntityList = getApplyEntities(applySaveDTO.getActiveName(), applySaveDTO.getSchoolYear());

        if (CollUtil.isEmpty(applyEntityList)) {
            //说明没有重复的活动
            ApplyEntity applyEntity = dozerUtils.map2(applySaveDTO, ApplyEntity.class);

            //校验日期的合法性
            inspectionTime(applyEntity);

            applyEntity.setApplyUserId(userId)
                    .setActiveType(ActiveTypeEnum.COMMUNITY_WORK)
                    .setAgreeStatus(AgreeStatusEnum.INIT)
                    .setIsRelease(IsReleaseEnum.INIT)
                    .setActiveApplyTime(LocalDateTime.now());
            baseMapper.insert(applyEntity);

            // 活动申请成功要通知社团联负责人进行活动信息的审核(短信或者邮箱)
            sendApplySuccess(applyEntity);

        } else {
            //抛出活动重复的异常
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), ApplyExceptionMsg.REPETITION_OF_CLASSMATE_ACTIVITIES.getMsg());
        }
    }

    /**
     * 活动申请信息修改
     *
     * @param applyUpdateDTO
     */
    @Override
    public void updateApplyById(ApplyUpdateDTO applyUpdateDTO) {
        //同一学期，同一活动不能重复申请
        List<ApplyEntity> applyEntityList = getApplyEntities(applyUpdateDTO.getActiveName(), applyUpdateDTO.getSchoolYear());

        if (CollUtil.isEmpty(applyEntityList)) {
            //说明没有重复的活动
            ApplyEntity applyEntity = dozerUtils.map2(applyUpdateDTO, ApplyEntity.class);

            //校验日期的合法性
            inspectionTime(applyEntity);

            applyEntity.setActiveType(ActiveTypeEnum.COMMUNITY_WORK)
                    .setAgreeStatus(AgreeStatusEnum.INIT)
                    .setActiveStatus(ActiveStatusEnum.INIT)
                    .setIsRelease(IsReleaseEnum.INIT);
            baseMapper.updateById(applyEntity);

            // 活动申请修改成功要通知社团联负责人进行活动信息的审核(短信或者邮箱)
            sendApplySuccess(applyEntity);
        } else {
            //抛出活动重复的异常
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), ApplyExceptionMsg.REPETITION_OF_CLASSMATE_ACTIVITIES.getMsg());
        }
    }

    private void sendApplySuccess(ApplyEntity applyEntity) {
        User user = userRoleApi.findRoleInfo().getData();
        OrganizeInfoEntity organizeInfoEntity = organizeInfoApi.info().getData();

        String title = "活动《" + applyEntity.getActiveName() + "》申请待审核通知";
        String body = "亲爱的社团联负责人：\r\n       "
                + organizeInfoEntity.getOrganizeName()
                + "申请的《" + applyEntity.getActiveName()
                + "》活动已经申请成功，请尽早进行审核，以免影响活动进度！";

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

    /**
     * 活动申请分页条件查询
     *
     * @param applyQueryDTO
     */
    @Override
    public ApplyListVo findApplyList(ApplyQueryDTO applyQueryDTO, Long userId) {
        Integer pageNo = applyQueryDTO.getPageNo();
        Integer pageSize = applyQueryDTO.getPageSize();
        applyQueryDTO.setPageNo((pageNo - 1) * pageSize);
        //查询总记录数
        Integer total = baseMapper.selectCount(Wraps.lbQ(new ApplyEntity())
                .eq(ApplyEntity::getApplyUserId, userId));

        if (total == 0) {
            return ApplyListVo.builder()
                    .total(0)
                    .applyEntityList(Collections.emptyList())
                    .pageNo(pageNo)
                    .pageSize(pageSize)
                    .build();
        }

        //分页查询活动申请列表
        List<ApplyEntity> applyEntityList = applyDao.findList(applyQueryDTO, userId);

        return ApplyListVo.builder()
                .total(total)
                .applyEntityList(applyEntityList)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .build();
    }

    /**
     * 根据Id删除申请的活动信息
     *
     * @param id
     */
    @Override
    public void removeActiveById(Long id) {
        //删除信息前要将申请的资料信息删除
        ApplyEntity applyEntity = baseMapper.selectById(id);
        fileOssApi.fileDelete(applyEntity.getApplyDataLink());
        baseMapper.deleteById(id);
    }

    /**
     * 活动申请信息审核
     *
     * @param activeApplyCheckRo
     * @param userId
     */
    @Override
    public void activeApplyCheck(ActiveApplyCheckRo activeApplyCheckRo, Long userId) {
        //更新活动信息状态
        try {
            ApplyEntity entity = dozerUtils.map2(activeApplyCheckRo, ApplyEntity.class);
            //设置活动状态
            baseMapper.updateById(entity);

            ApplyEntity applyEntity = baseMapper.selectById(entity.getId());
            User user = userApi.get(applyEntity.getApplyUserId()).getData();
            OrganizeInfoEntity organizeInfoEntity = organizeInfoApi.info(user.getId()).getData();

            String title = "活动《" + applyEntity.getActiveName() + "》申请审核通知";
            String body = "亲爱的：\r\n       "
                    + organizeInfoEntity.getOrganizeName()
                    + "，您申请的《" + applyEntity.getActiveName()
                    + "》活动已经审核成功，请尽早发布，避免影响活动正常进行！";

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

        } catch (Exception e) {
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), ApplyExceptionMsg.ACTIVITY_APPLICATION_APPROVAL_EXCEPTION.getMsg());
        }

    }

    /**
     * 进行活动的一键发布
     *
     * @param activeReleaseRo
     */
    @Override
    public void activeRelease(ActiveReleaseRo activeReleaseRo) {
        ApplyEntity applyEntity = baseMapper.selectById(activeReleaseRo.getId());
        applyEntity.setActiveStatus(ActiveStatusEnum.INIT)
                .setIsRelease(IsReleaseEnum.FINISH)
                .setActiveContent(activeReleaseRo.getActiveContent());
        baseMapper.updateById(applyEntity);
    }

    private void inspectionTime(ApplyEntity applyEntity) {
        if (applyEntity.getActiveStartTime().toEpochDay() - LocalDate.now().toEpochDay() < 7) {
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), "活动必须提前一周时间申请");
        }
        // 活动开始时间，必须小于等于活动结束时间  isBefore：比较第一个日期是否在第二个日期之前
        if (applyEntity.getActiveEndTime().isBefore(applyEntity.getActiveStartTime())) {
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), "活动开始时间必须小于等于活动结束时间");
        }
        if (applyEntity.getActiveStartTime().isBefore(applyEntity.getRegistrationDeadTime())) {
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), "活动报名截止时间必须小于等于活动开始时间");
        }
        if (applyEntity.getRegistrationDeadTime().isBefore(LocalDate.now())) {
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), "活动报名截止时间必须大于当前时间");
        }
    }

    private List<ApplyEntity> getApplyEntities(String activeName, String schoolYear) {
        return baseMapper.selectList(Wraps.lbQ(new ApplyEntity())
                .eq(ApplyEntity::getActiveName, activeName)
                .eq(ApplyEntity::getSchoolYear, schoolYear));
    }
}