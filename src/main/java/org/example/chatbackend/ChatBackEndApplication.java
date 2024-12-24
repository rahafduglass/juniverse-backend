package org.example.chatbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.example.chatbackend")
public class ChatBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatBackEndApplication.class, args);
    }

}
