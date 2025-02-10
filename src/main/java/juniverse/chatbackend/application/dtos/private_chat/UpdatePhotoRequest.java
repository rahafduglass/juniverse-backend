package juniverse.chatbackend.application.dtos.private_chat;

import lombok.Data;

@Data
public class UpdatePhotoRequest {
    private String photoAsBase64;
}
