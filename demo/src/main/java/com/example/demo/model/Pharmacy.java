package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Pharmacy extends BaseEntity {

    private String name;

    private String address;

    private String description;

    private boolean deleted;

    @OneToMany(mappedBy = "pharmacy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PharmacyAdmin> pharmacyAdmins = new ArrayList<>();
}
