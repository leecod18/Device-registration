package com.example.Deviceregistration.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class VehicleInfo {

    @Id
    private String vid;

    @Column(unique = true)
    private String vin;

}
