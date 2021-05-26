package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor

@Data
public class PharmacyAdminResponse {

    private UUID id;

    private String email; //not possible to update

    private String firstName;

    private String lastName;

    private String address;

    private String city;

    private String country;

    private String phoneNumber;
}
