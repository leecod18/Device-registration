package com.example.Deviceregistration.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VehicleInfoResponse {
    private String vid;
    private String vin;
    private String userName;
    private String year;
    private String model;

    public VehicleInfoResponse() {

    }
}
