package juniverse.application.dtos.sys_user;

import lombok.Data;

@Data
public class UpdatePhotoRequest {
    private String photoAsBase64;
    private String fileExtension;
}
