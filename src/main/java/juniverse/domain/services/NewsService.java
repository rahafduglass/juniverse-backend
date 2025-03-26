package juniverse.domain.services;

import juniverse.domain.models.NewsModel;
import juniverse.domain.provider.IdentityProvider;
import juniverse.persistance.repositories.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
}
