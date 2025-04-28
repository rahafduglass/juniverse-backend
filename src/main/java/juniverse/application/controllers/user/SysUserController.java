package juniverse.application.controllers.user;


import io.swagger.v3.oas.annotations.tags.Tag;
import juniverse.application.dtos.ApiResponse;
import juniverse.application.dtos.sys_user.ProfileAndCoverPicturesResponse;
import juniverse.application.dtos.sys_user.SysUserProfileResponse;
import juniverse.application.dtos.sys_user.UpdateBioRequest;
import juniverse.application.dtos.sys_user.UpdatePhotoRequest;
import juniverse.application.helpers.ApiResponseHelper;
import juniverse.domain.mappers.user.SysUserMapper;
import juniverse.domain.services.dashboard.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/sys-user")
@Tag(name = "USER-PROFILE")
public class SysUserController {

    private final SysUserService sysUserService;
    private final SysUserMapper sysUserMapper;
    private final ApiResponseHelper apiResponseHelper;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<SysUserProfileResponse>> getProfile() {
        try {
            SysUserProfileResponse response = sysUserMapper.modelToDTO(sysUserService.getProfile());
            boolean isFail = response == null;
            if (response != null && response.getBio() == null)
                response.setBio("");
            return apiResponseHelper.buildApiResponse(response, !isFail
                    , (isFail ? "couldn't update" : "profile updated successfully")
                    , (isFail ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK));
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, "An error occurred: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }

    }

    @PutMapping("/bio")
    public ResponseEntity<ApiResponse<Boolean>> updateBio(@RequestBody UpdateBioRequest request) {
        try {
            Boolean isUpdated = sysUserService.updateBio(sysUserMapper.requestToModel(request));
            return apiResponseHelper.buildApiResponse(isUpdated, isUpdated
                    , (!isUpdated ? "couldn't update" : "profile updated successfully")
                    , (!isUpdated ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK));
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, "An error occurred: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/profile-picture")
    public ResponseEntity<ApiResponse<Boolean>> updateProfilePicture(@RequestBody UpdatePhotoRequest request) {
        try {
            boolean isUpdated = sysUserService.updateProfilePicture(request.getPhotoAsBase64(), request.getFileExtension());
            return apiResponseHelper.buildApiResponse(isUpdated, isUpdated
                    , (!isUpdated ? "couldn't update" : "profile picture updated successfully")
                    , (!isUpdated ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK));
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, "An error occurred: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/cover-picture")
    public ResponseEntity<ApiResponse<Boolean>> updateCoverPicture(@RequestBody UpdatePhotoRequest request) {
        try {
            boolean isUpdated = sysUserService.updateCoverPicture(request.getPhotoAsBase64(), request.getFileExtension());
            return apiResponseHelper.buildApiResponse(isUpdated, isUpdated
                    , (!isUpdated ? "couldn't update" : "cover picture updated successfully")
                    , (!isUpdated ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK));
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, "An error occurred: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/profile-and-cover-picture")
    public ResponseEntity<ApiResponse<ProfileAndCoverPicturesResponse>> getProfileAndCoverPictures() {
        try {
            var oGetCoverPicture = sysUserService.getCoverPicture();
            var oGetProfilePicture = sysUserService.getProfilePicture();
            ProfileAndCoverPicturesResponse response = new ProfileAndCoverPicturesResponse((String) ((Object[]) oGetProfilePicture[0])[0], (String) ((Object[]) oGetProfilePicture[0])[1], (String) ((Object[]) oGetCoverPicture[0])[0], (String) ((Object[]) oGetCoverPicture[0])[1]);
            boolean areNotFetched = (response.getCoverPicturesBase64().isEmpty()) && (response.getProfilePictureBase64().isEmpty());
            return apiResponseHelper.buildApiResponse(response, !areNotFetched
                    , (areNotFetched ? "couldn't retrieve" : "retrieved successfully")
                    , (areNotFetched ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK));
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, "An error occurred: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/profile-picture")
    public ResponseEntity<ApiResponse<Boolean>> deleteProfilePicture() {
        try {
            boolean isDeleted = sysUserService.deleteProfilePicture();
            return apiResponseHelper.buildApiResponse(isDeleted, !isDeleted
                    , (!isDeleted ? "couldn't delete" : "deleted successfully")
                    , (!isDeleted ? HttpStatus.NOT_FOUND : HttpStatus.OK));
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, "An error occurred: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/cover-picture")
    public ResponseEntity<ApiResponse<Boolean>> deleteCoverPicture() {
        try {
            boolean isDeleted = sysUserService.deleteCoverPicture();
            return apiResponseHelper.buildApiResponse(isDeleted, !isDeleted
                    , (!isDeleted ? "couldn't delete" : "deleted successfully")
                    , (!isDeleted ? HttpStatus.NOT_FOUND : HttpStatus.OK));
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, "An error occurred: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

}
