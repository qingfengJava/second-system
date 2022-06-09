package com.qingfeng.controller;

import com.qingfeng.constant.ResStatus;
import com.qingfeng.dto.UserDto;
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
@Api(value = "提供社团组织管理模块的功能", tags = "社团组织管理层接口")
@CrossOrigin
public class OrganizeController {

    @Autowired
    private OrganizeService organizeService;

    /**
     * 文件路径
     */
    @Value("${photo.file.dir}")
    private String realPath;

    @ApiOperation("添加/修改社团组织接口")
    @PostMapping("/addOrganizeInfo/{uid}")
    public ResultVO addOrganizeInfo(@PathVariable("uid") Integer uid, @RequestBody Organize organize) {
        try {
            //添加或保存社团组织详情
            return organizeService.addOrganizeInfo(uid, organize);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVO(ResStatus.NO, "完善信息出现未知的异常！", null);
        }
    }

    @ApiOperation("查询社团组织详情信息")
    @PostMapping("/checkOrganizeInfo/{uid}")
    public ResultVO checkOrganizeInfo(@PathVariable("uid") Integer uid) {
        //根据用户主键Id查询社团组织信息详情，含轮播图信息
        return organizeService.checkOrganizeInfo(uid);
    }

    @ApiOperation("分页条件查询社团组织列表")
    @ApiImplicitParam(paramType = "string", name = "organizeName", value = "社团组织的名称", required = false)
    @GetMapping("/queryOrganize")
    public ResultVO queryOrganize(Integer pageNum, Integer limit, String organizeName) {
        //分页查询社团组织列表
        return organizeService.queryOrganize(pageNum, limit, organizeName);
    }

    @ApiOperation("根据用户类型查询组织名称")
    @GetMapping("/queryOrganizeName/{isAdmin}")
    public ResultVO queryOrganizeName(@PathVariable("isAdmin") Integer isAdmin) {
        //根据用户类型查询组织名称
        List<UserDto> list = organizeService.queryByIsAdmin(isAdmin);
        return new ResultVO(ResStatus.OK, "success", list);
    }

    @ApiOperation("添加或修改社团主图")
    @ApiImplicitParam(paramType = "string", name = "photoName", value = "旧图片名", required = true)
    @PostMapping("/addOrganizePhoto/import/{uid}")
    public void addOrganizePhoto(@PathVariable("uid") Integer uid, String photoName,MultipartFile file) {
        try {
            boolean notEmpty = (file != null);
            String newFileName = null;
            //不为空
            if (notEmpty) {
                //检查就照片是否存在，存在就将其删除掉，再保存新照片
                File newFile = new File(realPath, photoName);
                if (newFile.exists()) {
                    //如果文件存在，就删除文件
                    if (!photoName.equals("default.png")) {
                        //如果不是默认头像就删除老照片
                        newFile.delete();
                    }
                }
                newFileName = FileUtils.uploadFile(file, realPath);
            }
            organizeService.updateImg(uid, newFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("添加社团轮播图")
    @PostMapping("/addOrganizeImg/import/{organizeId}")
    public void addOrganizeImg(@PathVariable("organizeId") Integer organizeId,MultipartFile file) {
        try {
            //判断是否更新头像  空是true，表示没有更新头像
            boolean notEmpty = (file != null);
            String newFileName = null;
            //不为空
            if (notEmpty) {
                //处理新的头像上传   1、处理头像的上传 & 修改文件名
                newFileName = FileUtils.uploadFile(file, realPath);
            }
            //调用service层的上传头像的方法
            organizeService.addPhoto(organizeId, newFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("修改社团轮播图信息")
    @ApiImplicitParam(paramType = "string", name = "photoName", value = "旧图片名", required = true)
    @PostMapping("/updateOrganizeImg/import/{imgId}")
    public void updateOrganizeImg(@PathVariable("imgId") Integer imgId,String photoName,MultipartFile file){
        try {
            //判断是否更新头像  空是true，表示没有更新头像
            boolean notEmpty = (file != null);
            String newFileName = null;
            //不为空
            if (notEmpty) {
                //检查就照片是否存在，存在就将其删除掉，再保存新照片
                File newFile = new File(realPath, photoName);
                if (newFile.exists()) {
                    //如果文件存在，就删除文件
                    if (!photoName.equals("default.png")) {
                        //如果不是默认头像就删除老照片
                        newFile.delete();
                    }
                }
                //处理新的头像上传   1、处理头像的上传 & 修改文件名
                newFileName = FileUtils.uploadFile(file, realPath);
            }
            //调用service层的修改头像的方法
            organizeService.updateOrganizeImg(imgId, newFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("根据Id删除社团轮播图")
    @DeleteMapping("/deleteOrganizeImg/{imgId}")
    public ResultVO deleteOrganizeImg(@PathVariable("imgId") Integer imgId){
        return organizeService.deleteOrganizeImg(imgId);
    }
}
