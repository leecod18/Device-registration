package com.example.Deviceregistration.web.rest;


import com.example.Deviceregistration.dto.UserRequest;
import com.example.Deviceregistration.service.AddUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserResource {

    private final AddUserService addUserService;
    public UserResource(AddUserService addUserService) {
        this.addUserService = addUserService;
    }
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){
        return addUserService.createKeycloakUser(userRequest);
    }
}
