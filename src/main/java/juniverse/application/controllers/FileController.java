package juniverse.application.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import juniverse.application.dtos.ApiResponse;
import juniverse.application.dtos.file.EncodedFileResponse;
import juniverse.application.dtos.file.FileRequest;
import juniverse.application.dtos.file.FileResponse;
import juniverse.application.helpers.ApiResponseHelper;
import juniverse.domain.enums.FileStatus;
import juniverse.domain.mappers.FileMapper;
import juniverse.domain.models.EncodedFileModel;
import juniverse.domain.services.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@Tag(name = "FILES")
@RequestMapping("api/v1/files")
public class FileController {

    private final ApiResponseHelper apiResponseHelper;
    private final FileService fileService;
    private final FileMapper fileMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<Boolean>> addFile(@RequestBody FileRequest fileRequest) {
        try {
            boolean isFail = !fileService.addFile(fileMapper.requestToModel(fileRequest), fileRequest.getFileAsBase64());
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to add" : "file added successfully", isFail ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/{folderId}")
    public ResponseEntity<ApiResponse<List<FileResponse>>> getAcceptedFiles(@PathVariable Long folderId) {
        try {
            List<FileResponse> response = (fileService.getAcceptedFiles(folderId)).stream()
                    .map(fileMapper::modelToResponse)
                    .collect(Collectors.toList());
            boolean isFail = response.isEmpty();
            return apiResponseHelper.buildApiResponse(response, !isFail, isFail ? "there's no files" : " retrieved succesfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/file/{fileId}")
    public ResponseEntity<ApiResponse<EncodedFileResponse>> getFileAsBase64(@PathVariable Long fileId) {
        try {
            EncodedFileModel modelResponse = fileService.getFileAsBase64(fileId);
            EncodedFileResponse response = EncodedFileResponse.builder()
                    .fileAsBase64(modelResponse.getFileAsBase64())
                    .extension(modelResponse.getExtension())
                    .build();

            boolean isFail = response.getFileAsBase64().isEmpty();
            return apiResponseHelper.buildApiResponse(response, !isFail, isFail ? "there's no such file" : " retrieved succesfully", isFail ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);

        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/file/pending/{folderId}")
    public ResponseEntity<ApiResponse<List<FileResponse>>> getPendingFiles(@PathVariable Long folderId) {
        try{
            List<FileResponse> response= (fileService.getPendingFiles(folderId)).stream()
                    .map(fileMapper::modelToResponse)
                    .collect(Collectors.toList());
            boolean isFail = response.isEmpty();
            return apiResponseHelper.buildApiResponse(response, !isFail, isFail ? "there's no files" : " retrieved succesfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        }catch(Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("file/pending")
    public ResponseEntity<ApiResponse<List<FileResponse>>> getPendingFiles(){
        try{
            List<FileResponse> response= (fileService.getPendingFiles()).stream()
                    .map(fileMapper::modelToResponse)
                    .collect(Collectors.toList());
            boolean isFail = response.isEmpty();
            return apiResponseHelper.buildApiResponse(response, !isFail, isFail ? "there's no files" : " retrieved succesfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        }catch(Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/file/{fileId}/reject")
    public ResponseEntity<ApiResponse<Boolean>> rejectFile(@PathVariable Long fileId) {
        try{
            boolean isFail= !fileService.updateFileStatus(fileId,FileStatus.REJECTED);
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to update" : "file updated successfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        }catch(Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/file/{fileId}/accept")
    public ResponseEntity<ApiResponse<Boolean>> acceptFile(@PathVariable Long fileId) {
        try{
            boolean isFail= !fileService.updateFileStatus(fileId,FileStatus.ACCEPTED);
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to update" : "file updated successfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        }catch(Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/file/{fileId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteFile(@PathVariable Long fileId) {
        try{
            boolean isFail= !fileService.deleteFile(fileId);
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to update" : "file updated successfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        }catch(Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
}
