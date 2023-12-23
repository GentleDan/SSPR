package com.example.messagingapplication.controller;

import com.example.messagingapplication.model.MessagingApplication;
import com.example.messagingapplication.dto.MessagingApplicationDTO;
import com.example.messagingapplication.service.MessagingApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messaging-application")
public class MessagingApplicationController {
    private final MessagingApplicationService applicationService;

    @Autowired
    public MessagingApplicationController(MessagingApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("/")
    public List<MessagingApplication> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<MessagingApplication> getApplicationByUuid(@PathVariable String uuid) {
        return applicationService.getApplicationByUuid(uuid)
                .map(app -> ResponseEntity.ok().body(app))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<MessagingApplication> createApplication(@RequestBody MessagingApplicationDTO applicationDTO) {
        MessagingApplication createdApplication = applicationService.createApplication(applicationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdApplication);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<MessagingApplication> updateApplication(@PathVariable String uuid, @RequestBody MessagingApplicationDTO applicationDTO) {
        MessagingApplication updatedApplication = applicationService.updateApplication(uuid, applicationDTO);
        if (updatedApplication != null) {
            return ResponseEntity.ok().body(updatedApplication);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteApplication(@PathVariable String uuid) {
        boolean deleted = applicationService.deleteApplication(uuid);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
