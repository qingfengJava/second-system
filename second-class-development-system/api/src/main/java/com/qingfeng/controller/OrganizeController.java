package com.qingfeng.controller;

import com.qingfeng.constant.ResStatus;
import com.qingfeng.dto.UserDto;
import com.qingfeng.entity.Organize;
import com.qingfeng.service.OrganizeService;
import com.qingfeng.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 文件路径
     */
    @Value("${photo.file.dir}")
    private String realPath;

    @ApiOperation("添加社团组织接口")
    @PostMapping("/addOrganizeInfo/{uid}")
    public ResultVO addOrganizeInfo(@PathVariable("uid") Integer uid,@RequestBody Organize organize){
        try {
            //添加或保存社团组织详情
            return organizeService.addOrganizeInfo(uid,organize);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVO(ResStatus.NO,"完善信息出现未知的异常！",null);
        }
    }

    @ApiOperation("查询社团组织详情信息")
    @PostMapping("/checkOrganizeInfo/{uid}")
    public ResultVO checkOrganizeInfo(@PathVariable("uid")Integer uid){
        //根据用户主键Id查询社团组织信息详情，含轮播图信息
        return organizeService.checkOrganizeInfo(uid);
    }

    @ApiOperation("分页条件查询社团组织列表")
    @ApiImplicitParam(paramType = "string",name = "organizeName",value = "社团组织的名称",required = false)
    @GetMapping("/queryOrganize")
    public ResultVO queryOrganize(Integer pageNum, Integer limit,String organizeName){
        //分页查询社团组织列表
        return organizeService.queryOrganize(pageNum, limit,organizeName);
    }

    @ApiOperation("根据用户类型查询组织名称")
    @GetMapping("/queryOrganizeName/{isAdmin}")
    public ResultVO queryOrganizeName(@PathVariable("isAdmin")Integer isAdmin){
        //根据用户类型查询组织名称
        List<UserDto> list = organizeService.queryByIsAdmin(isAdmin);
        return new ResultVO(ResStatus.OK,"success",list);
    }
}
