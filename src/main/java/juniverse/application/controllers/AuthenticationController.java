package juniverse.application.controllers;


import io.swagger.v3.oas.annotations.tags.Tag;
import juniverse.application.dtos.ApiResponse;
import juniverse.application.dtos.sys_user.RegisterRequest;
import juniverse.application.dtos.authentication.UserAuthenticationRequest;
import juniverse.application.dtos.authentication.UserAuthenticationResponse;
import juniverse.application.helpers.ApiResponseHelper;
import juniverse.domain.mappers.SysUserMapper;
import juniverse.domain.mappers.UserAuthenticationMapper;
import juniverse.domain.services.UserAuthenticationService;
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
    private final UserAuthenticationService authenticationService;
    private final UserAuthenticationMapper userAuthenticationMapper;
    private final SysUserMapper sysUserMapper;


    @PostMapping("/signIn")
    public ResponseEntity<ApiResponse<UserAuthenticationResponse>> signIn(@RequestBody UserAuthenticationRequest userAuthenticationRequest) {
        try {
            UserAuthenticationResponse response=userAuthenticationMapper.modelToResponse(authenticationService.signIn(userAuthenticationMapper.requestToModel(userAuthenticationRequest)));
            boolean isFail = response == null;

            return apiResponseHelper.buildApiResponse(response, !isFail, (isFail ? "invalid credentials" : "successful login"), (isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK));
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, "An error occurred: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/register-list-of-users")
    public ResponseEntity<ApiResponse<Boolean>> registerListOfUsers(@RequestBody List<RegisterRequest> registerRequests) {
        try {
            Boolean areRegistered=authenticationService.registerListOfUsers(sysUserMapper.listOfRequestsToListOfModel(registerRequests));

            return apiResponseHelper.buildApiResponse(areRegistered, areRegistered, (!areRegistered ? "registration failed" : "successful registration"), (!areRegistered ? HttpStatus.NOT_FOUND : HttpStatus.OK));
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, "An error occurred: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }

    }
}
