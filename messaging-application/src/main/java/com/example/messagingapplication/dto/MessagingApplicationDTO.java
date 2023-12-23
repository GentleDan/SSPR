package com.example.messagingapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessagingApplicationDTO {
    private String name;
    private String supportedPlatforms;
    private int maximumParticipants;
}
