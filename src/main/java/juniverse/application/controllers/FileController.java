package juniverse.application.controllers;

import juniverse.application.dtos.ApiResponse;
import juniverse.application.dtos.file.FileRequest;
import juniverse.application.dtos.file.FileResponse;
import juniverse.application.helpers.ApiResponseHelper;
import juniverse.domain.mappers.FileMapper;
import juniverse.domain.services.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/files")
public class FileController {

    private final ApiResponseHelper apiResponseHelper;
    private final FileService fileService;
    private final FileMapper fileMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<Boolean>> addFolder(@RequestBody FileRequest fileRequest){
        try {
            boolean isFail = !fileService.addFolder(fileMapper.requestToModel(fileRequest), fileRequest.getFileAsBase64());
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to add" : "file added successfully", isFail ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);
        }catch(Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
    //TODO

    @GetMapping("/folders/{folderId}")
    public ResponseEntity<ApiResponse<List<FileResponse>>> getAcceptedFiles(@PathVariable Long folderId){
        try {
            List<FileResponse> response = (fileService.getAcceptedFiles(folderId)).stream().map(element->fileMapper.modelToResponse(element)).collect(Collectors.toList());
            boolean isFail = response.isEmpty();
            return apiResponseHelper.buildApiResponse(response,!isFail, isFail?"failed to get files":" retrieved succesfully",  isFail ?HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);
        }catch(Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

}
