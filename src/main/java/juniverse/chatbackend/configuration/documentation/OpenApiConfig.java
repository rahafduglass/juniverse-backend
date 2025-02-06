package juniverse.chatbackend.configuration.documentation;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Juniverse API")
                        .version("1.0.0")
                        .description("Private Chat: API to manage private chats between the users and the therapist. "))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT"))
                );
    }

    @Bean
    public OperationCustomizer privateChatControllerOperationCustomizer() {
        return (operation, handlerMethod) -> {

            if (handlerMethod.getMethod().getName().equals("getAllMessages")) {
                Class<?>[] parameterTypes = handlerMethod.getMethod().getParameterTypes();

                if (parameterTypes.length == 0) {
                    operation.summary("retrieve all messages")
                            .description("retrieve ALL MESSAGES associated with specific user chat\n" +
                                    " || ROLES permissions : STUDENT, ADMIN, MODERATOR.")
                            .setTags(Arrays.asList("STUDENT","ADMIN","MODERATOR"));
                } else if (parameterTypes.length == 1) {
                    operation.summary("retrieve all messages by chatId")
                            .description("retrieve ALL MESSAGES associated with therapist chats\n" +
                                    " || we're requesting the chatId because therapist has multiple chats\n" +
                                    " || ROLES permissions : THERAPIST")
                            .setTags(Arrays.asList("THERAPIST"));
                }
            }
            if (handlerMethod.getMethod().getName().equals("getAllTherapistChats")) {
                operation.summary("retrieve ALL THERAPIST CHATS ")
                        .description("retrieve ALL CHATS METADATA associated with a therapist\n" +
                                " || NOTE: you can use the chatId to retrieve messages later\n" +
                                " || ROLES permissions : THERAPIST")
                        .setTags(Arrays.asList("THERAPIST"));
            }
            if (handlerMethod.getMethod().getName().equals("getChat")) {
                operation.summary("retrieve USER CHAT")
                        .description("retrieve THE CHAT METADATA between the therapist and user\n" +
                                " || NOTE: each user has one chat only with the therapist\n" +
                                "|| ROLES permissions : STUDENT, MODERATOR, ADMIN")
                        .setTags(Arrays.asList("STUDENT","ADMIN","MODERATOR"));
            }
            if (handlerMethod.getMethod().getName().equals("sendMessageFromTherapist")) {
                operation.summary("lets the therapist send a message")
                        .description("use when a therapist want to reply to a message\n" +
                                " || NOTE: therapist has many chats, so please give me the chatId that was fetched first in the GET allTherapistChats\n" +
                                " || ROLES permissions : THERAPIST")
                        .setTags(Arrays.asList("THERAPIST"));
            }
            if (handlerMethod.getMethod().getName().equals("sendMessageToTherapist")) {
                operation.summary("lets users talk to therapist")
                        .description("use when a user want to initialize or continue chat with therapist\n" +
                                " || ROLES permissions : ADMIN, STUDENT, MODERATOR")
                        .setTags(Arrays.asList("STUDENT","ADMIN","MODERATOR"));
            }
            if (handlerMethod.getMethod().getName().equals("markChatAsRead")) {
                operation.summary("mark chat as READ")
                        .description("client-side should manually request to read a chat after the user enters chat\n" +
                                " || INTUITIVE NOTE: the count of unread messages will be zero \n" +
                                " || ROLES permissions : ALL ROLES")
                        .setTags(Arrays.asList("ALL ROLES"));
            }


            // Return the modified operation object
            return operation;
        };
    }

    @Bean
    public OperationCustomizer AuthenticationControllerOperationCustomizer() {
        return (operation, handlerMethod) -> {
            if (handlerMethod.getMethod().getName().equals("signIn")) {
                operation.summary("sign in and get ur token!!")
                        .description("bru just sign in :3");
            }
            if (handlerMethod.getMethod().getName().equals("registerListOfStudents")) {
                operation.summary("USE ONCE ONLY")
                        .description("this endpoint is for assigning the mock users we have in our resources folder juniverse-mock-users.json || YOU SHOULD use this ONLY ONCE when u run the app for the first time. || other entities and tables should be empty || CONCLUSION: EMPTY DATABASE ")
                        .setTags(Arrays.asList("LOGIN & REGISTER"));

            }
            return operation;
        };
    }

}
