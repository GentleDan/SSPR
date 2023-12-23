package com.example.messagingapplication.repository;

import com.example.messagingapplication.model.MessagingApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessagingApplicationRepository extends JpaRepository<MessagingApplication, Long> {
    Optional<MessagingApplication> findByUuid(String uuid);
}
