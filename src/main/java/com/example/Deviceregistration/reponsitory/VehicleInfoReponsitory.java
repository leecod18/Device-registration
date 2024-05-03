package com.example.Deviceregistration.reponsitory;

import com.example.Deviceregistration.entity.VehicleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleInfoReponsitory extends JpaRepository<VehicleInfo,String>{
    boolean existsByVin(String vin);
}
