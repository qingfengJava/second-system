package com.qingfeng.currency.authority.biz.service.mq.consumer;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qingfeng.cms.domain.dict.enums.DictDepartmentEnum;
import com.qingfeng.cms.domain.news.dto.NewsNotifySaveDTO;
import com.qingfeng.cms.domain.news.enums.IsSeeEnum;
import com.qingfeng.cms.domain.news.enums.NewsTypeEnum;
import com.qingfeng.cms.domain.student.dto.StuInfoSaveDTO;
import com.qingfeng.cms.domain.student.enums.EducationalSystemEnum;
import com.qingfeng.cms.domain.student.enums.HuKouTypeEnum;
import com.qingfeng.cms.domain.student.enums.IsChangeEnum;
import com.qingfeng.cms.domain.student.enums.PoliticsStatusEnum;
import com.qingfeng.cms.domain.student.enums.StateSchoolEnum;
import com.qingfeng.cms.domain.student.enums.StudentTypeEnum;
import com.qingfeng.currency.authority.biz.service.auth.UserService;
import com.qingfeng.currency.authority.biz.service.core.OrgService;
import com.qingfeng.currency.authority.biz.service.core.StationService;
import com.qingfeng.currency.authority.config.mq.RabbitMqConfig;
import com.qingfeng.currency.authority.entity.auth.User;
import com.qingfeng.currency.authority.entity.auth.vo.UserReadVo;
import com.qingfeng.currency.authority.entity.core.Org;
import com.qingfeng.currency.authority.entity.core.Station;
import com.qingfeng.currency.authority.enumeration.auth.Sex;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.sdk.auth.user.UserApi;
import com.qingfeng.sdk.messagecontrol.StuInfo.StuInfoApi;
import com.qingfeng.sdk.messagecontrol.news.NewsNotifyApi;
import com.qingfeng.sdk.sms.email.EmailApi;
import com.qingfeng.sdk.sms.email.domain.EmailEntity;
import com.rabbitmq.client.Channel;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/19
 */
@Component
public class RabbitReceiveHandler {

    private static Integer COUNT = 0;
    private static Integer MAX_LIMIT = 3;
    /**
     * 学生处管理员账号Id
     */
    private static Long STU_OFFICE_ADMIN = 641577229343523041L;


    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrgService orgService;
    @Autowired
    private StationService stationService;

    @Autowired
    private StuInfoApi stuInfoApi;
    @Autowired
    private UserApi userApi;
    @Autowired
    private EmailApi emailApi;
    @Autowired
    private NewsNotifyApi newsNotifyApi;

    /**
     * 监听邮箱队列的消费
     *
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = {RabbitMqConfig.QUEUE_USER_INFO})
    public void send_email(Message message, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        if (COUNT.equals(MAX_LIMIT)) {
            COUNT = 0;
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }

        // 接收到对象
        UserReadVo userReadVo = objectMapper.readValue(new String(message.getBody(), StandardCharsets.UTF_8), UserReadVo.class);

        //先封装用户对象
        User user = User.builder()
                .account(userReadVo.getAccount())
                .name(userReadVo.getName())
                .email(userReadVo.getEmail())
                .mobile(userReadVo.getMobile())
                .sex(Sex.get(userReadVo.getSex()))
                .status(userReadVo.getStatus())
                .password(DigestUtils.md5Hex(userReadVo.getPassword()))
                .build();

        //根据院系名称去查询组织和岗位   这里要求院系名称要和组织设定的院系名称一致 不然无法查询
        if (StrUtil.isNotBlank(userReadVo.getDepartment())) {
            Org org = orgService.getOne(Wraps.lbQ(new Org())
                    .eq(Org::getName, userReadVo.getDepartment()));

            Optional.ofNullable(org)
                    .ifPresent(o -> {
                        user.setOrgId(o.getId());
                        //根据组织Id查询岗位Id
                        Station station = stationService.getOne(Wraps.lbQ(new Station())
                                .eq(Station::getOrgId, org.getId())
                                .likeLeft(Station::getName, "学生"));
                        Optional.ofNullable(station)
                                .ifPresent(s -> user.setStationId(s.getId()));
                    });
        }

        //先将用户进行添加
        userService.save(user);

        //下面根据用户的Id进行用户详情信息的保存
        StuInfoSaveDTO stuInfoSaveDTO = StuInfoSaveDTO.builder()
                .userId(user.getId())
                .studentNum(userReadVo.getStudentNum())
                .studentName(userReadVo.getName())
                .birth(userReadVo.getBirth())
                .nation(userReadVo.getNation())
                .politicsStatus(PoliticsStatusEnum.get(userReadVo.getPoliticsStatus()))
                .enterTime(userReadVo.getEnterTime())
                .graduateTime(userReadVo.getGraduateTime())
                .idCard(userReadVo.getIdCard())
                .hukou(HuKouTypeEnum.get(userReadVo.getHukou()))
                .qq(userReadVo.getQq())
                .weChat(userReadVo.getWeChat())
                .nativePlace(userReadVo.getNativePlace())
                .address(userReadVo.getAddress())
                .stateSchool(StateSchoolEnum.get(userReadVo.getStateSchool()))
                .type(StudentTypeEnum.get(userReadVo.getType()))
                .department(DictDepartmentEnum.get(userReadVo.getDepartment()))
                .major(userReadVo.getMajor())
                .grade(userReadVo.getGrade())
                .clazz(userReadVo.getClazz())
                .educationalSystem(EducationalSystemEnum.get(userReadVo.getEducationalSystem()))
                .hobyDes(userReadVo.getHobyDes())
                .isChange(IsChangeEnum.FALSE)
                .build();
        //直接进行保存
        stuInfoApi.save(stuInfoSaveDTO);

        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }

    /**
     * 监听死信队列的消费
     *
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = {RabbitMqConfig.DEAD_LETTER_QUEUE})
    public void dead_email(Message message, Channel channel) throws IOException {
        // 一旦进入死信队列，需要通知当前发送邮件的人
        UserReadVo userReadVo = objectMapper.readValue(new String(message.getBody(), StandardCharsets.UTF_8), UserReadVo.class);
        //一旦失败，需要给当前操作人发送短信或者邮件通知
        User user = userApi.get(STU_OFFICE_ADMIN).getData();
        if (StrUtil.isNotBlank(user.getEmail())) {
            emailApi.sendEmail(EmailEntity.builder()
                    .email(user.getEmail())
                    .title("学生：" + userReadVo.getName() + "(" + userReadVo.getAccount() + ")的信息导入失败通知")
                    .body("学生：" + userReadVo.getName() + "(" + userReadVo.getAccount() + ")的信息在Excel表导入的时候出现未知的异常！\r\n"
                            + "请核对信息，前往系统手动添加用户。")
                    .build());

            newsNotifyApi.save(NewsNotifySaveDTO.builder()
                    .userId(user.getId())
                    .newsType(NewsTypeEnum.MAILBOX)
                    .newsTitle("学生：" + userReadVo.getName() + "(" + userReadVo.getAccount() + ")的信息导入失败通知")
                    .newsContent("学生：" + userReadVo.getName() + "(" + userReadVo.getAccount() + ")的信息在Excel表导入的时候出现未知的异常！\r\n"
                            + "请核对信息，前往系统手动添加用户。")
                    .isSee(IsSeeEnum.IS_NOT_VIEWED)
                    .build());
        }

        // 采用手动应答模式, 手动确认应答更为安全稳定
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }

}
