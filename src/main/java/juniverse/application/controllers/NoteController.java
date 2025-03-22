package juniverse.application.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import juniverse.application.dtos.ApiResponse;
import juniverse.application.dtos.note.NoteRequest;
import juniverse.application.dtos.note.NoteResponse;
import juniverse.application.helpers.ApiResponseHelper;
import juniverse.domain.mappers.NoteMapper;
import juniverse.domain.services.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1")
@Tag(name = "NOTES")
public class NoteController {
    private final NoteService noteService;
    private final NoteMapper noteMapper;
    private final ApiResponseHelper apiResponseHelper;

    @PostMapping("notes/note")
    public ResponseEntity<ApiResponse<Boolean>> addNote(NoteRequest noteRequest) {
        try {
            boolean isFail = !noteService.addNote(noteMapper.requestToModel(noteRequest));
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to add" : "note added successfully", isFail ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/private-chat/{chatId}/notes")
    public ResponseEntity<ApiResponse<List<NoteResponse>>> getNotes(@PathVariable Long chatId) {
        try {
            List<NoteResponse> response = (noteService.getNotes(chatId)).stream()
                    .map(noteMapper::modelToResponse)
                    .collect(Collectors.toList());
            boolean isFail = response.isEmpty();
            return apiResponseHelper.buildApiResponse(response, !isFail, isFail ? "there's no files" : " retrieved succesfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/notes/note/{noteId}")
    public ResponseEntity<ApiResponse<Boolean>> updateNote(@PathVariable Long noteId, String title, String description) {
        try {
            boolean isFail = !noteService.updateNote(noteId, title, description);
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "cant update" : "updated successfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/notes/note/{noteId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteNote(@PathVariable Long noteId) {
        try{
            boolean isFail=!noteService.deleteNote(noteId);
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to delete" : "deleted successfully", isFail ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);
        }catch(Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

}
