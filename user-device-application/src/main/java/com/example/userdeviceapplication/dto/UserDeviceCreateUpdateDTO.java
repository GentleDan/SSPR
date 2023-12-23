package com.example.userdeviceapplication.dto;

import lombok.Data;

@Data
public class UserDeviceCreateUpdateDTO {
    private String model;
    private String type;
    private String interfaceType;
    private String messagingAppUuid;
}