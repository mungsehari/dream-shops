package com.hari.service.user;

import com.hari.dto.UserDto;
import com.hari.model.User;
import com.hari.request.CreateUserRequest;
import com.hari.request.UserUpdateRequest;

public interface UserService {

    User getUserById(Long UserId);

    User createUser(CreateUserRequest request);

    User updateUser(UserUpdateRequest request, Long userId);

    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}
