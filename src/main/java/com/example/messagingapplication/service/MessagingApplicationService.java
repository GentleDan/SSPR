package com.example.messagingapplication.service;

import com.example.messagingapplication.model.MessagingApplication;
import com.example.messagingapplication.dto.MessagingApplicationDTO;
import com.example.messagingapplication.repository.MessagingApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MessagingApplicationService {
    private final MessagingApplicationRepository applicationRepository;

    @Transactional
    public List<MessagingApplication> getAllApplications() {
        return applicationRepository.findAll();
    }

    @Transactional
    public Optional<MessagingApplication> getApplicationByUuid(String uuid) {
        return applicationRepository.findByUuid(uuid);
    }

    @Transactional
    public MessagingApplication createApplication(MessagingApplicationDTO applicationDTO) {
        MessagingApplication application = new MessagingApplication();
        application.setUuid(createUUID());
        application.setName(applicationDTO.getName());
        application.setSupportedPlatforms(applicationDTO.getSupportedPlatforms());
        application.setMaximumParticipants(applicationDTO.getMaximumParticipants());
        return applicationRepository.save(application);
    }

    @Transactional
    public MessagingApplication updateApplication(String uuid, MessagingApplicationDTO applicationDTO) {
        Optional<MessagingApplication> existingApplication = applicationRepository.findByUuid(uuid);
        if (existingApplication.isPresent()) {
            MessagingApplication application = existingApplication.get();
            application.setName(applicationDTO.getName());
            application.setSupportedPlatforms(applicationDTO.getSupportedPlatforms());
            application.setMaximumParticipants(applicationDTO.getMaximumParticipants());
            return applicationRepository.save(application);
        }
        return null;
    }

    @Transactional
    public boolean deleteApplication(String uuid) {
        Optional<MessagingApplication> existingApplication = applicationRepository.findByUuid(uuid);
        if (existingApplication.isPresent()) {
            applicationRepository.delete(existingApplication.get());
            return true;
        }
        return false;
    }

    private String createUUID() {
        Random random = new Random();
        long mostSigBits = random.nextLong();
        long leastSigBits = random.nextLong();
        return String.format("%08x-%04x-%04x-%04x-%012x",
                mostSigBits >>> 32, (mostSigBits >>> 16) & 0xFFFF, mostSigBits & 0xFFFF,
                (leastSigBits >>> 16) & 0xFFFF, leastSigBits & 0xFFFF);
    }
}
