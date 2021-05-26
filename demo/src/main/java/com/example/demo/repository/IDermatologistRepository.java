package com.example.demo.repository;

import com.example.demo.model.Dermatologist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface IDermatologistRepository extends JpaRepository<Dermatologist, UUID> {

    Dermatologist findOneById(UUID id);

    Set<Dermatologist> findAllByUserDeleted(boolean deleted);

    Set<Dermatologist> findAllByPharmacy_IdAndUser_Deleted(UUID pharmacyId, boolean deleted);
}
