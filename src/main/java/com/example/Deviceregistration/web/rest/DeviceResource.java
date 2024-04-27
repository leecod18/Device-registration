package com.example.Deviceregistration.web.rest;


import com.example.Deviceregistration.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/device")
public class DeviceResource {

    private final DeviceService deviceService;

    public DeviceResource(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> registerDevice(){
        return deviceService.register();
    }
}

