package juniverse.chatbackend.application.controllers;


import juniverse.chatbackend.application.dtos.RegisterRequest;
import juniverse.chatbackend.application.dtos.authentication.UserAuthenticationResponse;
import juniverse.chatbackend.application.dtos.authentication.UserAuthenticationRequest;
import juniverse.chatbackend.application.helpers.ApiResponseHelper;
import juniverse.chatbackend.domain.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final ApiResponseHelper apiResponseHelper;

    private final AuthenticationService authenticationService;

    @PostMapping("/signIn")
    public ResponseEntity<UserAuthenticationResponse> signIn(@RequestBody UserAuthenticationRequest userAuthenticationRequest) {
        return ResponseEntity.ok(authenticationService.signIn(userAuthenticationRequest));
    }

    @PostMapping("/register-list-of-students") // run once "replaces integrating students data"
    public ResponseEntity<Boolean> registerListOfStudents(@RequestBody List<RegisterRequest> registerRequests) {
        return ResponseEntity.ok(authenticationService.registerListOfUsers(registerRequests));
    }
}
