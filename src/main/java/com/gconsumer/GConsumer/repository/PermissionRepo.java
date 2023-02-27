package com.gconsumer.GConsumer.repository;

import com.gconsumer.GConsumer.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PermissionRepo extends JpaRepository<Permission, Long> {


    @Query("SELECT p FROM Permission p WHERE p.userCredential.id = ?1 AND p.operation.id = ?2")
    Optional<Permission> findByUserIdAndOpId(Long userId, Long operationId);

    @Query("SELECT p FROM Permission p WHERE p.userCredential.fullName = ?1 OR p.userCredential.phone = ?1 OR p.userCredential.email = ?1")
    List<Permission> findByUsername(String username);
}

