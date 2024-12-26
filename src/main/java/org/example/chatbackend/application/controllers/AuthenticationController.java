package org.example.chatbackend.application.controllers;


import lombok.RequiredArgsConstructor;
import org.example.chatbackend.application.dtos.authentication.LoginRequest;
import org.example.chatbackend.application.dtos.authentication.LoginResponse;
import org.example.chatbackend.domain.mappers.UserMapper;
import org.example.chatbackend.domain.models.UserModel;
import org.example.chatbackend.domain.services.SysUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final SysUserService sysUserService;
    private final UserMapper userMapper;

    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest){
        UserModel userModel= userMapper.requestToModel(loginRequest);
        return ResponseEntity.ok(userMapper.modelToResponse(sysUserService.authenticateUser(userModel)));
    }

}
