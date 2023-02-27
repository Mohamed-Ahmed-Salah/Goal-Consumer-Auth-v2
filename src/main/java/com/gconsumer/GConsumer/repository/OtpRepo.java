package com.gconsumer.GConsumer.repository;

import com.gconsumer.GConsumer.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepo extends JpaRepository<Otp, Long> {

    @Query("SELECT o FROM Otp o WHERE o.user.id = :userId AND o.otp = :otp ")
    Optional<Otp> findByParams(@Param("userId") Long userId, @Param("otp") String otp);
}
