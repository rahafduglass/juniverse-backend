package juniverse.application.controllers;

import jakarta.persistence.Access;
import juniverse.application.dtos.ApiResponse;
import juniverse.application.dtos.file.FileRequest;
import juniverse.application.helpers.ApiResponseHelper;
import juniverse.domain.mappers.FileMapper;
import juniverse.domain.services.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to add" : "folder added successfully", isFail ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);
        }catch(Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
}
