package juniverse.application.dtos.file;

import lombok.Data;

import java.util.Base64;

@Data
public class FileRequest {

    private String name;

    private String extension;

    private String description;

    private String fileAsBase64;

    private Long folderId;


}
