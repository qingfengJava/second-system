package com.qingfeng.currency.authority.controller.auth;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qingfeng.cms.domain.college.dto.CollegeInformationSaveDTO;
import com.qingfeng.cms.domain.college.dto.CollegeInformationUpdateDTO;
import com.qingfeng.cms.domain.college.entity.CollegeInformationEntity;
import com.qingfeng.currency.authority.biz.service.auth.RoleService;
import com.qingfeng.currency.authority.biz.service.auth.UserService;
import com.qingfeng.currency.authority.biz.service.core.OrgService;
import com.qingfeng.currency.authority.biz.service.core.StationService;
import com.qingfeng.currency.authority.dto.auth.UserPageDTO;
import com.qingfeng.currency.authority.dto.auth.UserRoleDTO;
import com.qingfeng.currency.authority.dto.auth.UserSaveDTO;
import com.qingfeng.currency.authority.dto.auth.UserUpdateAvatarDTO;
import com.qingfeng.currency.authority.dto.auth.UserUpdateDTO;
import com.qingfeng.currency.authority.dto.auth.UserUpdatePasswordDTO;
import com.qingfeng.currency.authority.entity.auth.Role;
import com.qingfeng.currency.authority.entity.auth.User;
import com.qingfeng.currency.authority.entity.core.Org;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.base.entity.SuperEntity;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.database.mybatis.conditions.query.LbqWrapper;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.log.annotation.SysLog;
import com.qingfeng.currency.user.feign.UserQuery;
import com.qingfeng.currency.user.model.SysOrg;
import com.qingfeng.currency.user.model.SysRole;
import com.qingfeng.currency.user.model.SysStation;
import com.qingfeng.currency.user.model.SysUser;
import com.qingfeng.sdk.messagecontrol.collegeinformation.CollegeInformationApi;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 前端控制器
 * 用户
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/18
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/user")
@Api(value = "User", tags = "用户")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private StationService stationService;
    @Autowired
    private DozerUtils dozer;

    @Autowired
    private CollegeInformationApi collegeInformationApi;

    @ApiOperation(value = "分页查询用户", notes = "分页查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "页码", dataType = "long", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "分页条数", dataType = "long", paramType = "query", defaultValue = "10"),
    })
    @GetMapping("/page")
    @SysLog("分页查询用户")
    public R<IPage<User>> page(UserPageDTO userPage) {
        IPage<User> page = getPage();

        User user = dozer.map2(userPage, User.class);
        if (userPage.getOrgId() != null && userPage.getOrgId() >= 0) {
            user.setOrgId(null);
        }
        LbqWrapper<User> wrapper = Wraps.lbQ(user);
        if (userPage.getOrgId() != null && userPage.getOrgId() >= 0) {
            List<Org> children = orgService.findChildren(Arrays.asList(userPage.getOrgId()));
            wrapper.in(User::getOrgId, children.stream().mapToLong(Org::getId).boxed().collect(Collectors.toList()));
        }
        wrapper.geHeader(User::getCreateTime, userPage.getStartCreateTime())
                .leFooter(User::getCreateTime, userPage.getEndCreateTime())
                .like(User::getName, userPage.getName())
                .like(User::getAccount, userPage.getAccount())
                .like(User::getEmail, userPage.getEmail())
                .like(User::getMobile, userPage.getMobile())
                .eq(User::getSex, userPage.getSex())
                .eq(User::getStatus, userPage.getStatus())
                .orderByDesc(User::getId);

        userService.findPage(page, wrapper);
        return success(page);
    }

    @ApiOperation(value = "查询用户", notes = "查询用户")
    @GetMapping("/{id}")
    @SysLog("查询用户")
    public R<User> get(@PathVariable Long id) {
        return success(userService.getById(id));
    }

    @ApiOperation(value = "查询所有用户", notes = "查询所有用户")
    @GetMapping("/find")
    @SysLog("查询所有用户")
    public R<List<Long>> findAllUserId() {
        return success(
                userService
                        .list()
                        .stream()
                        .mapToLong(User::getId)
                        .boxed()
                        .collect(Collectors.toList()));
    }

    @ApiOperation(value = "新增用户", notes = "新增用户不为空的字段")
    @PostMapping
    @SysLog("新增用户")
    public R<User> save(@RequestBody @Validated UserSaveDTO data) {
        User user = dozer.map(data, User.class);
        userService.saveUser(user);
        return success(user);
    }

    @ApiOperation(value = "修改用户", notes = "修改用户不为空的字段")
    @PutMapping
    @SysLog("修改用户")
    public R<User> update(@RequestBody @Validated(SuperEntity.Update.class) UserUpdateDTO data) {
        User user = dozer.map(data, User.class);
        userService.updateUser(user);
        return success(user);
    }

    @ApiOperation(value = "修改头像", notes = "修改头像")
    @PutMapping("/avatar")
    @SysLog("修改头像")
    public R<User> avatar(@RequestBody @Validated(SuperEntity.Update.class) UserUpdateAvatarDTO data) {
        User user = dozer.map(data, User.class);
        userService.updateUser(user);
        return success(user);
    }

    @ApiOperation(value = "修改密码", notes = "修改密码")
    @PutMapping("/password")
    @SysLog("修改密码")
    public R<Boolean> updatePassword(@RequestBody UserUpdatePasswordDTO data) {
        return success(userService.updatePassword(data));
    }

    @ApiOperation(value = "重置密码", notes = "重置密码")
    @GetMapping("/reset")
    @SysLog("重置密码")
    public R<Boolean> resetTx(@RequestParam("ids[]") List<Long> ids) {
        userService.reset(ids);
        return success();
    }

    @ApiOperation(value = "删除用户", notes = "根据id物理删除用户")
    @DeleteMapping
    @SysLog("删除用户")
    public R<Boolean> delete(@RequestParam("ids[]") List<Long> ids) {
        userService.remove(ids);
        return success(true);
    }

    @ApiOperation(value = "查询用户详细", notes = "查询用户详细")
    @PostMapping(value = "/anno/id/{id}")
    public R<SysUser> getById(@PathVariable Long id, @RequestBody UserQuery query) {
        User user = userService.getById(id);
        if (user == null) {
            return success(null);
        }
        SysUser sysUser = dozer.map(user, SysUser.class);

        if (query.getFull() || query.getOrg()) {
            sysUser.setOrg(dozer.map(orgService.getById(user.getOrgId()), SysOrg.class));
        }
        if (query.getFull() || query.getStation()) {
            sysUser.setStation(dozer.map(stationService.getById(user.getStationId()), SysStation.class));
        }

        if (query.getFull() || query.getRoles()) {
            List<Role> list = roleService.findRoleByUserId(id);
            sysUser.setRoles(dozer.mapList(list, SysRole.class));
        }

        return success(sysUser);
    }

    @ApiOperation(value = "查询角色的已关联用户", notes = "查询角色的已关联用户")
    @GetMapping(value = "/role/{roleId}")
    public R<UserRoleDTO> findUserByRoleId(@ApiParam(value = "角色Id", required = true) @PathVariable("roleId") Long roleId,
                                           @ApiParam(value = "账号account或名称name", required = true) @RequestParam(value = "keyword", required = false) String keyword) {
        List<User> list = userService.findUserByRoleId(roleId, keyword);
        List<Long> idList = list.stream().mapToLong(User::getId).boxed().collect(Collectors.toList());
        return success(UserRoleDTO.builder().idList(idList).userList(list).build());
    }

    @ApiOperation(value = "根据用户Id查询用户关联的二级学院的信息", notes = "根据用户Id查询用户关联的二级学院的信息")
    @GetMapping("/info/{userId}")
    @SysLog("根据用户Id查询用户关联的二级学院的信息")
    public R<CollegeInformationEntity> getCollegeInfo(@ApiParam(value = "用户Id", required = true)
                                            @PathVariable("userId") @NotNull Long userId) {
        System.out.println(getUserId());
        return collegeInformationApi.info(userId);
    }

    @ApiOperation(value = "保存用户关联的二级学院的信息", notes = "保存用户关联的二级学院的信息")
    @PostMapping("/save")
    @SysLog("保存用户关联的二级学院的信息")
    public R saveCollegeInformation(@ApiParam(value = "院系信息保存实体", required = true)
                  @RequestBody @Validated CollegeInformationSaveDTO collegeInformationSaveDTO) {
        return collegeInformationApi.save(collegeInformationSaveDTO);
    }

    @ApiOperation(value = "修改用户关联的二级学院的信息", notes = "修改用户关联的二级学院的信息")
    @PutMapping("/update")
    @SysLog("修改用户关联的二级学院的信息")
    public R updateCollegeInformation(@ApiParam(value = "数据字典实体")
                    @RequestBody @Validated(SuperEntity.Update.class) CollegeInformationUpdateDTO collegeInformationUpdateDTO) {
        return collegeInformationApi.update(collegeInformationUpdateDTO);
    }

    @ApiOperation(value = "导出用户（学生）Excel信息模板", notes = "导出用户（学生）Excel信息模板")
    @GetMapping("/excel/export_user_template")
    @SysLog("导出用户（学生）Excel信息模板")
    public void exportTemplate(HttpServletResponse response) {
        userService.exportUserTemplate(response);
    }

    @ApiOperation(value = "导出学生信息", notes = "导出学生信息")
    @GetMapping("/excel/export_user")
    @SysLog("导出学生信息Excel")
    public void exportDict(HttpServletResponse response) {
        userService.exportUser(response);
    }

    @ApiOperation(value = "学生信息Excel导入", notes = "学生信息Excel导入")
    @PostMapping("/excel/user/import")
    @SysLog("学生信息Excel导入")
    public R userImport(MultipartFile file) {
        userService.importUser(file);
        return success();
    }
}