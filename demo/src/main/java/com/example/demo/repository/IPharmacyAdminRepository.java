package com.example.demo.repository;

import com.example.demo.model.PharmacyAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface IPharmacyAdminRepository extends JpaRepository<PharmacyAdmin, UUID> {

    PharmacyAdmin findOneById(UUID id);

    Set<PharmacyAdmin> findAllByUser_Deleted(boolean deleted);

    Set<PharmacyAdmin> findAllByPharmacy_IdAndUser_Deleted(UUID pharmacyId, boolean deleted);
}
