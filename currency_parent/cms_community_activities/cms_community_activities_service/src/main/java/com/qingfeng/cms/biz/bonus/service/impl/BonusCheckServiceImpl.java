package com.qingfeng.cms.biz.bonus.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qingfeng.cms.biz.apply.enums.ApplyExceptionMsg;
import com.qingfeng.cms.biz.apply.service.ApplyService;
import com.qingfeng.cms.biz.bonus.dao.BonusCheckDao;
import com.qingfeng.cms.biz.bonus.listener.BonusExcelListener;
import com.qingfeng.cms.biz.bonus.service.BonusCheckService;
import com.qingfeng.cms.biz.mq.service.producer.RabbitSendMsg;
import com.qingfeng.cms.domain.apply.entity.ApplyEntity;
import com.qingfeng.cms.domain.bonus.dto.BonusCheckQueryDTO;
import com.qingfeng.cms.domain.bonus.dto.BonusCheckSaveDTO;
import com.qingfeng.cms.domain.bonus.entity.BonusCheckEntity;
import com.qingfeng.cms.domain.bonus.enums.BonusCheckStatusEnum;
import com.qingfeng.cms.domain.bonus.ro.BonusCheckRo;
import com.qingfeng.cms.domain.bonus.vo.BonusCheckVo;
import com.qingfeng.cms.domain.news.dto.NewsNotifySaveDTO;
import com.qingfeng.cms.domain.news.enums.IsSeeEnum;
import com.qingfeng.cms.domain.news.enums.NewsTypeEnum;
import com.qingfeng.cms.domain.organize.entity.OrganizeInfoEntity;
import com.qingfeng.cms.domain.sign.vo.SingBonusPointsVo;
import com.qingfeng.currency.authority.entity.auth.User;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.exception.code.ExceptionCode;
import com.qingfeng.sdk.auth.user.UserApi;
import com.qingfeng.sdk.messagecontrol.news.NewsNotifyApi;
import com.qingfeng.sdk.messagecontrol.organize.OrganizeInfoApi;
import com.qingfeng.sdk.sms.email.domain.EmailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;

/**
 * 加分文件审核表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-03 11:28:11
 */
@Service
public class BonusCheckServiceImpl extends ServiceImpl<BonusCheckDao, BonusCheckEntity> implements BonusCheckService {

    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private ApplyService applyService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RabbitSendMsg rabbitSendMsg;
    @Autowired
    private NewsNotifyApi newsNotifyApi;
    @Autowired
    private UserApi userApi;
    @Autowired
    private OrganizeInfoApi organizeInfoApi;

    /**
     * 查询需要加分审核的活动列表
     *
     * @param bonusCheckQueryDTO
     * @return
     */
    @Override
    public BonusCheckVo bonusList(BonusCheckQueryDTO bonusCheckQueryDTO) {
        Integer pageNo = bonusCheckQueryDTO.getPageNo();
        Integer pageSize = bonusCheckQueryDTO.getPageSize();
        // 查询需要加分审核的活动数
        Integer total = baseMapper.selectCountByQuery(bonusCheckQueryDTO);
        if (total == 0) {
            return BonusCheckVo.builder()
                    .total(total)
                    .bonusList(Collections.emptyList())
                    .pageNo(pageNo)
                    .pageSize(pageSize)
                    .build();
        }

        // 链表查询需要的数据
        bonusCheckQueryDTO.setPageNo((pageNo - 1) * pageSize);
        List<BonusCheckRo> bonusCheckRoList = baseMapper.bonusList(bonusCheckQueryDTO);

        return BonusCheckVo.builder()
                .total(total)
                .bonusList(bonusCheckRoList)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .build();
    }

    /**
     * 活动加分审核结果保存
     *
     * @param bonusCheckSaveDTO
     */
    @Override
    public void updateBonus(BonusCheckSaveDTO bonusCheckSaveDTO) throws IOException {
        baseMapper.updateById(dozerUtils.map2(bonusCheckSaveDTO, BonusCheckEntity.class));

        // 给对应用户发送审核通知
        ApplyEntity applyEntity = applyService.getById(
                baseMapper.selectById(bonusCheckSaveDTO.getId())
                        .getApplyId()
        );
        User user = userApi.get(applyEntity.getApplyUserId()).getData();
        OrganizeInfoEntity organizeInfoEntity = organizeInfoApi.info(user.getId()).getData();
        String title = "活动《" + applyEntity.getActiveName() + "》加分审核通知";
        String body = "";
        if (bonusCheckSaveDTO.getCheckStatus().equals(BonusCheckStatusEnum.IS_PASSED)) {
            body = "亲爱的：\r\n       "
                    + organizeInfoEntity.getOrganizeName()
                    + "，贵社举办的《" + applyEntity.getActiveName()
                    + "》活动加分审核已经【审核通过】，感谢贵社带来的精彩活动！";
        } else {
            body = "亲爱的：\r\n       "
                    + organizeInfoEntity.getOrganizeName()
                    + "，贵社举办的《" + applyEntity.getActiveName()
                    + "》活动加分审核【审核不通过】，请尽早规范修改，避免影响活动加分！"
                    + "\r\n原因：\r\n"
                    + bonusCheckSaveDTO.getCheckContent();
        }

        if (StrUtil.isNotBlank(user.getEmail())) {
            //有邮箱就先向邮箱中发送消息   使用消息队列进行发送  失败重试三次
            try {
                rabbitSendMsg.sendEmail(objectMapper.writeValueAsString(EmailEntity.builder()
                        .email(user.getEmail())
                        .title(title)
                        .body(body)
                        .key("bonus.check.email")
                        .build()), "bonus.check.email");

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


        // TODO 添加学分，待学分服务完善才能做，后期使用MQ去做
        // 获取加分文件的连接
        String bonusFile = applyService.getById(
                baseMapper.selectById(bonusCheckSaveDTO.getId())
                        .getApplyId()
        ).getBonusFile();

        InputStream inputStream = new URL(bonusFile).openConnection().getInputStream();
        EasyExcel.read(
                        inputStream,
                        SingBonusPointsVo.class,
                        new BonusExcelListener()
                ).sheet()
                .doRead();
    }
}