package com.example.Deviceregistration.service.impl;
import com.example.Deviceregistration.config.KeycloakConfig;
import com.example.Deviceregistration.config.KeycloakConfigAttributes;
import com.example.Deviceregistration.entity.VehicleInfo;
import com.example.Deviceregistration.reponsitory.VehicleInfoReponsitory;
import com.example.Deviceregistration.service.DeviceService;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class DefaulDeviceService implements DeviceService {
    private static final String YEAR = "year";
    private static final String MODEL = "model";
    private static final String VIN = "vin";

    @Value("${app.keycloak-attribute.user-name}")
    private String userName;
    @Value("${app.keycloak-attribute.year}")
    private String year;
    @Value("${app.keycloak-attribute.model}")
    private String model;
    @Value("${app.keycloak-attribute.vin}")
    private String vin;
    private final VehicleInfoReponsitory reponsitory;
    private final KeycloakConfigAttributes keycloakConfigAttributes;
    private final KeycloakConfig keycloakConfig;


    @Override
    public Map<String,Object> register() {
        RealmResource realmResource = keycloakConfig.adminKeycloak().realm(keycloakConfigAttributes.getRealm());
        UsersResource usersResource = realmResource.users();

        // Tạo một UserRepresentation
        UserRepresentation userRepresentation  = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(userName);
        userRepresentation.singleAttribute(YEAR, year)
                .singleAttribute(MODEL, model)
                .singleAttribute(VIN, vin);

        Response  response = usersResource.create(userRepresentation);
        if(response.getStatus()==201){
//          String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
            VehicleInfo vehicleInfo = new VehicleInfo();
            String userId = CreatedResponseUtil.getCreatedId(response);
            vehicleInfo.setVid(userId);
            vehicleInfo.setVin(vin);
            reponsitory.save(vehicleInfo);

            Map<String,Object> map = Map.of();
            map.get(userRepresentation.getUserProfileMetadata());
            return map;
        }else{
             throw new RuntimeException("Failed to create User on Keycloak");
        }
    }
}
