package juniverse.application.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import juniverse.application.dtos.ApiResponse;
import juniverse.application.dtos.event.EventRequest;
import juniverse.application.helpers.ApiResponseHelper;
import juniverse.domain.mappers.EventMapper;
import juniverse.domain.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to update" : "file updated successfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        }catch(Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
}
