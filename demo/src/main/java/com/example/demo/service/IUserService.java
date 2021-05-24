package com.example.demo.service;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;

public interface IUserService {

    UserResponse createUser(UserRequest request) throws Exception;
}
