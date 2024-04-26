package com.example.Deviceregistration.service.impl;
import com.example.Deviceregistration.dto.UserRequest;
import com.example.Deviceregistration.entity.VehicleInfo;
import com.example.Deviceregistration.reponsitory.VehicleInfoReponsitory;
import com.example.Deviceregistration.service.AddUserService;
import jakarta.ws.rs.core.Response;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class DefaulAddUserService implements AddUserService {
    private final VehicleInfoReponsitory reponsitory;

    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    public DefaulAddUserService(VehicleInfoReponsitory reponsitory) {
        this.reponsitory = reponsitory;
    }


    @Override
    public ResponseEntity<String> createKeycloakUser(UserRequest userRequest) {
       Keycloak keycloak = KeycloakBuilder.builder()
               .serverUrl(keycloakServerUrl)
               .realm(realm)
               .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
               .clientId(clientId)
               .username("admin")
               .password("admin")
               .clientSecret(clientSecret).build();

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        // Tạo một UserRepresentation
        UserRepresentation userRepresentation  = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(userRequest.getUserName());
        userRepresentation.setEmail(userRequest.getEmail());
        userRepresentation.setFirstName(userRequest.getFistName());
        userRepresentation.setLastName(userRequest.getLastName());

        Response  response = usersResource.create(userRepresentation);
        if(response.getStatus()==201){
//            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
           VehicleInfo vehicleInfo = new VehicleInfo();
            String userId = CreatedResponseUtil.getCreatedId(response);
            Long userLong = Long.parseLong(userId);
            vehicleInfo.setVin(userLong);
            reponsitory.save(vehicleInfo);
            return new ResponseEntity<>("Created User on Keycloak", HttpStatus.CREATED);
        }else{
             throw new RuntimeException("Failed to create User on Keycloak");
        }
    }
}
