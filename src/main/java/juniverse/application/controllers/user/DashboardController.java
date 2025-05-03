package juniverse.application.controllers.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import juniverse.application.dtos.ApiResponse;
import juniverse.application.dtos.dashboard.TaskResponse;
import juniverse.application.dtos.file.FileResponse;
import juniverse.application.helpers.ApiResponseHelper;
import juniverse.domain.mappers.filesharing.FileMapper;
import juniverse.domain.mappers.dashboard.TaskMapper;
import juniverse.domain.services.filesharing.FileService;
import juniverse.domain.services.dashboard.QuoteService;
import juniverse.domain.services.dashboard.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/dashboard")
@RestController
@Tag(name="DASHBOARD")
@RequiredArgsConstructor
public class DashboardController {

    private final ApiResponseHelper apiResponseHelper;

    private final FileService fileService;
    private final FileMapper fileMapper;

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    private final QuoteService quoteService;


    @GetMapping("/files/pending")
    public ResponseEntity<ApiResponse<List<FileResponse>>> getUserPendingFiles(){
        try{
            List<FileResponse> response= (fileService.getUserPendingFiles()).stream()
                    .map(fileMapper::modelToResponse)
                    .collect(Collectors.toList());
            boolean isFail = response.isEmpty();
            return apiResponseHelper.buildApiResponse(response, !isFail, isFail ? "there's no files" : " retrieved successfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        }catch(Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/files/accepted")
    public ResponseEntity<ApiResponse<List<FileResponse>>> getUserAcceptedFiles(){
        try{
            List<FileResponse> response= (fileService.getUserAcceptedFiles()).stream()
                    .map(fileMapper::modelToResponse)
                    .collect(Collectors.toList());
            boolean isFail = response.isEmpty();
            return apiResponseHelper.buildApiResponse(response, !isFail, isFail ? "there's no files" : " retrieved successfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        }catch(Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
    @PostMapping("/tasks")
    public ResponseEntity<ApiResponse<Boolean>> addTask(String taskTitle){
        try {
            boolean isFail = !taskService.addTask(taskTitle);
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to add" : "task added successfully", isFail ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/tasks")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getTasks(){
        try {
            List<TaskResponse> response = (taskService.getTasks()).stream()
                    .map(taskMapper::modelToResponse)
                    .sorted(Comparator.comparing(TaskResponse::getId)).toList();

            boolean isFail = response.isEmpty();
            return apiResponseHelper.buildApiResponse(response, !isFail, isFail ? "failed to get" : "tasks retrieved successfully", isFail ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/tasks/{taskId}/check")
    public ResponseEntity<ApiResponse<Boolean>> checkTask(@PathVariable Long taskId){
        try{
            boolean isFail= !taskService.checkTask(taskId);
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to check" : "task checked successfully", isFail ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);
        }catch (Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
    @PutMapping("/tasks/{taskId}/uncheck")
    public ResponseEntity<ApiResponse<Boolean>> uncheckTask(@PathVariable Long taskId){
        try{
            boolean isFail= !taskService.uncheck(taskId);
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to check" : "task checked successfully", isFail ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);
        }catch (Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteTask(@PathVariable Long taskId){
        try{
            boolean isFail= !taskService.deleteTask(taskId);
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to delete" : "task deleted successfully", isFail ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);
        }catch (Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/quotes")
    public ResponseEntity<ApiResponse<Boolean>> updateQuote(String quote){
        try{
            boolean isFail= !quoteService.updateQuote(quote);
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to update" : "quote updated successfully", isFail ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);
        }catch (Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/quotes/quote")
    public ResponseEntity<ApiResponse<String>> getUserQuote(){
        try {
            String response = quoteService.getUserQuote();
            boolean isFail = response.isEmpty();
            return apiResponseHelper.buildApiResponse(response, !isFail, isFail ? "failed to get" : "tasks retrieved successfully", isFail ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
}
