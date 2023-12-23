package com.example.userdeviceapplication.controller;

import com.example.userdeviceapplication.dto.UserDeviceCreateUpdateDTO;
import com.example.userdeviceapplication.dto.UserDeviceDTO;
import com.example.userdeviceapplication.dto.UserDeviceDetailDTO;
import com.example.userdeviceapplication.service.UserDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-device-application")
public class UserDeviceController {
    private final UserDeviceService userDeviceService;

    @Autowired
    public UserDeviceController(UserDeviceService userDeviceService) {
        this.userDeviceService = userDeviceService;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDeviceDTO>> getUserDevices() {
        List<UserDeviceDTO> userDevices = userDeviceService.getAllUserDevices();
        return ResponseEntity.ok(userDevices);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserDeviceDetailDTO> getUserDevice(@PathVariable String uuid) {
        UserDeviceDetailDTO userDevice = userDeviceService.getUserDeviceByUuid(uuid);
        if (userDevice != null) {
            return ResponseEntity.ok(userDevice);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<UserDeviceDetailDTO> createUserDevice(@RequestBody UserDeviceCreateUpdateDTO createDTO) {
        UserDeviceDetailDTO userDevice = userDeviceService.createUserDevice(createDTO);
        return ResponseEntity.ok(userDevice);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<UserDeviceDetailDTO> updateUserDevice(@PathVariable String uuid, @RequestBody UserDeviceCreateUpdateDTO updateDTO) {
        UserDeviceDetailDTO userDevice = userDeviceService.updateUserDevice(uuid, updateDTO);
        if (userDevice != null) {
            return ResponseEntity.ok(userDevice);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteUserDevice(@PathVariable String uuid) {
        boolean deleted = userDeviceService.deleteUserDevice(uuid);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
