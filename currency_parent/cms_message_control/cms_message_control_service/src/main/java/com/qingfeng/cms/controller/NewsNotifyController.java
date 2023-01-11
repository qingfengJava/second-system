package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.news.service.NewsNotifyService;
import com.qingfeng.cms.domain.news.dto.NewsNotifyQueryDTO;
import com.qingfeng.cms.domain.news.dto.NewsNotifySaveDTO;
import com.qingfeng.cms.domain.news.entity.NewsNotifyEntity;
import com.qingfeng.cms.domain.news.enums.IsSeeEnum;
import com.qingfeng.cms.domain.news.vo.NewsNotifyListVo;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
 * 消息通知表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供消息通知的相关功能", tags = "提供消息通知的相关功能")
@RequestMapping("/newsnotify")
public class NewsNotifyController extends BaseController {

    @Autowired
    private NewsNotifyService newsNotifyService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "long", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页显示几条", dataType = "long", paramType = "query", defaultValue = "15"),
    })
    @ApiOperation(value = "分页查询系统消息通知信息", notes = "分页查询系统消息通知信息")
    @GetMapping("/page")
    @SysLog("分页查询系统消息通知信息")
    public R<NewsNotifyListVo> list(NewsNotifyQueryDTO newsNotifyQueryDTO) {
        NewsNotifyListVo newsNotifyListVoList = newsNotifyService.findList(getPageNo(), getPageSize(), getUserId(), newsNotifyQueryDTO);
        return success(newsNotifyListVoList);
    }

    @ApiOperation(value = "保存消息通知信息", notes = "保存消息通知信息")
    @PostMapping("/save")
    @SysLog("保存消息通知信息")
    public R save(@ApiParam(value = "消息保存实体", required = true)
                  @RequestBody @Validated NewsNotifySaveDTO newsNotifySaveDTO) {
        newsNotifyService.saveNews(newsNotifySaveDTO);
        return success();
    }

    @ApiOperation(value = "修改消息的状态", notes = "修改消息的状态")
    @PutMapping("/anno/update/{id}")
    @SysLog("修改消息的状态")
    public R update(@ApiParam(value = "消息对应的Id", required = true)
                    @PathVariable("id") Long id) {
        //直接将消息状态修改为已查看
        newsNotifyService.update(NewsNotifyEntity.builder()
                .isSee(IsSeeEnum.IS_VIEWED)
                .build(), Wraps.lbQ(new NewsNotifyEntity())
                .eq(NewsNotifyEntity::getId, id));
        return success();
    }

    @ApiOperation(value = "一键全部标记为已读状态", notes = "一键全部标记为已读状态")
    @PutMapping("/update")
    @SysLog("一键全部标记为已读状态")
    public R update() {
        newsNotifyService.update(NewsNotifyEntity.builder()
                .isSee(IsSeeEnum.IS_VIEWED)
                .build(), Wraps.lbQ(new NewsNotifyEntity())
                .eq(NewsNotifyEntity::getUserId, getUserId()));
        return success();
    }

    @ApiOperation(value = "根据Id删除对应的消息", notes = "根据Id删除对应的消息")
    @DeleteMapping("/anno/delete/{id}")
    @SysLog("根据Id删除对应的消息")
    public R delete(@ApiParam(value = "消息对应的Id", required = true)
                    @PathVariable("id") Long id) {
        newsNotifyService.removeById(id);
        return success();
    }

    @ApiOperation(value = "删除全部自己的消息", notes = "删除全部自己的消息")
    @DeleteMapping("/delete")
    @SysLog("删除全部自己的消息")
    public R deleteAll() {
        newsNotifyService.remove(Wraps.lbQ(new NewsNotifyEntity())
                .eq(NewsNotifyEntity::getUserId, getUserId()));
        return success();
    }
}
