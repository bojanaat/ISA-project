package com.example.demo.repository;

import com.example.demo.model.SystemAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface ISystemAdminRepository extends JpaRepository<SystemAdmin, UUID> {

    SystemAdmin findOneById(UUID id);

    Set<SystemAdmin> findAllByUser_Deleted(boolean deleted);
}
