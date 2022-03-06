package com.qingfeng.controller;

import com.qingfeng.entity.Organize;
import com.qingfeng.service.OrganizeService;
import com.qingfeng.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 社团组织控制层
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/3/3
 */
@RestController
@RequestMapping("/organize")
@Api(value = "提供社团组织管理模块的功能",tags = "社团组织管理层接口")
@CrossOrigin
public class OrganizeController {

    @Autowired
    private OrganizeService organizeService;

    @ApiOperation("添加或修改社团组织详情接口")
    @PostMapping("/updateOrganizeInfo/{uid}")
    public ResultVO updateOrganizeInfo(@PathVariable("uid") Integer uid, @RequestBody Organize organize){
        //添加或保存社团组织详情
        return organizeService.updateOrganizeInfo(uid,organize);
    }
}
