package juniverse.chatbackend.application.controllers;


import io.swagger.v3.oas.annotations.tags.Tag;
import juniverse.chatbackend.application.dtos.ApiResponse;
import juniverse.chatbackend.application.dtos.RegisterRequest;
import juniverse.chatbackend.application.dtos.authentication.UserAuthenticationRequest;
import juniverse.chatbackend.application.dtos.authentication.UserAuthenticationResponse;
import juniverse.chatbackend.application.helpers.ApiResponseHelper;
import juniverse.chatbackend.domain.mappers.SysUserMapper;
import juniverse.chatbackend.domain.mappers.UserAuthenticationMapper;
import juniverse.chatbackend.domain.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name="LOGIN & REGISTER")
public class AuthenticationController {

    private final ApiResponseHelper apiResponseHelper;

    private final AuthenticationService authenticationService;

    private final UserAuthenticationMapper userAuthenticationMapper;
    private final SysUserMapper sysUserMapper;

    @PostMapping("/signIn")
    public ResponseEntity<ApiResponse<UserAuthenticationResponse>> signIn(@RequestBody UserAuthenticationRequest userAuthenticationRequest) {
        try {
            //sign user and retrieve token
            UserAuthenticationResponse response=userAuthenticationMapper.modelToResponse(authenticationService.signIn(userAuthenticationMapper.requestToModel(userAuthenticationRequest)));
            //check if login succeeded
            boolean isFail = response == null;
            //build response
            return apiResponseHelper.buildApiResponse(response, !isFail, (isFail ? "invalid credentials" : "successful login"), (isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK));
        } catch (Exception e) {
            // Handle exceptions and return error response
            return apiResponseHelper.buildApiResponse(null, false, "An error occurred: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/register-list-of-users") // run once "replaces integrating user data"
    public ResponseEntity<ApiResponse<Boolean>> registerListOfUsers(@RequestBody List<RegisterRequest> registerRequests) {
        try {
            //register users
            Boolean areRegistered=authenticationService.registerListOfUsers(sysUserMapper.listOfRequestsToListOfModel(registerRequests));
            //build response
            return apiResponseHelper.buildApiResponse(areRegistered, areRegistered, (!areRegistered ? "registration failed" : "successful registration"), (!areRegistered ? HttpStatus.NOT_FOUND : HttpStatus.OK));
        } catch (Exception e) {
            // Handle exceptions and return error response
            return apiResponseHelper.buildApiResponse(null, false, "An error occurred: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }

    }
}
