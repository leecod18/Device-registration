package com.example.Deviceregistration.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class VehicleInfo {

    @Id
    private String vid;

    @Column(unique = true)
    private String vin;

}
