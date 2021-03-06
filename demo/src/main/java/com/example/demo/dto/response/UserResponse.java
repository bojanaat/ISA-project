package com.example.demo.dto.response;

import com.example.demo.utils.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor

@Data
public class UserResponse {

    private UUID id;

    private String email; //not possible to update

    private String firstName;

    private String lastName;

    private String address;

    private String city;

    private String country;

    private String phoneNumber;

    private UserType userType;

    private boolean setNewPassword;
}
