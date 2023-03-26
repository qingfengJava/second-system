package com.qingfeng.sdk.messagecontrol.clazz;

import com.qingfeng.cms.domain.clazz.dto.ClazzInfoDTO;
import com.qingfeng.cms.domain.clazz.entity.ClazzInfoEntity;
import com.qingfeng.cms.domain.clazz.vo.UserVo;
import com.qingfeng.currency.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/14
 */
@FeignClient(value = "cms-message-control", fallback = ClazzInfoApiFallback.class)
@Component
public interface ClazzInfoApi {

    /**
     * 查询班级下的学生信息
     * @return
     */
    @GetMapping("/clazz_info/stu/list")
    public R<List<UserVo>> stuList();

    /**
     * 根据院系，年级，班级，专业查询班级信息
     * @param clazzInfoDTO
     * @return
     */
    @PostMapping("/clazz_info/info/user")
    public R<ClazzInfoEntity> classInfo(@RequestBody ClazzInfoDTO clazzInfoDTO);

    /**
     * 根据用户Id查询班级信息
     * @return
     */
    @GetMapping("/clazz_info/info")
    public R<ClazzInfoEntity> info();
}
