package com.gconsumer.GConsumer.repository;

import com.gconsumer.GConsumer.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperationRepo extends JpaRepository<Operation, Long> {
    @Query("SELECT o FROM Operation o WHERE (o.name = :name AND o.applicationName = :applicationName)")
    Optional<Operation> findByALLParam(@Param("name") String name, @Param("applicationName") String applicationName);

    @Query("SELECT o FROM Operation o WHERE o.name = :name ")
    Optional<Operation> findByName(@Param("name") String name);

    @Query("SELECT o.id, o.name, o.applicationName, o.description FROM Operation o ")
    Object getOperations();


}
