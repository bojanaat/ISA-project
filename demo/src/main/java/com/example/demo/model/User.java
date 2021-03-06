package com.example.demo.model;

import com.example.demo.utils.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor

@Entity(name = "user_entity")
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String email; //not possible to update

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String phoneNumber;

    private boolean deleted;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    private Date firstTimeLoggedIn;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Patient patient;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private SystemAdmin systemAdmin;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Supplier supplier;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private PharmacyAdmin pharmacyAdmin;

}
