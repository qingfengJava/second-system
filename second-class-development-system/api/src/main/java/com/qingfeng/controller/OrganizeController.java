package com.qingfeng.controller;

import com.qingfeng.constant.ResStatus;
import com.qingfeng.entity.Organize;
import com.qingfeng.service.OrganizeService;
import com.qingfeng.utils.FileUtils;
import com.qingfeng.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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

    @ApiOperation("添加或修改社团组织详情接口")
    @ApiImplicitParam(paramType = "string",name = "oldPhoto",value = "社团旧的主图的名字",required = true)
    @PostMapping("/updateOrganizeInfo/{uid}")
    public ResultVO updateOrganizeInfo(@PathVariable("uid") Integer uid,Organize organize,String oldPhoto, MultipartFile img){
        try {
            //判断是否更新头像  空是true，表示没有更新头像
            boolean notEempty = (img != null);
            //不为空
            if (notEempty){
                //检查就照片是否存在，存在就将其删除掉，再保存新照片
                File file = new File(realPath,oldPhoto);
                if (file.exists()) {
                    //如果文件存在，就删除文件
                    file.delete();
                }
                //处理新的头像上传   1、处理头像的上传 & 修改文件名
                String newFileName = FileUtils.uploadFile(img, realPath);
                //修改用户头像的信息
                organize.setMainUrl(newFileName);
            }else {
                //否则不修改头像
                organize.setMainUrl(oldPhoto);
            }
            //添加或保存社团组织详情
            return organizeService.updateOrganizeInfo(uid,organize);
        } catch (IOException e) {
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
    @ApiImplicitParam(paramType = "string",name = "organizeName",value = "社团组织的名称",required = true)
    @GetMapping("/queryOrganize")
    public ResultVO queryOrganize(Integer pageNum, Integer limit,String organizeName){
        //分页查询社团组织列表
        return organizeService.queryOrganize(pageNum, limit);
    }

}
