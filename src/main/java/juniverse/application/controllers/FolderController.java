package juniverse.application.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import juniverse.application.dtos.ApiResponse;
import juniverse.application.dtos.folder.FolderRequest;
import juniverse.application.helpers.ApiResponseHelper;
import juniverse.domain.mappers.FolderMapper;
import juniverse.domain.services.FolderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/folder")
@Tag(name = "FOLDERS")
public class FolderController {

    private final FolderService folderService;

    private final ApiResponseHelper apiResponseHelper;

    private final FolderMapper folderMapper;


    @PostMapping
    public ResponseEntity<ApiResponse<Boolean>> addFolder(@RequestBody FolderRequest folderRequest) {
        try {
            boolean isFail=!(folderService.addFolder(folderMapper.requestToModel(folderRequest)));
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to send" : "message sent successfully", isFail ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);

        }
    }
}
// TO DO LIST
//upload a folder
//get all folders
//delete a folder
