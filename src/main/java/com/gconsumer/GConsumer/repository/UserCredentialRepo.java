package com.gconsumer.GConsumer.repository;

import com.gconsumer.GConsumer.model.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepo extends JpaRepository<UserCredential, Long>, JpaSpecificationExecutor<UserCredential> {

//    @Query("SELECT a.username FROM UserAuthData a")

    @Query("SELECT a FROM UserCredential a WHERE  a.email = :email OR a.phone = :phone")
    Optional<UserCredential> findByUniqueFields( @Param("email") String email, @Param("phone") String phone);//same as findByEmailOrPhone

    @Query("SELECT a FROM UserCredential a WHERE a.email = :email")
    Optional<UserCredential> findByEmail(@Param("email") String email);

    @Query("SELECT a FROM UserCredential a WHERE a.fullName = :username")
    Optional<UserCredential> findByUsername(@Param("username") String username);

    @Query("SELECT a FROM UserCredential a WHERE a.phone = :phone")
    Optional<UserCredential> findByPhone(@Param("phone") String phone);

    @Query("SELECT a FROM UserCredential a WHERE ( a.phone = :username or a.email = :username) ")
    Optional<UserCredential> findByEmailOrPhone(/*@Param("username")*/ String username);

//    @Query("select a from UserCredential a where (a.firstName = :first or a.lastName = :last)")
//    List<UserCredential> findByFilter(@Param("first") String first, @Param("last") String last);
}
