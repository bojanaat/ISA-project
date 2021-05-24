package com.example.demo.dto.request;

import com.example.demo.utils.UserType;
import lombok.Data;

@Data
public class UserRequest {

    private String email; //not possible to update

    private String password;

    private String rePassword;

    private String firstName;

    private String lastName;

    private String address;

    private String city;

    private String country;

    private String phoneNumber;

    private UserType userType;
}
