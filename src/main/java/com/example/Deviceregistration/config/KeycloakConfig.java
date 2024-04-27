package com.example.Deviceregistration.config;
import lombok.AllArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

import org.springframework.context.annotation.Configuration;



@Configuration
@AllArgsConstructor
public class KeycloakConfig {

    private  final KeycloakConfigAttributes attributes;
    public Keycloak adminKeycloak(){
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(attributes.getKeycloakServerUrl())
                .realm(attributes.getRealm())
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(attributes.getClientId())
                .username("admin")
                .password("admin")
//               .clientSecret(clientSecret)
                .build();
        return keycloak;
    }

}
