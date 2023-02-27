package com.gconsumer.GConsumer.repository;

import com.gconsumer.GConsumer.model.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPreferenceRepo extends JpaRepository<UserPreference, Long> {
}
