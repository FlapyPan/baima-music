package com.baima.music.service;

import com.baima.music.entity.User;
import com.baima.music.request.ChangePasswordRequest;
import com.baima.music.request.UserCreateRequest;
import com.baima.music.request.UserSearchFilter;
import com.baima.music.request.UserUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends GeneralService<User>, UserDetailsService {
    User getCurrentUser();

    String login(String username, String password);

    User create(UserCreateRequest userCreateRequest);

    User update(UserUpdateRequest userUpdateRequest);

    void delete(String id);

    Page<User> search(UserSearchFilter searchFilter);

    void changePassword(ChangePasswordRequest changePasswordRequest);

    void setAdmin(String id, Boolean isAdmin);
}
