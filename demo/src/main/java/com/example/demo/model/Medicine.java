package com.example.demo.model;

import com.example.demo.utils.MedicineType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Medicine extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String code;

    private double amount;

    @Enumerated(EnumType.STRING)
    private MedicineType medicineType;
}
