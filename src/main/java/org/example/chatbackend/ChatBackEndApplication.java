package org.example.chatbackend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.example.chatbackend")
@OpenAPIDefinition
public class ChatBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatBackEndApplication.class, args);
    }


}
