package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {

    User findOneById(UUID id);

    User findOneByEmail(String email);

    List<User> findAllByDeleted(boolean deleted);
}
