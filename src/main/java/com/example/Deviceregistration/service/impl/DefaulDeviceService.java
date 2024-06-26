package com.example.Deviceregistration.service.impl;
import com.example.Deviceregistration.config.KeycloakConfigAttributes;
import com.example.Deviceregistration.dto.response.VehicleInfoResponse;
import com.example.Deviceregistration.entity.VehicleInfo;
import com.example.Deviceregistration.reponsitory.VehicleInfoReponsitory;
import com.example.Deviceregistration.service.DeviceService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class DefaulDeviceService implements DeviceService {
    private static final String YEAR = "year";
    private static final String MODEL = "model";
    private static final String VIN = "vin";

    @Value("${app.keycloak-attribute.year}")
    private String year;
    @Value("${app.keycloak-attribute.model}")
    private String model;
    @Value("${app.keycloak-attribute.vin}")
    private String vin;

    private final VehicleInfoReponsitory reponsitory;
    private final KeycloakConfigAttributes keycloakConfigAttributes;
    private  final Keycloak keycloak;


    @Override
    public VehicleInfoResponse register() {
        if(reponsitory.existsByVin(vin)){
            throw new RuntimeException("user already exists");
        }else{
            RealmResource realmResource = keycloak.realm(keycloakConfigAttributes.getRealm());
            UsersResource usersResource = realmResource.users();
            // Tạo một UserRepresentation
            UserRepresentation userRepresentation = new UserRepresentation();
            userRepresentation.setEnabled(true);
            userRepresentation.setUsername(vin);
            userRepresentation.singleAttribute(YEAR, year)
                    .singleAttribute(MODEL, model)
                    .singleAttribute(VIN, vin);

            Response response = usersResource.create(userRepresentation);
            if (response.getStatus() == 201) {
//          String userId = VehicleInfoResponse.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
                VehicleInfo vehicleInfo = new VehicleInfo();
                String userId = CreatedResponseUtil.getCreatedId(response);
                vehicleInfo.setVid(userId);
                vehicleInfo.setVin(vin);
                reponsitory.save(vehicleInfo);

                VehicleInfoResponse vehicleInfoResponse = new VehicleInfoResponse();
                vehicleInfoResponse.setVid(vehicleInfo.getVid());
                vehicleInfoResponse.setVin(vin);
                return vehicleInfoResponse;
            } else {
                throw new RuntimeException("Failed create");
            }
        }
    }
}
