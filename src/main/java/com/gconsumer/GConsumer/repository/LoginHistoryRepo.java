package com.gconsumer.GConsumer.repository;

import com.gconsumer.GConsumer.model.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginHistoryRepo extends JpaRepository<LoginHistory, Long> {

    @Query("SELECT o FROM LoginHistory o WHERE o.userCredential.id = :userId order by o.createdOn desc ")
//    @Query("SELECT COUNT(id) FROM LoginHistory where 'user_id' = :id and 'status' = 'FAILED'")
//    @Query("SELECT COUNT(o.id) FROM LoginHistory o WHERE o.userCredential.id = :userId AND o.status = 'FAILED' AND date(o.createdOn) < current_timestamp ")
    List<LoginHistory> findLastSuccess(@Param("userId") Long userId);
}
