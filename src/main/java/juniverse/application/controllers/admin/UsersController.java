package juniverse.application.controllers.admin;


import io.swagger.v3.oas.annotations.tags.Tag;
import juniverse.application.dtos.ApiResponse;
import juniverse.application.dtos.sys_user.SysUserResponse;
import juniverse.application.helpers.ApiResponseHelper;
import juniverse.domain.enums.UserRole;
import juniverse.domain.mappers.user.SysUserMapper;
import juniverse.domain.services.dashboard.SysUserService;
import juniverse.domain.services.admin.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User-Management")
public class UsersController {

    private final UsersService usersService;
    private final ApiResponseHelper apiResponseHelper;
    private final SysUserService sysUserService;
    private final SysUserMapper sysUserMapper;

    @PutMapping("/{studentId}/promote")
    public ResponseEntity<ApiResponse<Boolean>> promoteStudent(@PathVariable Long studentId) {
        try {
            boolean isFail = !usersService.promoteStudent(studentId);
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to promote" : "student promoted successfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/{moderatorId}/demote")
    public ResponseEntity<ApiResponse<Boolean>> demoteModerator(@PathVariable Long moderatorId) {
        try {
            boolean isFail = !usersService.demoteModerator(moderatorId);
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to demote" : "moderator demoted successfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/{userId}/ban")
    public ResponseEntity<ApiResponse<Boolean>> banUser(@PathVariable Long userId) {
        try {
            boolean isFail = !usersService.banUser(userId);
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to ban" : "user banned successfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/banned")
    public ResponseEntity<ApiResponse<List<SysUserResponse>>> getBannedUsers() {
        try {
            List<SysUserResponse> response = (sysUserService.getBannedUsers()).stream().map(sysUserMapper::modelToResponse).toList();
            boolean isFail = response.isEmpty();
            return apiResponseHelper.buildApiResponse(response, !isFail, isFail ? "there are no banned users" : " retrieved succesfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/{role}")
    public ResponseEntity<ApiResponse<List<SysUserResponse>>> getUsersByRole(@PathVariable UserRole role) {
        try {
            List<SysUserResponse> response = (sysUserService.getUsersByRole(role)).stream().map(sysUserMapper::modelToResponse).toList();
            boolean isFail = response.isEmpty();
            return apiResponseHelper.buildApiResponse(response, !isFail, isFail ? "there are no users" : " retrieved succesfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/{userId}/unban")
    public ResponseEntity<ApiResponse<Boolean>> unbanUser(@PathVariable Long userId) {
        try {
            boolean isFail = !usersService.unbanUser(userId);
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to unban" : "user ubanned successfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
}
