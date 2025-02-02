package juniverse.chatbackend.configuration.documentation;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

        @Bean
        public OperationCustomizer privateChatControllerOperationCustomizer() {
            return (operation, handlerMethod) -> {

                if (handlerMethod.getMethod().getName().equals("getUserChatMessages")) {
                    operation.summary("Get all messages for a private chat(therapist-user) by userId")
                            .description("fetches all messages associated with the userId only not therapistId.");
                }
                if (handlerMethod.getMethod().getName().equals("getTherapistChats")) {
                    operation.summary("Get all private chats initialized with the therapist")
                            .description("Fetches all private chat conversations that have been initialized with the therapist.");
                }
                if (handlerMethod.getMethod().getName().equals("getPrivateChatById")) {
                    operation.summary("Get chat info by chat id");
                }
                if (handlerMethod.getMethod().getName().equals("markChatMessagesAsRead")) {
                    operation.summary("update and mark a private chat received messages as READ when a user enters the chat");
                }
                if (handlerMethod.getMethod().getName().equals("sendPrivateMessage")) {
                    operation.summary("if a user wants to initialize a chat or resume an already initialized chat ")
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
