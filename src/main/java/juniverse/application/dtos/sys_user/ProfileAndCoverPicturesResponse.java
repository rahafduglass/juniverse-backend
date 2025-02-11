package juniverse.application.dtos.sys_user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileAndCoverPicturesResponse {

    private String profilePictureBase64;

    private String coverPicturesBase64;

}
