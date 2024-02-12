package com.baima.music.service.impl;

import com.baima.music.entity.User;
import com.baima.music.enums.ResponseType;
import com.baima.music.enums.RoleName;
import com.baima.music.exception.BizException;
import com.baima.music.mapper.UserMapper;
import com.baima.music.repository.UserRepository;
import com.baima.music.request.ChangePasswordRequest;
import com.baima.music.request.UserCreateRequest;
import com.baima.music.request.UserSearchFilter;
import com.baima.music.request.UserUpdateRequest;
import com.baima.music.service.UserService;
import com.baima.music.utils.JWTUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    @Getter(value = AccessLevel.PROTECTED)
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return getByName(authentication.getName());
    }

    @Override
    public String login(String username, String password) {
        var user = getByName(username);
        // 校验密码
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException(ResponseType.USER_PASSWORD_NOT_MATCH.getMessage());
        }
        // 是否开启
        if (!user.isEnabled()) {
            throw new DisabledException(ResponseType.USER_NOT_ENABLED.getMessage());
        }
        // 是否锁定
        if (!user.isAccountNonLocked()) {
            throw new LockedException(ResponseType.USER_LOCKED.getMessage());
        }
        // 生成jwtToken
        return JWTUtil.sign(username, user.getRoles());
    }

    @Override
    public User create(UserCreateRequest userCreateRequest) {
        var username = userCreateRequest.getUsername();
        // 校验验证码
        if (!JWTUtil.validateCaptcha(userCreateRequest.getToken(), userCreateRequest.getCaptcha(), username)) {
            throw new BizException(ResponseType.CAPTCHA_INVALID);
        }
        // 检查是否重复
        if (repository.findByUsername(username).isPresent()) {
            throw new BizException(ResponseType.USER_NAME_DUPLICATE);
        }
        var user = mapper.createEntity(userCreateRequest);
        var nickname = userCreateRequest.getNickname();
        // 没有昵称使用邮箱名做昵称
        if (!StringUtils.hasText(nickname)) {
            user.setNickname(username.split("@")[0]);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 设置为USER权限
        user.setRoles(RoleName.ROLE_USER.name());
        return repository.save(user);
    }

    @Override
    public Page<User> search(UserSearchFilter userSearchFilter) {
        if (StringUtils.hasText(userSearchFilter.getNickname())) {
            var nicknameMatch = "%" + userSearchFilter.getNickname() + "%";
            return repository.findByNicknameLikeIgnoreCase(nicknameMatch, userSearchFilter.toPageable());
        } else {
            return repository.findAll(userSearchFilter.toPageable());
        }
    }

    @Override
    public User update(UserUpdateRequest userUpdateRequest) {
        var user = getCurrentUser();
        mapper.updateEntity(userUpdateRequest, user);
        return repository.save(user);
    }

    @Override
    public void delete(String id) {
        // 别把自己删了
        if (Objects.equals(getCurrentUser().getId(), id)) return;
        //不直接删除，关闭账户即可
        //repository.delete(getById(id));
        var user = getById(id);
        user.setEnabled(false);
        repository.save(user);
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        var user = getCurrentUser();
        var username = user.getUsername();
        // 校验
        if (!JWTUtil.validateCaptcha(changePasswordRequest.getToken(), changePasswordRequest.getCaptcha(), username)) {
            throw new BizException(ResponseType.CAPTCHA_INVALID);
        }
        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new BizException(ResponseType.OLD_PASSWORD_NOT_MATCH);
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        repository.save(user);
    }

    @Override
    public void setAdmin(String id, Boolean isAdmin) {
        // 别把自己降维打击了
        if (Objects.equals(getCurrentUser().getId(), id)) return;
        var user = getById(id);
        // 是否开启
        if (!user.isEnabled()) {
            throw new DisabledException(ResponseType.USER_NOT_ENABLED.getMessage());
        }
        // 是否锁定
        if (!user.isAccountNonLocked()) {
            throw new LockedException(ResponseType.USER_LOCKED.getMessage());
        }
        if (isAdmin) {
            user.setRoles(String.join(",", RoleName.ROLE_USER.name(), RoleName.ROLE_ADMIN.name()));
        } else {
            user.setRoles(String.join(",", RoleName.ROLE_USER.name()));
        }
        repository.save(user);
    }

    private User getByName(String username) {
        // 查询用户是否存在，不存在直接抛出异常
        Optional<User> user = repository.findByUsername(username);
        return user.orElseThrow(() -> new BizException(getNotFoundExceptionType()));
    }

    @Override
    public User getById(String id) {
        Optional<User> user = repository.findById(id);
        return user.orElseThrow(() -> new BizException(getNotFoundExceptionType()));
    }

    protected ResponseType getNotFoundExceptionType() {
        return ResponseType.USER_NOT_FOUND;
    }

    @Override
    public User loadUserByUsername(String username) {
        return repository.findByUsername(username).orElse(null);
    }

}
