package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.notice.service.NoticeService;
import com.qingfeng.cms.domain.notice.dto.NoticeQueryDTO;
import com.qingfeng.cms.domain.notice.dto.NoticeSaveDTO;
import com.qingfeng.cms.domain.notice.dto.NoticeUpdateDTO;
import com.qingfeng.cms.domain.notice.entity.NoticeEntity;
import com.qingfeng.cms.domain.notice.vo.NoticePageVo;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * 系统公告表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供系统公告的相关功能", tags = "系统公告")
@RequestMapping("/notice")
public class NoticeController extends BaseController {

    @Autowired
    private NoticeService noticeService;

    @ApiOperation(value = "分页查询用户自己发布的公告信息", notes = "分页查询用户自己发布的公告信息")
    @PostMapping("/list")
    @SysLog("分页查询用户自己发布的公告信息")
    public R<NoticePageVo> noticeList(@RequestBody NoticeQueryDTO noticeQueryDTO){
        NoticePageVo noticePageVo = noticeService.noticeList(noticeQueryDTO, getUserId());
        return success(noticePageVo);
    }

    // TODO 查询用户需要查看的公告信息


    @ApiOperation(value = "根据Id查询公告信息", notes = "根据Id查询公告信息")
    @GetMapping("/info/{id}")
    @SysLog("根据Id查询公告信息")
    public R<NoticeEntity> info(@PathVariable("id") Long id){
		NoticeEntity notice = noticeService.getNoticeById(id, getUserId());
        return success(notice);
    }

    @ApiOperation(value = "发布第二课堂公告信息", notes = "发布第二课堂公告信息")
    @PostMapping("/publish")
    @SysLog("发布第二课堂公告信息")
    public R publishNotice(@RequestBody @Validated NoticeSaveDTO noticeSaveDTO){
		noticeService.publishNotice(noticeSaveDTO, getUserId());
        return success();
    }

    @ApiOperation(value = "修改已发布的公告信息", notes = "修改已发布的公告信息")
    @PutMapping
    @SysLog("修改已发布的公告信息")
    public R updateNotice(@RequestBody @Validated NoticeUpdateDTO noticeUpdateDTO){
		noticeService.updateNoticeById(noticeUpdateDTO, getUserId());
        return success();
    }

    @ApiOperation(value = "根据Id删除第二课堂公告信息", notes = "根据Id删除第二课堂公告信息")
    @DeleteMapping("/{id}")
    @SysLog("根据Id删除第二课堂公告信息")
    public R deleteNoticeById(@PathVariable("id") Long id){
		noticeService.deleteNoticeById(id);
        return success();
    }

}
