package juniverse.persistance.adapter;

import juniverse.domain.mappers.NewsMapper;
import juniverse.domain.models.NewsModel;
import juniverse.persistance.jpa.NewsJpaRepository;
import juniverse.persistance.repositories.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NewsAdapter implements NewsRepository {

    private final NewsJpaRepository newsJpaRepository;
    private final NewsMapper newsMapper;

    @Override
    public void addNews(NewsModel newsModel) {
        newsJpaRepository.save(newsMapper.modelToEntity(newsModel));
    }
}
