package juniverse.chatbackend.application.controllers;


import io.swagger.v3.oas.annotations.tags.Tag;
import juniverse.chatbackend.application.dtos.*;
import juniverse.chatbackend.application.dtos.private_chat.UpdatePhotoRequest;
import juniverse.chatbackend.application.helpers.ApiResponseHelper;
import juniverse.chatbackend.domain.mappers.SysUserMapper;
import juniverse.chatbackend.domain.services.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            return apiResponseHelper.buildApiResponse(response, !isFail
                    , (isFail ? "couldn't update" : "profile updated successfully")
                    , (isFail ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK));
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, "An error occurred: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }

    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<Boolean>> updateProfile(@RequestBody UpdateSysUserProfileRequest request) {
        try {
            Boolean isUpdated = sysUserService.updateProfile(sysUserMapper.requestToModel(request));
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
            boolean isUpdated = sysUserService.updateProfilePicture(request.getPhotoAsBase64());
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
            boolean isUpdated = sysUserService.updateCoverPicture(request.getPhotoAsBase64());
            return apiResponseHelper.buildApiResponse(isUpdated, isUpdated
                    , (!isUpdated ? "couldn't update" : "cover picture updated successfully")
                    , (!isUpdated ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK));
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, "An error occurred: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }


    @GetMapping("/profile-and-cover-picture")
    public ResponseEntity<ApiResponse<ProfileAndCoverPicturesResponse>>  getProfileAndCoverPictures() {
        try{
            String profilePhotoBase64=  sysUserService.getProfilePicture();
            String coverPhotoBase64=sysUserService.getCoverPicture();
            ProfileAndCoverPicturesResponse response= new ProfileAndCoverPicturesResponse(profilePhotoBase64,coverPhotoBase64);
            boolean areNotFetched=(response.getCoverPicturesBase64()==null)&&(response.getProfilePictureBase64()==null);
            return apiResponseHelper.buildApiResponse(response, !areNotFetched
                    , (areNotFetched ? "couldn't retrieve" : "retrieved successfully")
                    , (areNotFetched? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK));

        }catch(Exception e){
            return apiResponseHelper.buildApiResponse(null, false, "An error occurred: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
}
