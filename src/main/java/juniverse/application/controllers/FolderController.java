package juniverse.application.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import juniverse.application.dtos.ApiResponse;
import juniverse.application.dtos.folder.FolderRequest;
import juniverse.application.dtos.folder.FolderResponse;
import juniverse.application.helpers.ApiResponseHelper;
import juniverse.domain.mappers.FolderMapper;
import juniverse.domain.models.FolderModel;
import juniverse.domain.services.FolderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to add" : "folder added successfully", isFail ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/{folderId}")
    public ResponseEntity<ApiResponse<Boolean>> updateFolder(@PathVariable Long folderId,@RequestBody FolderRequest folderRequest) {
        try{
            FolderModel request=folderMapper.requestToModel(folderRequest);
            request.setId(folderId);
            boolean isFail=!(folderService.updateFolder(request));
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to add" : "folder added successfully", isFail ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);

        }catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FolderResponse>>> getFolders() {
        try{
            List<FolderResponse> results= folderMapper.listOfModelsToListOfResponses(folderService.getFolders());
            boolean isFail= results==null;
            return apiResponseHelper.buildApiResponse(results, !isFail, isFail ? "failed to retrieve folders" : "retrieved successfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);

        }catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }


}
// TO DO LIST
//get all folders
//delete a folder
//edit folder's name
//edit folder's description