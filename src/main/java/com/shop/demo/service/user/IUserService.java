package com.shop.demo.service.user;

import com.shop.demo.dto.UserDto;
import com.shop.demo.model.User;
import com.shop.demo.request.CreateUserRequest;
import com.shop.demo.request.UserUpdateRequest;


public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}