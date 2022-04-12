/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.foundi.admin.system.entity.domain.GroupDo;
import net.foundi.admin.system.entity.domain.MenuDo;
import net.foundi.admin.system.entity.domain.UserDo;
import net.foundi.admin.system.entity.dto.*;
import net.foundi.admin.system.entity.query.LoginLogQuery;
import net.foundi.admin.system.entity.query.UserQuery;
import net.foundi.admin.system.service.GroupService;
import net.foundi.admin.system.service.MenuService;
import net.foundi.admin.system.service.OAuthUserService;
import net.foundi.admin.system.service.UserService;
import net.foundi.common.utils.web.MultipartUtils;
import net.foundi.framework.entity.dto.TreeDto;
import net.foundi.framework.entity.validation.AddGroup;
import net.foundi.framework.entity.validation.EditGroup;
import net.foundi.framework.entity.validation.PhoneValid;
import net.foundi.framework.entity.validation.UsernameValid;
import net.foundi.framework.log.Log;
import net.foundi.framework.security.SecurityUtils;
import net.foundi.framework.web.BaseController;
import net.foundi.framework.web.WebReturn;
import net.foundi.framework.web.limit.Limit;
import net.foundi.framework.web.resubmit.Resubmit;
import net.foundi.support.oauth.config.OAuthType;
import net.foundi.support.oauth.qq.QqOAuthService;
import net.foundi.support.oauth.weibo.WeiboOAuthService;
import net.foundi.support.oauth.weixin.WeixinOAuthService;
import net.foundi.support.sms.service.SmsValidator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 系统用户Controller
 *
 * @author Afeng
 */
@Api(tags = "系统用户管理")
@RestController
@Validated
@RequestMapping("/system/user")
public class UserController extends BaseController {

    private final UserService userService;
    private final MenuService menuService;
    private final GroupService groupService;
    private final SmsValidator smsValidator;
    private final OAuthUserService oAuthUserService;
    private final WeixinOAuthService weixinOAuthService;
    private final QqOAuthService qqOAuthService;
    private final WeiboOAuthService weiboOAuthService;

    public UserController(UserService userService, MenuService menuService, GroupService groupService,
                          SmsValidator smsValidator, OAuthUserService oAuthUserService,
                          WeixinOAuthService weixinOAuthService, QqOAuthService qqOAuthService,
                          WeiboOAuthService weiboOAuthService) {
        this.userService = userService;
        this.menuService = menuService;
        this.groupService = groupService;
        this.smsValidator = smsValidator;
        this.oAuthUserService = oAuthUserService;
        this.weixinOAuthService = weixinOAuthService;
        this.qqOAuthService = qqOAuthService;
        this.weiboOAuthService = weiboOAuthService;
    }

    @ApiOperation("查询系统用户")
    @GetMapping("/{id}")
    @PreAuthorize("@authz.hasPerm('system:user:get')")
    public WebReturn getOne(@PathVariable("id") Long id) {
        UserDo aDo = userService.getById(id);
        return WebReturn.ok().content(UserDto.fromDo(aDo));
    }

    @ApiOperation("查询系统用户")
    @GetMapping
    @PreAuthorize("@authz.hasPerm('system:user:list')")
    public WebReturn list(UserQuery query) {
        IPage<UserDo> page = userService.page(getPage(), query);
        return WebReturn.ok()
                .page(page)
                .content(UserDto.fromDo(page.getRecords()));
    }

    @ApiOperation("新增系统用户")
    @PostMapping
    @PreAuthorize("@authz.hasPerm('system:user:add')")
    @Resubmit
    @Log("新增系统用户")
    public WebReturn add(@RequestBody @Validated({AddGroup.class}) UserDto dto) {
        UserDo aDo = UserDto.toDo(dto);
        UserDo newDo = userService.saveAndGet(aDo);
        UserDto result = UserDto.fromDo(newDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("修改系统用户")
    @PutMapping
    @PreAuthorize("@authz.hasPerm('system:user:edit')")
    @Resubmit
    @Log("修改系统用户")
    public WebReturn edit(@RequestBody @Validated({EditGroup.class}) UserDto dto) {
        UserDo aDo = UserDto.toDo(dto);
        UserDo newDo = userService.updateAndGet(aDo);
        UserDto result = UserDto.fromDo(newDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("删除系统用户")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@authz.hasPerm('system:user:delete')")
    @Log("删除系统用户")
    public WebReturn deleteMulti(@PathVariable("ids") List<Long> ids) {
        userService.removeBatch(ids);
        return WebReturn.ok();
    }

    @ApiOperation("导出当前页数据")
    @GetMapping(value = "/exportPage")
    @PreAuthorize("@authz.hasPerm('system:loginLog:export')")
    public void exportPage(HttpServletResponse rep, LoginLogQuery query) throws IOException {
        IPage<UserDo> page = userService.page(getPage(), query);
        MultipartUtils.downloadExcel(UserDto.toMap(page.getRecords()), rep);
    }

    @ApiOperation("导出全部数据")
    @GetMapping(value = "/exportAll")
    @PreAuthorize("@authz.hasPerm('system:loginLog:export')")
    public void exportAll(HttpServletResponse rep, LoginLogQuery query) throws IOException {
        MultipartUtils.downloadExcel(UserDto.toMap(userService.list(query)), rep);
    }

    @ApiOperation("检测用户名")
    @GetMapping("/username/check")
    @PreAuthorize("@authz.hasPerm('system:user:edit')")
    public WebReturn checkUsername(@RequestParam(required = false) Long id,
                                   @RequestParam @UsernameValid String username) {
        return WebReturn.ok()
                .content(userService.checkNoDuplicate(id, UserDo::getUsername, username));
    }

    @ApiOperation("检测用户EMail")
    @GetMapping("/email/check")
    @PreAuthorize("@authz.hasPerm('system:user:edit')")
    public WebReturn checkEmail(@RequestParam(required = false) Long id,
                                @RequestParam @Email String email) {
        return WebReturn.ok()
                .content(userService.checkNoDuplicate(id, UserDo::getEmail, email));
    }

    @ApiOperation("检测用户手机号")
    @GetMapping("/mobile/check")
    @PreAuthorize("@authz.hasPerm('system:user:edit')")
    public WebReturn checkMobile(@RequestParam(required = false) Long id,
                                 @RequestParam @PhoneValid String mobile) {
        return WebReturn.ok()
                .content(userService.checkNoDuplicate(id, UserDo::getMobile, mobile));
    }

    @ApiOperation("更改密码")
    @PutMapping("/password")
    @PreAuthorize("@authz.hasPerm('system:user:edit')")
    public WebReturn changePassword(@RequestBody @Validated PasswordDto dto) {
        userService.updatePassword(dto.getId(), dto.getOldPass(), dto.getPassword());
        return WebReturn.ok();
    }

    //************************************************************
    //*  当前用户信息
    //************************************************************

    @ApiOperation("获取当前用户")
    @GetMapping("/current/info")
    @PreAuthorize("isAuthenticated()")
    public WebReturn getInfo() {
        Long userId = SecurityUtils.getCurrentUserOrEx().getId();
        UserDo aDo = userService.getById(userId);
        UserDto dto = userService.setOAuth(UserDto.fromDo(aDo));
        List<MenuDo> menus = menuService.getMenusByRoleIds(dto.getRoleIdList());
        List<String> perms = menuService.getPermsByRoleIds(dto.getRoleIdList());
        List<GroupDo> groups = groupService.getGroupsByRoleIds(dto.getRoleIdList());
        return WebReturn.ok()
                .content("user", UserDto.fromDo(aDo))
                .content("roles", RoleDto.fromDo(aDo.getRoleList()))
                .content("groups", TreeDto.buildTree(GroupDto.fromDo(groups)))
                .content("menu", TreeDto.buildTree(MenuDto.fromDo(menus)))
                .content("perms", perms);
    }

    @ApiOperation("修改当前用户")
    @PutMapping("/current")
    @PreAuthorize("isAuthenticated()")
    @Log("修改当前用户")
    public WebReturn CurrentUserEdit(@RequestBody UserDto dto) {
        Long userId = SecurityUtils.getCurrentUserOrEx().getId();
        UserDo aDo = UserDto.toDo(dto);
        aDo.setId(userId);
        UserDo newDo = userService.updateAndGet(aDo);
        return WebReturn.ok()
                .content(UserDto.fromDo(newDo));
    }

    @ApiOperation("修改当前用户头像")
    @PutMapping("/current/avatar")
    @PreAuthorize("isAuthenticated()")
    @Log("修改当前用户头像")
    public WebReturn CurrentUserChangeAvatar(@RequestParam @NotBlank(message = "验证码不能为空") String avatar) {
        Long userId = SecurityUtils.getCurrentUserOrEx().getId();
        UserDo newDo = new UserDo();
        newDo.setId(userId);
        newDo.setAvatar(avatar);
        userService.update(newDo);
        return WebReturn.ok();
    }

    @ApiOperation("更改当前用户密码")
    @PutMapping("/current/password")
    @PreAuthorize("isAuthenticated()")
    @Log("更改当前用户密码")
    public WebReturn CurrentUserChangePassword(@RequestBody PasswordDto dto) {
        userService.updatePassword(SecurityUtils.getCurrentUserOrEx().getId(), dto.getOldPass(), dto.getPassword());
        return WebReturn.ok();
    }

    @ApiOperation("当前用户添加密码")
    @PostMapping("/current/password")
    @PreAuthorize("isAuthenticated()")
    @Log("当前用户添加密码")
    public WebReturn CurrentUserAddPassword(@RequestBody PasswordDto dto) {
        userService.updatePassword(SecurityUtils.getCurrentUserOrEx().getId(), dto.getOldPass(), dto.getPassword());
        return WebReturn.ok();
    }

    @ApiOperation("当前用户检测用户名")
    @GetMapping("/current/username/check")
    @PreAuthorize("isAuthenticated()")
    public WebReturn currentCheckUsername(@RequestParam @UsernameValid String username) {
        Long userId = SecurityUtils.getCurrentUserOrEx().getId();
        return WebReturn.ok()
                .content(userService.checkNoDuplicate(userId, UserDo::getUsername, username));
    }

    @ApiOperation("当前用户检测手机号")
    @GetMapping("/current/mobile/check")
    @PreAuthorize("isAuthenticated()")
    public WebReturn currentCheckMobile(@RequestParam @PhoneValid String mobile) {
        Long userId = SecurityUtils.getCurrentUserOrEx().getId();
        return WebReturn.ok()
                .content(userService.checkNoDuplicate(userId, UserDo::getMobile, mobile));
    }

    @ApiOperation("当前用户获取手机验证码")
    @GetMapping("/current/mobile/valid")
    @PreAuthorize("isAuthenticated()")
    @Resubmit(lockKey = "#mobile", interval = 10)
    @Limit(checkFor = Limit.CheckTarget.IP, maxRate = 60, duration = 60L, forbiddenTime = 60 * 10L)
    @Log("当前用户获取手机验证码")
    public WebReturn currentChangeMobileValid(@RequestParam @PhoneValid @NotBlank(message = "手机号不能为空") String mobile) {
        smsValidator.sendCode(mobile);
        return WebReturn.ok();
    }

    @ApiOperation("当前用户修改手机号")
    @PutMapping("/current/mobile")
    @PreAuthorize("isAuthenticated()")
    @Resubmit
    @Log("当前用户修改手机号")
    public WebReturn currentChangeMobile(@RequestParam @NotBlank(message = "验证码不能为空") String code,
                                         @RequestParam @PhoneValid String mobile) {
        Long userId = SecurityUtils.getCurrentUserOrEx().getId();
        smsValidator.verifyCode(mobile, code);
        UserDo newDo = new UserDo();
        newDo.setId(userId);
        newDo.setMobile(mobile);
        userService.update(newDo);
        return WebReturn.ok();
    }

    @ApiOperation("当前用户解绑手机号")
    @GetMapping("/current/mobile/clear")
    @PreAuthorize("isAuthenticated()")
    @Log("当前用户解绑手机号")
    public WebReturn currentClearMobile() {
        Long userId = SecurityUtils.getCurrentUserOrEx().getId();
        UserDo newDo = new UserDo();
        newDo.setId(userId);
        newDo.setMobile("");
        userService.update(newDo);
        return WebReturn.ok();
    }

    @ApiOperation("当前用户检测邮箱是否可用")
    @GetMapping("/current/email/check")
    @PreAuthorize("isAuthenticated()")
    public WebReturn currentCheckEmail(@RequestParam @Email String email) {
        Long userId = SecurityUtils.getCurrentUserOrEx().getId();
        return WebReturn.ok()
                .content(userService.checkNoDuplicate(userId, UserDo::getEmail, email));
    }

    @ApiOperation("当前用户更换邮箱")
    @PutMapping("/current/email")
    @PreAuthorize("isAuthenticated()")
    @Resubmit
    @Log("当前用户更换邮箱")
    public WebReturn currentChangeEmail(@RequestParam @Email String email) {
        Long userId = SecurityUtils.getCurrentUserOrEx().getId();
        UserDo newDo = new UserDo();
        newDo.setId(userId);
        newDo.setEmail(email);
        userService.update(newDo);
        return WebReturn.ok();
    }

    @ApiOperation("当前用户解绑邮箱")
    @GetMapping("/current/email/clear")
    @PreAuthorize("isAuthenticated()")
    @Log("当前用户解绑邮箱")
    public WebReturn currentClearEmail() {
        Long userId = SecurityUtils.getCurrentUserOrEx().getId();
        UserDo newDo = new UserDo();
        newDo.setId(userId);
        newDo.setEmail("");
        userService.update(newDo);
        return WebReturn.ok();
    }

    @ApiOperation("当前用户绑定微信")
    @GetMapping("/current/weixin")
    @PreAuthorize("isAuthenticated()")
    @Log(value = "当前用户绑定微信", logAfter = Log.LogAfter.EXCEPTION)
    public WebReturn currentBindWeixin(@RequestParam String state) {
        return WebReturn.ok()
                .content(oAuthUserService.currentBindOAuth(weixinOAuthService, state, OAuthType.WEIXIN));
    }

    @ApiOperation("当前用户解绑微信")
    @GetMapping("/current/weixin/clear")
    @PreAuthorize("isAuthenticated()")
    @Log("当前用户解绑微信")
    public WebReturn currentClearWeixin() {
        Long userId = SecurityUtils.getCurrentUserOrEx().getId();
        oAuthUserService.unbindFromUser(userId, OAuthType.WEIXIN);
        return WebReturn.ok();
    }

    @ApiOperation("当前用户绑定QQ")
    @GetMapping("/current/qq")
    @PreAuthorize("isAuthenticated()")
    @Log(value = "当前用户绑定QQ", logAfter = Log.LogAfter.EXCEPTION)
    public WebReturn currentBindQQ(@RequestParam String state) {
        return WebReturn.ok()
                .content(oAuthUserService.currentBindOAuth(qqOAuthService, state, OAuthType.QQ));
    }

    @ApiOperation("当前用户解绑QQ")
    @GetMapping("/current/qq/clear")
    @PreAuthorize("isAuthenticated()")
    @Log("当前用户解绑QQ")
    public WebReturn currentClearQQ() {
        Long userId = SecurityUtils.getCurrentUserOrEx().getId();
        oAuthUserService.unbindFromUser(userId, OAuthType.QQ);
        return WebReturn.ok();
    }

    @ApiOperation("当前用户绑定微博")
    @GetMapping("/current/weibo")
    @PreAuthorize("isAuthenticated()")
    @Log(value = "当前用户绑定微博", logAfter = Log.LogAfter.EXCEPTION)
    public WebReturn currentBindWeibo(@RequestParam String state) {
        return WebReturn.ok()
                .content(oAuthUserService.currentBindOAuth(weiboOAuthService, state, OAuthType.WEIBO));
    }

    @ApiOperation("当前用户解绑微博")
    @GetMapping("/current/weibo/clear")
    @PreAuthorize("isAuthenticated()")
    @Log("当前用户解绑微博")
    public WebReturn currentClearWeibo() {
        Long userId = SecurityUtils.getCurrentUserOrEx().getId();
        oAuthUserService.unbindFromUser(userId, OAuthType.WEIBO);
        return WebReturn.ok();
    }

    //************************************************************
    //*  用户组
    //************************************************************

    @ApiOperation("获取特定用户组的用户")
    @GetMapping("/group/{id}")
    @PreAuthorize("@authz.hasPerm('system:user:list')")
    public WebReturn listByGroup(@PathVariable("id") Long groupId) {
        List<UserDo> result = userService.list(new QueryWrapper<UserDo>().lambda()
                .eq(UserDo::getGroupId, groupId));
        return WebReturn.ok()
                .content(UserDto.fromDo(result));
    }

    @ApiOperation("获取多个用户组的用户")
    @GetMapping("/id/groups/{ids}")
    @PreAuthorize("@authz.hasPerm('system:user:list')")
    public WebReturn listByGroup(@PathVariable("ids") Long[] groupIds) {
        List<Long> descList = groupService.getSubGroupIds(Arrays.asList(groupIds));
        List<UserDo> result = userService.list(new QueryWrapper<UserDo>().lambda()
                .in(UserDo::getGroupId, descList));
        return WebReturn.ok()
                .content(result.stream().map(UserDo::getId));
    }

}
