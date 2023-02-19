package com.qingfeng.cms.biz.project.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qingfeng.cms.biz.level.service.LevelService;
import com.qingfeng.cms.biz.mq.service.producer.RabbitSendMsg;
import com.qingfeng.cms.biz.project.dao.ProjectDao;
import com.qingfeng.cms.biz.project.enums.ProjectExceptionMsg;
import com.qingfeng.cms.biz.project.service.ProjectService;
import com.qingfeng.cms.biz.rule.service.CreditRulesService;
import com.qingfeng.cms.domain.college.entity.CollegeInformationEntity;
import com.qingfeng.cms.domain.level.entity.LevelEntity;
import com.qingfeng.cms.domain.level.vo.LevelListVo;
import com.qingfeng.cms.domain.news.dto.NewsNotifySaveDTO;
import com.qingfeng.cms.domain.news.enums.IsSeeEnum;
import com.qingfeng.cms.domain.news.enums.NewsTypeEnum;
import com.qingfeng.cms.domain.project.dto.ProjectCheckDTO;
import com.qingfeng.cms.domain.project.dto.ProjectQueryDTO;
import com.qingfeng.cms.domain.project.dto.ProjectSaveDTO;
import com.qingfeng.cms.domain.project.dto.ProjectUpdateDTO;
import com.qingfeng.cms.domain.project.entity.ProjectEntity;
import com.qingfeng.cms.domain.project.enums.ProjectCheckEnum;
import com.qingfeng.cms.domain.project.enums.ProjectTypeEnum;
import com.qingfeng.cms.domain.project.vo.ProjectCheckEnumsVo;
import com.qingfeng.cms.domain.project.vo.ProjectListVo;
import com.qingfeng.cms.domain.project.vo.ProjectTypeEnumsVo;
import com.qingfeng.cms.domain.rule.entity.CreditRulesEntity;
import com.qingfeng.cms.domain.rule.vo.CreditRulesVo;
import com.qingfeng.currency.authority.entity.auth.User;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.common.enums.RoleEnum;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.database.mybatis.conditions.query.LbqWrapper;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.exception.code.ExceptionCode;
import com.qingfeng.sdk.auth.role.RoleApi;
import com.qingfeng.sdk.auth.user.UserApi;
import com.qingfeng.sdk.messagecontrol.collegeinformation.CollegeInformationApi;
import com.qingfeng.sdk.messagecontrol.news.NewsNotifyApi;
import com.qingfeng.sdk.sms.email.EmailApi;
import com.qingfeng.sdk.sms.email.domain.EmailEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 项目表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-10-08 19:44:16
 */
@Service("projectService")
@Slf4j
public class ProjectServiceImpl extends ServiceImpl<ProjectDao, ProjectEntity> implements ProjectService {

    private static Long USER_ID = 0L;
    private static final String PROJECT_KEY = "project.inform.email";

    @Autowired
    private DozerUtils dozer;

    @Autowired
    private LevelService levelService;
    @Autowired
    private CreditRulesService creditRulesService;

    @Autowired
    private RoleApi roleApi;
    @Autowired
    private UserApi userApi;
    @Autowired
    private EmailApi emailApi;
    @Autowired
    private NewsNotifyApi newsNotifyApi;
    @Autowired
    private CollegeInformationApi collegeInformationApi;

    @Autowired
    private RabbitSendMsg rabbitSendMsg;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 保存模块项目内容
     * 注意事项：判断用户角色，主要是院级申请的项目，需要进行审核
     *
     * @param projectSaveDTO
     * @param userId
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public void saveProject(ProjectSaveDTO projectSaveDTO, Long userId) {
        //排除项目名字一样的内容
        ProjectEntity projectEntity = dozer.map2(projectSaveDTO, ProjectEntity.class);
        checkProject(projectEntity);

        // 需要判断用户角色，如果是二级学院领导（YUAN_LEVEL_LEADER），那么申报的项目就需要审核
        R<List<Long>> userIdByCode = roleApi.findUserIdByCode(new String[]{RoleEnum.YUAN_LEVEL_LEADER.name()});
        if (userIdByCode.getData().contains(userId)) {
            //是学院申请的项目
            // 查询用户对应的学院信息
            R<CollegeInformationEntity> r = collegeInformationApi.info(userId);
            if (ObjectUtil.isNotEmpty(r.getData())){
                projectEntity.setDepartment(r.getData().getOrganizationCode());
            }
            //封装信息
//            projectEntity.setDepartment("SJ");
            projectEntity.setIsCheck(ProjectCheckEnum.INIT);
        } else {
            // 不是学院直接封装 PZHU
            projectEntity.setDepartment("PZHU");
            projectEntity.setIsCheck(ProjectCheckEnum.IS_FINISHED);
        }

        // TODO 如果是需要审核的，发送审核通知

        //保存
        baseMapper.insert(projectEntity);

    }

    /**
     * 修改模块项目内容
     *
     * @param projectUpdateDTO
     */
    @Override
    public void updateProjectById(ProjectUpdateDTO projectUpdateDTO, Long userId) {
        // 需要判断修改的内容是否已经重复
        ProjectEntity projectEntity = dozer.map2(projectUpdateDTO, ProjectEntity.class);
        checkProject(projectEntity);
        // 需要判断用户角色
        R<List<Long>> userIdByCode = roleApi.findUserIdByCode(new String[]{RoleEnum.YUAN_LEVEL_LEADER.name()});
        if (userIdByCode.getData().contains(userId)) {
            //是学院申请的项目 要重新进行审核
            // 查询用户对应的学院信息
            R<CollegeInformationEntity> r = collegeInformationApi.info(userId);
            if (ObjectUtil.isNotEmpty(r.getData())){
                projectEntity.setDepartment(r.getData().getOrganizationCode());
            }
            //封装信息
            projectEntity.setIsCheck(ProjectCheckEnum.INIT);
        } else {
            // 不是学院直接封装 PZHU
            projectEntity.setDepartment("PZHU");
            projectEntity.setIsCheck(ProjectCheckEnum.IS_FINISHED);
        }

        baseMapper.updateById(projectEntity);
    }

    /**
     * 查询项目学分列表
     * 1、按条件进行筛选，
     * 2、排序
     *
     * @param projectQueryDTO
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public List<ProjectListVo> findList(ProjectQueryDTO projectQueryDTO) {
        List<ProjectListVo> projectListVo = baseMapper.selectAllList(projectQueryDTO);

        //查询项目下的等级
        List<LevelEntity> levelEntityList = levelService.list(Wraps.lbQ(new LevelEntity())
                .in(LevelEntity::getProjectId, projectListVo.stream()
                        .map(ProjectListVo::getId)
                        .collect(Collectors.toList())));
        Map<Long, List<LevelListVo>> LevelMap = dozer.mapList(levelEntityList, LevelListVo.class)
                .stream()
                .collect(Collectors.groupingBy(
                        LevelListVo::getProjectId
                ));

        //查询等级下的学分
        List<CreditRulesEntity> creditRulesEntityList = creditRulesService.list(Wraps.lbQ(new CreditRulesEntity())
                .in(CreditRulesEntity::getLevelId, levelEntityList.stream()
                        .map(LevelEntity::getId)
                        .collect(Collectors.toSet())));
        Map<Long, List<CreditRulesVo>> creditRulesMap = dozer.mapList(creditRulesEntityList, CreditRulesVo.class)
                .stream()
                .collect(Collectors.groupingBy(
                        CreditRulesVo::getLevelId
                ));

        projectListVo.forEach(p -> {
            //先封装学分
            if (!ObjectUtils.isEmpty(LevelMap.get(p.getId()))) {
                LevelMap.get(p.getId())
                        .forEach(l -> l.setCreditRulesVoList(creditRulesMap.get(l.getId())));
            }
            //封装等级
            p.setLevelListVo(LevelMap.get(p.getId()));
        });

        return projectListVo;
    }

    /**
     * 返回项目枚举类型
     *
     * @return
     */
    @Override
    public List<ProjectTypeEnumsVo> getProjectType() {
        List<ProjectTypeEnumsVo> projectTypeList = Arrays.stream(ProjectTypeEnum.values())
                .map(p -> ProjectTypeEnumsVo.builder()
                        .label(p.getDesc())
                        .value(p.name())
                        .build())
                .collect(Collectors.toList());

        return projectTypeList;
    }

    /**
     * 返回项目审核枚举
     *
     * @return
     */
    @Override
    public List<ProjectCheckEnumsVo> getProjectCheck() {
        List<ProjectCheckEnumsVo> projectCheckEnumsVos = Arrays.stream(ProjectCheckEnum.values())
                .map(p -> ProjectCheckEnumsVo.builder()
                        .label(p.getDesc())
                        .value(p.name())
                        .build())
                .collect(Collectors.toList());
        return projectCheckEnumsVos;
    }

    /**
     * 根据项目id删除项目及其关联的等级和学分
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public void removeProjectById(Long id) {
        //删除等级下关联的学分
        List<Long> levelIds = levelService.list(Wraps.lbQ(new LevelEntity())
                        .eq(LevelEntity::getProjectId, id))
                .stream()
                .map(LevelEntity::getId)
                .collect(Collectors.toList());
        if (CollUtil.isNotEmpty(levelIds)){
            creditRulesService.remove(Wraps.lbQ(new CreditRulesEntity())
                    .in(CreditRulesEntity::getLevelId, levelIds));
        }

        //删除关联的等级
        levelService.remove(Wraps.lbQ(new LevelEntity())
                .eq(LevelEntity::getProjectId, id));

        baseMapper.deleteById(id);
    }

    /**
     * 项目信息审核
     *
     * @param projectCheckDTO
     * @param userId
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public void checkProject(ProjectCheckDTO projectCheckDTO, Long userId) throws JsonProcessingException {
        USER_ID = userId;
        ProjectEntity projectEntity = dozer.map2(projectCheckDTO, ProjectEntity.class);
        baseMapper.updateById(projectEntity);

        //查询项目详情
        ProjectEntity project = baseMapper.selectById(projectCheckDTO.getId());
        //查询用户详细信息
        User user = userApi.get(project.getCreateUser()).getData();

        if (ObjectUtil.isNotEmpty(user)) {
            // TODO 审核结果发送消息通知  目前先发送邮件通知
            if (ObjectUtil.isNotEmpty(user.getEmail())) {
                String title = projectCheckDTO.getIsCheck().equals(ProjectCheckEnum.IS_FINISHED) ?
                        "项目<" + project.getProjectName() + ">申请审核通过通知" : "项目<" + project.getProjectName() + ">审核不通过通知";

                //有邮箱就先向邮箱中发送消息   使用消息队列进行发送  失败重试三次
                rabbitSendMsg.sendEmail(objectMapper.writeValueAsString(EmailEntity.builder()
                        .email(user.getEmail())
                        .title(title)
                        .body(projectCheckDTO.getCheckDetail())
                        .key(PROJECT_KEY)
                        .build()), PROJECT_KEY);

                //将消息通知写入数据库
                R r = newsNotifyApi.save(NewsNotifySaveDTO.builder()
                        .userId(project.getCreateUser())
                        .newsType(NewsTypeEnum.MAILBOX)
                        .newsTitle(title)
                        .newsContent(projectCheckDTO.getCheckDetail())
                        .isSee(IsSeeEnum.IS_NOT_VIEWED)
                        .build());

                if (r.getIsError()) {
                    throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), ProjectExceptionMsg.NEWS_SAVE_FAILED.getMsg());
                }

            } else if (ObjectUtil.isNotEmpty(user.getMobile())) {
                // TODO 向短信发送信息 待完善  失败重试三次

            }
        } else {
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), ProjectExceptionMsg.USER_NOT_EXITS.getMsg());

        }

    }

    /**
     * 审核信息发送失败，给负责人自己发送一条；信息通知
     *
     * @param emailEntity
     */
    @Override
    public void sendMessageToSlfe(EmailEntity emailEntity) {
        //先根据Id查询自己的信息
        User user = userApi.get(USER_ID).getData();
        if (ObjectUtil.isNotEmpty(user)) {
            if (ObjectUtil.isNotEmpty(user.getEmail())) {
                //邮箱不为空 就给邮箱发送信息  这里操作不用失败重试需要重新封装信息
                emailEntity.setEmail(user.getEmail())
                        .setBody("关于" + emailEntity.getTitle() + "邮件通知失败！，如有必要，请线下通知对方。")
                        .setTitle("消息通知失败");
                emailApi.sendEmail(emailEntity);
            } else if (ObjectUtil.isNotEmpty(user.getMobile())) {
                // TODO 否则，使用短信通知
            }
        }
    }

    /**
     * 检查模块项目是否重复
     *
     * @param projectEntity
     */
    private void checkProject(ProjectEntity projectEntity) {
        LbqWrapper<ProjectEntity> wrapper = Wraps.lbQ(new ProjectEntity())
                .eq(ProjectEntity::getModuleId, projectEntity.getModuleId())
                .like(ProjectEntity::getProjectName, projectEntity.getProjectName());
        if (ObjectUtil.isNotEmpty(projectEntity.getId())) {
            wrapper.ne(ProjectEntity::getId, projectEntity.getId());
        }
        ProjectEntity project = baseMapper.selectOne(wrapper);
        if (ObjectUtil.isNotEmpty(project)) {
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), ProjectExceptionMsg.IS_EXISTS.getMsg());
        }
    }
}