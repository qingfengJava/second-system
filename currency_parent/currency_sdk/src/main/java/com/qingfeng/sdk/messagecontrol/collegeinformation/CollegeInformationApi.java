package com.qingfeng.sdk.messagecontrol.collegeinformation;

import com.qingfeng.cms.domain.college.dto.CollegeInformationSaveDTO;
import com.qingfeng.cms.domain.college.dto.CollegeInformationUpdateDTO;
import com.qingfeng.cms.domain.college.entity.CollegeInformationEntity;
import com.qingfeng.cms.domain.student.entity.StuInfoEntity;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.base.entity.SuperEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/11/30
 */
@FeignClient(value = "cms-message-control", fallback = CollegeInformationFallback.class)
@Component
public interface CollegeInformationApi {

    /**
     * 查询用户关联的学院信息
     * @param userId
     * @return
     */
    @GetMapping("/collegeinformation/info/{userId}")
    public R<CollegeInformationEntity> info(@PathVariable("userId") @NotNull Long userId);

    /**
     * 保存用户关联的学院信息
     * @param collegeInformationSaveDTO
     * @return
     */
    @PostMapping("/collegeinformation/save")
    public R save(@RequestBody @Validated CollegeInformationSaveDTO collegeInformationSaveDTO);

    /**
     * 更新用户关联的学院信息
     * @param collegeInformationUpdateDTO
     * @return
     */
    @PutMapping("/collegeinformation/update")
    public R update(@RequestBody @Validated(SuperEntity.Update.class) CollegeInformationUpdateDTO collegeInformationUpdateDTO);

    /**
     * 根据二级学院领导id查询学院下的学生信息
     * @param userId
     * @return
     */
    @GetMapping("/collegeinformation/user/{userId}")
    public R<List<StuInfoEntity>> getUserInfoList(@PathVariable("userId") Long userId);
}
