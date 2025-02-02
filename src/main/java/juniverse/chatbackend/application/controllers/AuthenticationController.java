package juniverse.chatbackend.application.controllers;


import io.swagger.v3.oas.annotations.Operation;
import juniverse.chatbackend.application.helpers.ApiResponseHelper;
import lombok.RequiredArgsConstructor;
import juniverse.chatbackend.application.dtos.authentication.LoginRequest;
import juniverse.chatbackend.application.dtos.ApiResponse;
import juniverse.chatbackend.application.dtos.authentication.LoginResponse;
import juniverse.chatbackend.domain.mappers.UserMapper;
import juniverse.chatbackend.domain.models.UserModel;
import juniverse.chatbackend.domain.services.SysUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final SysUserService sysUserService;
    private final UserMapper userMapper;
    private final ApiResponseHelper apiResponseHelper;
    @Operation(
            summary = "user send their credentials to authenticate their identity to be given access to the system."
    )
    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ApiResponse<LoginResponse>> authenticateUser(@RequestBody LoginRequest loginRequest){
   try {
       LoginResponse loginResponse= userMapper.modelToResponse(sysUserService.authenticateUser(userMapper.requestToModel(loginRequest)));
       boolean  isLoginFailed = loginResponse == null;

       // Build the API response
       return apiResponseHelper.buildApiResponse(loginResponse,!isLoginFailed
               ,(isLoginFailed ? "Login failed" : "Login successful")
               , isLoginFailed ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);
   } catch (Exception e) {
       return apiResponseHelper.buildApiResponse(null, false, "an error occurred " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
   }

    }

}
