package juniverse.application.dtos.file;

import lombok.Data;

import java.util.Base64;

@Data
public class FileRequest {
    private String FileName;
    private String extension;
    private String description;
    private Base64 fileAsBase64;
    private Long folderId;
}
