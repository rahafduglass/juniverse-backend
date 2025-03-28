package juniverse.domain.services;

import juniverse.application.dtos.news.NewsRequest;
import juniverse.application.dtos.news.NewsResponse;
import juniverse.domain.models.NewsModel;
import juniverse.domain.provider.IdentityProvider;
import juniverse.persistance.repositories.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final IdentityProvider identityProvider;

    public boolean addNews(NewsModel newsModel) {
        newsModel.setAuthorId(identityProvider.currentIdentity().getId());
        newsModel.setCreatedAt(LocalDateTime.now());
        newsRepository.addNews(newsModel);
        return true;
    }

    public List<NewsModel> getAllNews() {
        return newsRepository.getAllNews();
    }

    public boolean updateNews(Long newsId,NewsModel newsModel) {
        newsModel.setId(newsId);
        newsModel.setUpdatedAt(LocalDateTime.now());
        newsModel.setUpdatedById(identityProvider.currentIdentity().getId());
        return newsRepository.updateNews(newsModel);
    }

    public boolean deleteNews(Long newsId) {
        return newsRepository.deleteNews(newsId);
    }
}
