package com.example.userdeviceapplication.dto;

import lombok.Data;

@Data
public class UserDeviceDetailInfoDTO {
    private String name;
    private String supportedPlatforms;
    private int maximumParticipants;
}