package juniverse.application.dtos.file;

import juniverse.domain.enums.FileExtension;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EncodedFileResponse {
    private String fileAsBase64;
    private FileExtension extension;
}
