package juniverse.application.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import juniverse.application.dtos.ApiResponse;
import juniverse.application.dtos.news.NewsRequest;
import juniverse.application.dtos.news.NewsResponse;
import juniverse.application.helpers.ApiResponseHelper;
import juniverse.domain.mappers.NewsMapper;
import juniverse.domain.services.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/news")
@RequiredArgsConstructor
@Tag(name = "NEWS")
public class NewsController {

    private final NewsService newsService;
    private final ApiResponseHelper apiResponseHelper;
    private final NewsMapper newsMapper;

    @PostMapping()
    public ResponseEntity<ApiResponse<Boolean>> addNews(@RequestBody NewsRequest newsRequest) {
        try {
            boolean isFail = !newsService.addNews(newsMapper.requestToModel(newsRequest));
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to add" : "news added successfully", isFail ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<NewsResponse>>> getAllNews() {
        try {
            List<NewsResponse> response = (newsService.getAllNews()).stream().map(newsMapper::modelToResponse).toList();
            boolean isFail = response.isEmpty();
            return apiResponseHelper.buildApiResponse(response, !isFail, isFail ? "there are no news" : " retrieved succesfully", isFail ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/{newsId}")
    public ResponseEntity<ApiResponse<Boolean>> updateNews(@PathVariable Long newsId, @RequestBody NewsRequest newsRequest) {
        try {
            boolean isFail = !newsService.updateNews(newsId, newsMapper.requestToModel(newsRequest));
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to update" : "news updated successfully", isFail ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/{newsId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteNews(@PathVariable Long newsId) {
        try {
            boolean isFail = !newsService.deleteNews(newsId);
            return apiResponseHelper.buildApiResponse(!isFail, !isFail, isFail ? "failed to delete" : "news deleted successfully", isFail ? HttpStatus.EXPECTATION_FAILED : HttpStatus.OK);
        } catch (Exception e) {
            return apiResponseHelper.buildApiResponse(null, false, e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
}
