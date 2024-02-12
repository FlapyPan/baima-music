package com.baima.music.controller;

import com.baima.music.entity.User;
import com.baima.music.request.*;
import com.baima.music.service.UserService;
import com.baima.music.utils.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "UserController", description = "用户相关接口")
@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Operation(summary = "登录获取token")
    @PostMapping("/login")
    public GlobalResponse<String> login(@Validated @RequestBody LoginRequest loginRequest) {
        return GlobalResponse.ok(userService.login(loginRequest.getUsername(), loginRequest.getPassword()));
    }

    @Operation(summary = "注册用户")
    @PostMapping
    public GlobalResponse<User> create(@Validated @RequestBody UserCreateRequest userCreateRequest) {
        return GlobalResponse.ok(userService.create(userCreateRequest));
    }

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/me")
    public GlobalResponse<User> me() {
        return GlobalResponse.ok(userService.getCurrentUser());
    }

    @Operation(summary = "修改个人信息")
    @PutMapping
    public GlobalResponse<User> update(@Validated @RequestBody UserUpdateRequest userUpdateRequest) {
        return GlobalResponse.ok(userService.update(userUpdateRequest));
    }

    @Operation(summary = "修改密码")
    @PutMapping("/password")
    public GlobalResponse<Void> changePassword(@Validated @RequestBody ChangePasswordRequest changePasswordRequest) {
        userService.changePassword(changePasswordRequest);
        return GlobalResponse.ok(null);
    }

    @Operation(summary = "搜索用户")
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse<Page<User>> search(@Validated UserSearchFilter userSearchFilter) {
        return GlobalResponse.ok(userService.search(userSearchFilter));
    }

    @Operation(summary = "获取指定id用户")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse<User> get(@PathVariable String id) {
        return GlobalResponse.ok(userService.getById(id));
    }

    @Operation(summary = "设置用户是否为管理员")
    @PutMapping("/{id}/set-admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse<Void> setUserAdmin(@PathVariable String id, @RequestParam("isAdmin") boolean isAdmin) {
        userService.setAdmin(id, isAdmin);
        return GlobalResponse.ok(null);
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse<Void> delete(@PathVariable String id) {
        userService.delete(id);
        return GlobalResponse.ok(null);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
