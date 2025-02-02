package juniverse.chatbackend.configuration.documentation;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OperationCustomizer;
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
                        .description("Private Chat: API to manage private chats between the users and the therapist. ")

                );
    }

    @Bean
    public OperationCustomizer privateChatControllerOperationCustomizer() {
        return (operation, handlerMethod) -> {

            if (handlerMethod.getMethod().getName().equals("getUserChatMessages")) {
                operation.summary("retrieve all messages for a private chat by userId")
                        .description("fetches all messages associated with the userId only not therapistId.");
            }
            if (handlerMethod.getMethod().getName().equals("getTherapistChats")) {
                operation.summary("Get all the therapist's private chats")
                        .description("Fetches all private chat conversations that have been initialized with the therapist.");
            }
            if (handlerMethod.getMethod().getName().equals("getPrivateChatById")) {
                operation.summary("Get private chat metadata")
                        .description("response will be schema PrivateChatResponse except PrivateChatResponse.unreadMessagesCount");
            }
            if (handlerMethod.getMethod().getName().equals("markChatMessagesAsRead")) {
                operation.summary("mark a private chat as READ");
            }
            if (handlerMethod.getMethod().getName().equals("sendPrivateMessage")) {
                operation.summary("sending a message to a private chat")
                        .description("1. if the sender is a regular user the receiver id=2 because we have one therapist, " +
                                "2. if the sender is a therapist, the receiver id depends on the userId that was already fetched in therapist chats.");
            }
            // Return the modified operation object
            return operation;
        };
    }

    @Bean
    public OperationCustomizer AuthenticationControllerOperationCustomizer() {
        return (operation, handlerMethod) -> {

            if (handlerMethod.getMethod().getName().equals("authenticateUser")) {
                operation.summary("user send their credentials to authenticate their identity to be given access to the system.");
            }

            return operation;
        };
    }

}
