package com.example.demo.repository;

import com.example.demo.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface ISupplierRepository extends JpaRepository<Supplier, UUID> {

    Supplier findOneById(UUID id);

    Set<Supplier> findAllByUser_Deleted(boolean deleted);
}
