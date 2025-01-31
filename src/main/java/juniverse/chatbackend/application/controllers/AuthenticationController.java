package juniverse.chatbackend.application.controllers;


import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "user send their credentials to authenticate their identity to be given access to the system."
    )
    @PostMapping("/authenticate")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ApiResponse<LoginResponse>> authenticateUser(@RequestBody LoginRequest loginRequest){
   try {
       UserModel userModel= userMapper.requestToModel(loginRequest);
       LoginResponse loginResponse= userMapper.modelToResponse(sysUserService.authenticateUser(userModel));
       boolean  isLoginFailed = loginResponse == null;


       // Build the API response
       ApiResponse<LoginResponse> response = ApiResponse.<LoginResponse>builder()
               .success(!isLoginFailed)
               .message(isLoginFailed ? "Login failed" : "Login successful")
               .data(loginResponse)
               .build();

       // Return the appropriate HTTP status and response
       HttpStatus status = isLoginFailed ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK;
       return ResponseEntity.status(status).body(response);
   } catch (Exception e) {
       ApiResponse<LoginResponse> response = ApiResponse.<LoginResponse>builder()
               .success(false)
               .message(e.getMessage())
               .build();
       return ResponseEntity.status(417).body(response);
   }

    }

}
