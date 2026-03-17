package com.lorenzoproject.slope.service.user;

import com.lorenzoproject.slope.dto.UserDto;
import com.lorenzoproject.slope.model.User;
import com.lorenzoproject.slope.request.CreateUserRequest;
import com.lorenzoproject.slope.request.UserUpdateRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);
    UserDto convertUserToDto(User user);
    User getAuthenticatedUser();

}
