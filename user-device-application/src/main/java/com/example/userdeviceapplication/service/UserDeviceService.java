package com.example.userdeviceapplication.service;

import com.example.userdeviceapplication.dto.UserDeviceCreateUpdateDTO;
import com.example.userdeviceapplication.dto.UserDeviceDTO;
import com.example.userdeviceapplication.dto.UserDeviceDetailDTO;
import com.example.userdeviceapplication.dto.UserDeviceDetailInfoDTO;
import com.example.userdeviceapplication.model.UserDevice;
import com.example.userdeviceapplication.repository.UserDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserDeviceService {
    private final UserDeviceRepository userDeviceRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public UserDeviceService(UserDeviceRepository userDeviceRepository, RestTemplate restTemplate) {
        this.userDeviceRepository = userDeviceRepository;
        this.restTemplate = restTemplate;
    }

    public List<UserDeviceDTO> getAllUserDevices() {
        List<UserDevice> userDevices = userDeviceRepository.findAll();
        return userDevices.stream()
                .map(this::convertToUserDeviceDTO)
                .collect(Collectors.toList());
    }

    public UserDeviceDetailDTO getUserDeviceByUuid(String uuid) {
        UserDevice userDevice = userDeviceRepository.findByUuid(uuid);
        if (userDevice != null) {
            return convertToUserDeviceDetailDTO(userDevice);
        }
        return null;
    }

    public UserDeviceDetailDTO createUserDevice(UserDeviceCreateUpdateDTO createDTO) {
        UserDevice userDevice = new UserDevice();
        userDevice.setUuid(createUUID());
        updateUserDeviceFromDTO(userDevice, createDTO);
        userDevice = userDeviceRepository.save(userDevice);
        return convertToUserDeviceDetailDTO(userDevice);
    }

    public UserDeviceDetailDTO updateUserDevice(String uuid, UserDeviceCreateUpdateDTO updateDTO) {
        UserDevice userDevice = userDeviceRepository.findByUuid(uuid);
        if (userDevice != null) {
            updateUserDeviceFromDTO(userDevice, updateDTO);
            userDevice = userDeviceRepository.save(userDevice);
            return convertToUserDeviceDetailDTO(userDevice);
        }
        return null;
    }

    public boolean deleteUserDevice(String uuid) {
        UserDevice userDevice = userDeviceRepository.findByUuid(uuid);
        if (userDevice != null) {
            userDeviceRepository.delete(userDevice);
            return true;
        }
        return false;
    }

    private UserDeviceDTO convertToUserDeviceDTO(UserDevice userDevice) {
        UserDeviceDTO dto = new UserDeviceDTO();
        dto.setUuid(userDevice.getUuid());
        dto.setModel(userDevice.getModel());
        dto.setType(userDevice.getType());
        dto.setInterfaceType(userDevice.getInterfaceType());
        dto.setMessagingAppUuid(userDevice.getMessagingAppUuid());
        return dto;
    }

    private UserDeviceDetailDTO convertToUserDeviceDetailDTO(UserDevice userDevice) {
        UserDeviceDetailDTO detailDTO = new UserDeviceDetailDTO();
        detailDTO.setUuid(userDevice.getUuid());
        detailDTO.setModel(userDevice.getModel());
        detailDTO.setType(userDevice.getType());
        detailDTO.setInterfaceType(userDevice.getInterfaceType());
        detailDTO.setMessagingAppUuid(userDevice.getMessagingAppUuid());

        UserDeviceDetailInfoDTO subscriptionInfo = getmessagingAppInfo(userDevice.getMessagingAppUuid());
        detailDTO.setMessagingAppInfo(subscriptionInfo);

        return detailDTO;
    }

    private void updateUserDeviceFromDTO(UserDevice userDevice, UserDeviceCreateUpdateDTO dto) {
        userDevice.setModel(dto.getModel());
        userDevice.setType(dto.getType());
        userDevice.setInterfaceType(dto.getInterfaceType());
        userDevice.setMessagingAppUuid(dto.getMessagingAppUuid());
    }

    private String createUUID() {
        Random random = new Random();
        long mostSigBits = random.nextLong();
        long leastSigBits = random.nextLong();
        return String.format("%08x-%04x-%04x-%04x-%012x",
                mostSigBits >>> 32, (mostSigBits >>> 16) & 0xFFFF, mostSigBits & 0xFFFF,
                (leastSigBits >>> 16) & 0xFFFF, leastSigBits & 0xFFFF);
    }

    private UserDeviceDetailInfoDTO getmessagingAppInfo(String messagingAppUuid) {
        String messagingAppInfoUrl = "http://nginx/messaging-application/" + messagingAppUuid;
        return restTemplate.getForObject(messagingAppInfoUrl, UserDeviceDetailInfoDTO.class);
    }
}
