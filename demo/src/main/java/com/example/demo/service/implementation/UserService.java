package com.example.demo.service.implementation;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.model.User;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private final IUserRepository _iUserRepository;
    private final PasswordEncoder _passwordEncoder;

    public UserService(IUserRepository iUserRepository, PasswordEncoder passwordEncoder) {
        _iUserRepository = iUserRepository;
        _passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse createUser(UserRequest request) throws Exception {

        if(!request.getPassword().equals(request.getRePassword())) {
            throw new Exception("The passwords you entered do not match.");
        }

        User user = new User();
        List<User> users = _iUserRepository.findAllByDeleted(false);
        user.setEmail(request.getEmail());
        for (User u : users) {
            if(u.getEmail().equals(user.getEmail())) {
                throw new Exception("The email address you entered already exists.");
            }
        }
        user.setPassword(_passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAddress(request.getAddress());
        user.setCity(request.getCity());
        user.setCountry(request.getCountry());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setUserType(request.getUserType());
        user.setDeleted(false);

        User savedUser = _iUserRepository.save(user);

        UserResponse userResponse = new UserResponse();

        userResponse.setId(savedUser.getId());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setFirstName(savedUser.getFirstName());
        userResponse.setLastName(savedUser.getLastName());
        userResponse.setAddress(savedUser.getAddress());
        userResponse.setCity(savedUser.getCity());
        userResponse.setCountry(savedUser.getCountry());
        userResponse.setPhoneNumber(savedUser.getPhoneNumber());
        userResponse.setUserType(savedUser.getUserType());

        return userResponse;
    }
}
