package com.greenfoxacademy.backendapp.repositories;

import com.greenfoxacademy.backendapp.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByName(String username);

    boolean existsByName(String username);
}
