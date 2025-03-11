package juniverse.domain.models;

import juniverse.domain.enums.FileExtension;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EncodedFileModel {
    private String fileAsBase64;
    private FileExtension extension;
}
