package com.example.userdeviceapplication.dto;

import lombok.Data;

@Data
public class UserDeviceDetailDTO {
    private String uuid;
    private String model;
    private String type;
    private String interfaceType;
    private String messagingAppUuid;
    private UserDeviceDetailInfoDTO messagingAppInfo;
}