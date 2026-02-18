package com.app.ecom.service;

import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<UserResponse> fetchAllUser();
    public Optional<UserResponse> fetchUserById(Long id);
    public void addUser(UserRequest userRequest);
    public boolean updateUser(Long id, UserRequest user);
    public  boolean deleteUser(long userId);
}
