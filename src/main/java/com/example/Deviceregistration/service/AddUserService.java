package com.example.Deviceregistration.service;

import com.example.Deviceregistration.dto.UserRequest;
import org.springframework.http.ResponseEntity;

public interface AddUserService {
   ResponseEntity<String> createKeycloakUser(UserRequest userRequest);
}
