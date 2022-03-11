package com.qingfeng.controller;

import com.qingfeng.service.NoticeService;
import com.qingfeng.service.RegistService;
import com.qingfeng.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务管理控制层：
 *     注意：对于不同用户来说，任务是不一样的。
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/3/10
 */
@Api(value = "提供任务管理的接口",tags = "任务管理层接口")
@RestController
@RequestMapping("/task")
@CrossOrigin
public class TaskController {

    @Autowired
    private RegistService registService;
    @Autowired
    private NoticeService noticeService;

    @ApiOperation("学生待处理（重要）任务查询")
    @PostMapping("/stu_wait")
    public ResultVO queryWaitTask(Integer isAdmin, Integer pageNum, Integer limit){
        return noticeService.queryWaitTask(isAdmin,pageNum,limit);
    }
}
