package juniverse.chatbackend.application.controllers;


import juniverse.chatbackend.application.helpers.ApiResponseHelper;
import lombok.RequiredArgsConstructor;
import juniverse.chatbackend.domain.mappers.UserMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {



    private final ApiResponseHelper apiResponseHelper;

}
