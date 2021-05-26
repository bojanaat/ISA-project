package com.example.demo.repository;

import com.example.demo.model.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface IPharmacyRepository extends JpaRepository<Pharmacy, UUID> {

    Pharmacy findOneById(UUID id);

    Set<Pharmacy> findAllByDeleted(boolean deleted);
}
