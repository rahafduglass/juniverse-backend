package juniverse.configuration.documentation;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import juniverse.application.controllers.chat.PrivateChatController;
import juniverse.application.controllers.chat.PublicChatController;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.service.OperationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Juniverse API")
                        .version("1.0.0")
                        .description(""))
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
            Class<?> declaringClass = handlerMethod.getMethod().getDeclaringClass();
            if (handlerMethod.getMethod().getName().equals("getAllMessages") && declaringClass.equals(PrivateChatController.class)) {
                Class<?>[] parameterTypes = handlerMethod.getMethod().getParameterTypes();

                if (parameterTypes.length == 0) {
                    operation.summary("retrieve all messages")
                            .description("retrieve ALL MESSAGES associated with specific user chat" +
                                    " || ROLES permissions : STUDENT, ADMIN, MODERATOR.");
                } else if (parameterTypes.length == 1) {
                    operation.summary("retrieve all messages by chatId")
                            .description("retrieve ALL MESSAGES associated with therapist chats" +
                                    " || we're requesting the chatId because therapist has multiple chats" +
                                    " || ROLES permissions : THERAPIST");
                }
            }
            if (handlerMethod.getMethod().getName().equals("getAllTherapistChats")) {
                operation.summary("retrieve ALL THERAPIST CHATS ")
                        .description("retrieve ALL CHATS METADATA associated with a therapist" +
                                " || NOTE: you can use the chatId to retrieve messages later" +
                                " || ROLES permissions : THERAPIST");
            }
            if (handlerMethod.getMethod().getName().equals("getChat")) {
                operation.summary("retrieve USER CHAT")
                        .description("retrieve THE CHAT METADATA between the therapist and user\n" +
                                " || NOTE: each user has one chat only with the therapist\n" +
                                "|| ROLES permissions : STUDENT, MODERATOR, ADMIN");
            }
            if (handlerMethod.getMethod().getName().equals("sendMessageFromTherapist")) {
                operation.summary("lets the therapist send a message")
                        .description("use when a therapist want to reply to a message\n" +
                                " || NOTE: therapist has many chats, so please give me the chatId that was fetched first in the GET allTherapistChats\n" +
                                " || ROLES permissions : THERAPIST");
            }
            if (handlerMethod.getMethod().getName().equals("sendMessageToTherapist")) {
                operation.summary("lets users talk to therapist")
                        .description("use when a user want to initialize or continue chat with therapist\n" +
                                " || ROLES permissions : ADMIN, STUDENT, MODERATOR");
            }


            // Return the modified operation object
            return operation;
        };
    }

    @Bean
    public OperationCustomizer AuthenticationController() {
        return (operation, handlerMethod) -> {
            if (handlerMethod.getMethod().getName().equals("signIn")) {
                operation.summary("sign in and get ur token!!")
                        .description("^^");
            }
            if (handlerMethod.getMethod().getName().equals("registerListOfUsers")) {
                operation.summary("USE ONCE ONLY")
                        .description("this endpoint is for assigning the mock users we have in our resources folder juniverse-mock-users.json || YOU SHOULD use this ONLY ONCE when u run the app for the first time. || other entities and tables should be empty || CONCLUSION: EMPTY DATABASE ");

            }
            return operation;
        };
    }

    @Bean
    public OperationCustomizer SysUserController() {
        return (operation, handlerMethod) -> {
            if (handlerMethod.getMethod().getName().equals("getProfile")) {
                operation.summary("profile info for user!!")
                        .description(" || ROLES permissions : ALL USERS.^^");
            }
            if (handlerMethod.getMethod().getName().equals("updateBio")) {
                operation.summary("update profile bio")
                        .description("  || ROLES permissions : ALL USERS.^^");
            }
            if (handlerMethod.getMethod().getName().equals("updateProfilePicture")) {
                operation.summary(".png photos for now :D")
                        .description(" || ROLES permissions : ALL USERS.^^");
            }
            if (handlerMethod.getMethod().getName().equals("updateCoverPicture")) {
                operation.summary(".png photos for now :D")
                        .description(" || ROLES permissions : ALL USERS.^^");
            }
            if (handlerMethod.getMethod().getName().equals("getProfileAndCoverPictures")) {
                operation.summary("get profile and cover pics")
                        .description("WE'LL USE THIS ENDPOINT since they're not included in GET /profile || ROLES permissions : ALL USERS.");
            }
            return operation;
        };
    }

    @Bean
    public OperationCustomizer PublicChatController() {
        return (operation, handlerMethod) -> {
            Class<?> declaringClass = handlerMethod.getMethod().getDeclaringClass();

            if (handlerMethod.getMethod().getName().equals("sendMessage")) {
                operation.summary("send public message!")
                        .description("just fill content || ROLES permissions : ALL USERS.");
            }
            if (handlerMethod.getMethod().getName().equals("getAllMessages") && declaringClass.equals(PublicChatController.class)) {
                operation.summary("get all public chat messages")
                        .description(" || ROLES permissions : ALL USERS.");
            }
            if (handlerMethod.getMethod().getName().equals("markMessageAsDeleted")) {
                operation.summary("delete a message if it's not appropriate")
                        .description(" || ROLES permissions : ADMIN, MODERATOR.^^");
            }
            if (handlerMethod.getMethod().getName().equals("editMessage")) {
                operation.summary("lets users edit their messages")
                        .description(" || ROLES permissions : ALL USERS.^^");
            }

            return operation;
        };
    }

    @Bean
    public OperationCustomizer FolderController() {
        return (operation, handlerMethod) -> {
            if (handlerMethod.getMethod().getName().equals("addFolder")) {
                operation.summary("adding folders :D")
                        .description(" || ROLES permissions : ADMINS.^^");
            }
            if (handlerMethod.getMethod().getName().equals("updateFolder")) {
                operation.summary("update folder's name and description")
                        .description(" use to update both name and description using folderId for sure|| ROLES permissions : ADMINS.^^");
            }
            if (handlerMethod.getMethod().getName().equals("updateFolderName")) {
                operation.summary("update folder's name alone")
                        .description(" use to update folder's name alone using folderId for sure|| ROLES permissions : ADMINS.^^");
            }
            if (handlerMethod.getMethod().getName().equals("updateFolderDescription")) {
                operation.summary("update folder's description")
                        .description(" use to update folder's description using folderId for sure|| ROLES permissions : ADMINS.^^");
            }
            if (handlerMethod.getMethod().getName().equals("getFolders")) {
                operation.summary("get all folders")
                        .description(" || ROLES permissions : ALL USERS.^^");
            }
            if (handlerMethod.getMethod().getName().equals("deleteFolder")) {
                operation.summary("delete a folder")
                        .description("delete a folder using folderId || ROLES permissions : ADMINS.^^");
            }
            if (handlerMethod.getMethod().getName().equals("updateFolder")) {
                operation.summary("update folder's name and description")
                        .description(" use to update both name and description using folderId for sure|| ROLES permissions : ADMINS.^^");
            }
            return operation;
        };
    }

    @Bean
    public OperationCustomizer FileController(OperationService operationBuilder) {
        return (operation, handlerMethod) -> {
            if (handlerMethod.getMethod().getName().equals("addFile")) {
                operation
                        .summary("add a file in a folder")
                        .description("adding a file to a specific folder, using the folderId || ROLES permissions : ALL USERS.^^");
            }
            if (handlerMethod.getMethod().getName().equals("getAcceptedFiles")) {
                operation
                        .summary("retrieve files in a folder")
                        .description("get all files related to a folder || ROLES permissions : ALL USERS.^^");
            }
            if (handlerMethod.getMethod().getName().equals("getFileAsBase64")) {
                operation
                        .summary("retrieve the file as BASE64")
                        .description("retrieve specific file encoded in BASE64 and its extension by fileId || ROLES permissions : ALL USERS.^^");
            }
            if (handlerMethod.getMethod().getName().equals("getPendingFiles")) {
                operation
                        .summary("retrieve pending files as mod")
                        .description("get all files related to a folder || ROLES permissions : MODERATOR");
            }
            if (handlerMethod.getMethod().getName().equals("rejectFile")) {
                operation
                        .summary("reject a pending file")
                        .description("|| ROLES permissions : MODERATOR");
            }
            if (handlerMethod.getMethod().getName().equals("acceptFile")) {
                operation
                        .summary("accept a pending file")
                        .description("|| ROLES permissions : MODERATOR");
            }
            if (handlerMethod.getMethod().getName().equals("deleteFile")) {
                operation
                        .summary("delete a file")
                        .description("|| ROLES permissions :ADMIN, MODERATOR");
            }
            return operation;
        };
    }
}
