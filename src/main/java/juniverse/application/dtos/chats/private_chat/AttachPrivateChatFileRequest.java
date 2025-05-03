package juniverse.application.dtos.chats.private_chat;

import lombok.Data;

@Data
public class AttachPrivateChatFileRequest {

    private String name;

    private String extension;

    private String fileAsBase64;


}
