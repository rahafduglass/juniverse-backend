package org.example.chatbackend.application.controllers;


import lombok.RequiredArgsConstructor;
import org.example.chatbackend.application.dtos.authentication.LoginRequest;
import org.example.chatbackend.application.dtos.authentication.LoginResponse;
import org.example.chatbackend.domain.mappers.UserMapper;
import org.example.chatbackend.domain.models.UserModel;
import org.example.chatbackend.domain.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/authenticate")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest){
        UserModel userModel= userMapper.requestToModel(loginRequest);
        return ResponseEntity.ok(userMapper.modelToResponse(userService.authenticateUser(userModel)));
    }
}
