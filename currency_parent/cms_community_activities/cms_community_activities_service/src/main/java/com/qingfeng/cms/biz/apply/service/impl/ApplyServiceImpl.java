package com.qingfeng.cms.biz.apply.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qingfeng.cms.biz.apply.dao.ApplyDao;
import com.qingfeng.cms.biz.apply.enums.ApplyExceptionMsg;
import com.qingfeng.cms.biz.apply.service.ApplyService;
import com.qingfeng.cms.biz.bonus.service.BonusCheckService;
import com.qingfeng.cms.biz.mq.service.producer.RabbitSendMsg;
import com.qingfeng.cms.biz.sign.service.ActiveSignService;
import com.qingfeng.cms.domain.apply.dto.ApplyCheckQueryDTO;
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
import com.qingfeng.cms.domain.apply.vo.ActiveApplyVo;
import com.qingfeng.cms.domain.apply.vo.ApplyCheckEntityVo;
import com.qingfeng.cms.domain.apply.vo.ApplyCheckListVo;
import com.qingfeng.cms.domain.apply.vo.ApplyListVo;
import com.qingfeng.cms.domain.apply.vo.BonusFileVo;
import com.qingfeng.cms.domain.bonus.entity.BonusCheckEntity;
import com.qingfeng.cms.domain.bonus.enums.BonusCheckStatusEnum;
import com.qingfeng.cms.domain.news.dto.NewsNotifySaveDTO;
import com.qingfeng.cms.domain.news.enums.IsSeeEnum;
import com.qingfeng.cms.domain.news.enums.NewsTypeEnum;
import com.qingfeng.cms.domain.organize.entity.OrganizeInfoEntity;
import com.qingfeng.cms.domain.sign.entity.ActiveSignEntity;
import com.qingfeng.currency.authority.entity.auth.User;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.database.mybatis.conditions.query.LbqWrapper;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private BonusCheckService bonusCheckService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ActiveSignService activeSignService;

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
        List<ApplyEntity> applyEntityList = getApplyEntities(applySaveDTO.getActiveName(), applySaveDTO.getSchoolYear(), null);

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
        List<ApplyEntity> applyEntityList = getApplyEntities(applyUpdateDTO.getActiveName(),
                applyUpdateDTO.getSchoolYear(),
                applyUpdateDTO.getId());

        if (CollUtil.isEmpty(applyEntityList)) {
            //说明没有重复的活动
            ApplyEntity applyEntity = dozerUtils.map2(applyUpdateDTO, ApplyEntity.class);

            //校验日期的合法性
            inspectionTime(applyEntity);

            applyEntity.setActiveType(ActiveTypeEnum.COMMUNITY_WORK)
                    .setAgreeStatus(AgreeStatusEnum.INIT)
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
            String body = "";
            if (activeApplyCheckRo.getAgreeStatus().equals(AgreeStatusEnum.IS_PASSED)) {
                body = "亲爱的：\r\n       "
                        + organizeInfoEntity.getOrganizeName()
                        + "，您申请的《" + applyEntity.getActiveName()
                        + "》活动已经【审核通过】，请尽早发布，避免影响活动正常进行！";
            } else {
                body = "亲爱的：\r\n       "
                        + organizeInfoEntity.getOrganizeName()
                        + "，您申请的《" + applyEntity.getActiveName()
                        + "》活动【审核不通过】，请尽早修改，避免影响活动正常进行！";
            }

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

    /**
     * 活动申请审核列表
     *
     * @param applyCheckQueryDTO
     * @return
     */
    @Override
    public ApplyCheckListVo findApplyCheckList(ApplyCheckQueryDTO applyCheckQueryDTO) {
        Integer pageNo = applyCheckQueryDTO.getPageNo();
        Integer pageSize = applyCheckQueryDTO.getPageSize();
        applyCheckQueryDTO.setPageNo((pageNo - 1) * pageSize);
        //查询总记录数
        Integer total = baseMapper.selectCount(Wraps.lbQ(new ApplyEntity()));
        if (total == 0) {
            return ApplyCheckListVo.builder()
                    .total(0)
                    .applyCheckEntityVoList(Collections.emptyList())
                    .pageNo(pageNo)
                    .pageSize(pageSize)
                    .build();
        }
        List<ApplyEntity> applyEntityList = applyDao.findApplyCheckList(applyCheckQueryDTO);
        List<Long> applyUserIds = applyEntityList.stream()
                .map(ApplyEntity::getApplyUserId)
                .distinct()
                .collect(Collectors.toList());
        //查询社团信息
        ConcurrentMap<Long, OrganizeInfoEntity> organizeInfoEntityConcurrentMap = organizeInfoApi.infoList(applyUserIds)
                .getData()
                .stream()
                .collect(Collectors.toConcurrentMap(
                        OrganizeInfoEntity::getUserId,
                        Function.identity())
                );

        List<ApplyCheckEntityVo> applyCheckEntityVos = dozerUtils.mapList(applyEntityList, ApplyCheckEntityVo.class);
        applyCheckEntityVos.forEach(a -> a.setOrganizeInfoEntity(organizeInfoEntityConcurrentMap.get(a.getApplyUserId())));
        return ApplyCheckListVo.builder()
                .total(total)
                .applyCheckEntityVoList(applyCheckEntityVos)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .build();
    }

    @Override
    public void uploadBonusFile(BonusFileVo bonusFileVo) {
        // 活动加分文件，直接刷新进对应的活动
        baseMapper.updateById(dozerUtils.map2(bonusFileVo, ApplyEntity.class));
        // 往加分活动审核表里面添加一条数据
        BonusCheckEntity bonusCheck = bonusCheckService.getOne(Wraps.lbQ(new BonusCheckEntity())
                .eq(BonusCheckEntity::getApplyId, bonusFileVo.getId()));

        if (ObjectUtil.isEmpty(bonusCheck)) {
            bonusCheck = BonusCheckEntity.builder()
                    .applyId(bonusFileVo.getId())
                    .checkStatus(BonusCheckStatusEnum.INIT)
                    .build();
            bonusCheckService.save(bonusCheck);
        } else {
            bonusCheck.setCheckStatus(BonusCheckStatusEnum.INIT);
            bonusCheckService.updateById(bonusCheck);
        }

    }

    /**
     * 根据活动Id和用户Id查询活动信息
     *
     * @param applyId
     * @param userId
     * @return
     */
    @Override
    public ActiveApplyVo findActiveByApplyIdAndUserId(Long applyId, Long userId) {
        // 查询活动申请信息
        ApplyEntity applyEntity = baseMapper.selectById(applyId);
        // 查询用户签到信息
        ActiveSignEntity sign = activeSignService.getOne(
                Wraps.lbQ(new ActiveSignEntity())
                        .eq(ActiveSignEntity::getApplyId, applyId)
                        .eq(ActiveSignEntity::getUserId, userId)
        );

        // 查询社团组织信息
        OrganizeInfoEntity organizeInfo = organizeInfoApi.info(applyEntity.getApplyUserId()).getData();

        return ActiveApplyVo.builder()
                .apply(applyEntity)
                .activeSign(sign)
                .organizeInfo(organizeInfo)
                .build();
    }

    /**
     * 查询社团申请并已经通过的活动，并且按照活动开始时间正序排序
     *
     * @param userId
     * @return
     */
    @Override
    public List<ApplyEntity> applyByUserId(Long userId) {
        return baseMapper.selectList(Wraps.lbQ(new ApplyEntity())
                .eq(ApplyEntity::getApplyUserId, userId)
                .eq(ApplyEntity::getAgreeStatus, AgreeStatusEnum.IS_PASSED)
                .in(ApplyEntity::getActiveStatus, Arrays.asList(
                                ActiveStatusEnum.INIT,
                                ActiveStatusEnum.HAVING
                        )
                )
                .orderByAsc(ApplyEntity::getActiveStartTime)
        );
    }

    /**
     * 发布活动开始通知
     *
     * @param applyId
     */
    @Override
    public void applyStartNotice(Long applyId) {
        // 查询改活动所有以报名的用户报名信息
        List<ActiveSignEntity> signEntityList = activeSignService.list(
                Wraps.lbQ(new ActiveSignEntity())
                        .eq(ActiveSignEntity::getApplyId, applyId)
        );

        if (CollUtil.isNotEmpty(signEntityList)) {
            ApplyEntity applyEntity = baseMapper.selectById(applyId);

            // 给对应的用户发送邮件信息
            Map<Long, User> userMap = userApi.userInfoList(
                            signEntityList.stream()
                                    .map(ActiveSignEntity::getUserId)
                                    .collect(Collectors.toList())
                    ).getData()
                    .stream()
                    .collect(Collectors.toMap(
                                    User::getId,
                                    Function.identity()
                            )
                    );

            signEntityList.forEach(s -> {
                String title = "活动《" + applyEntity.getActiveName() + "》已开始通知";
                String body = "亲爱的同学：\r\n       "
                        + "，您报名的活动《" + applyEntity.getActiveName()
                        + "》已经开始了，请及时参与活动，并前往签到服务进行活动签到，否则将视为弃权活动！";
                User user = userMap.get(s.getUserId());
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
            });

        }
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

    private List<ApplyEntity> getApplyEntities(String activeName, String schoolYear, Long id) {
        LbqWrapper<ApplyEntity> wrapper = Wraps.lbQ(new ApplyEntity())
                .eq(ApplyEntity::getActiveName, activeName)
                .eq(ApplyEntity::getSchoolYear, schoolYear);
        if (ObjectUtil.isNotEmpty(id)) {
            wrapper.ne(ApplyEntity::getId, id);
        }
        return baseMapper.selectList(wrapper);
    }
}