package juniverse.application.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import juniverse.application.dtos.ApiResponse;
import juniverse.application.dtos.event.EventRequest;
import juniverse.application.dtos.event.EventResponse;
import juniverse.application.helpers.ApiResponseHelper;
import juniverse.domain.mappers.EventMapper;
import juniverse.domain.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/events")
@Tag(name="EVENTS")
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final ApiResponseHelper apiResponseHelper;

    @PostMapping()
    public ResponseEntity<ApiResponse<Boolean>> addEvent(@RequestBody EventRequest eventRequest) {
        try{
            boolean isFail= !eventService.addEvent(eventMapper.requestToModel(eventRequest));
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to add" : "event added successfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        }catch(Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
    @GetMapping()
    public ResponseEntity<ApiResponse<List<EventResponse>>> getEvents(){
        try{
            List<EventResponse> response= (eventService.getEvents()).stream().map(eventMapper::modelToResponse).toList();
            boolean isFail = response.isEmpty();
            return apiResponseHelper.buildApiResponse(response, !isFail, isFail ? "there's no events" : " retrieved successfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        }catch (Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
    @DeleteMapping("/{eventId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteEvent(@PathVariable Long eventId) {
        try{
            boolean isFail= !eventService.deleteEvent(eventId);
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to delete" : "event deleted successfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        }catch (Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
}
