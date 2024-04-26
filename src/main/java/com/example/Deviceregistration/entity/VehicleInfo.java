package com.example.Deviceregistration.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class VehicleInfo {
    @Id
    private Long vin;

    public Long getVin() {
        return vin;
    }
    public void setVin(Long vin) {
        this.vin = vin;
    }
}
