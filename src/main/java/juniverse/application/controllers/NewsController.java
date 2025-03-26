package juniverse.application.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import juniverse.application.dtos.ApiResponse;
import juniverse.application.dtos.news.NewsRequest;
import juniverse.application.helpers.ApiResponseHelper;
import juniverse.domain.mappers.NewsMapper;
import juniverse.domain.services.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2/news")
@RequiredArgsConstructor
@Tag(name="NEWS")
public class NewsController {

    private final NewsService newsService;
    private final ApiResponseHelper apiResponseHelper;
    private final NewsMapper newsMapper;

    @PostMapping()
    public ResponseEntity<ApiResponse<Boolean>> addNews(@RequestBody NewsRequest newsRequest){
        try{
            boolean isFail= !newsService.addNews(newsMapper.requestToModel(newsRequest));
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to add" : "news added successfully", isFail ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);
        }catch(Exception e){
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
}
