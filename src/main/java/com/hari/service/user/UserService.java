package com.hari.service.user;

import com.hari.model.User;
import com.hari.request.CreateUserRequest;
import com.hari.request.UserUpdateRequest;

public interface UserService {

    User getUserById(Long UserId);

    User CreateUser(CreateUserRequest request);

    User updateUser(UserUpdateRequest request, Long userId);

    void deleteUser(Long userId);

}
