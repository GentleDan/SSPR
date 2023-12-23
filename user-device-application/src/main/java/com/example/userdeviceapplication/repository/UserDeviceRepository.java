package com.example.userdeviceapplication.repository;

import com.example.userdeviceapplication.model.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {
    UserDevice findByUuid(String uuid);
}
