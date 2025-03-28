package juniverse.persistance.adapter;

import juniverse.domain.mappers.NewsMapper;
import juniverse.domain.models.NewsModel;
import juniverse.persistance.jpa.NewsJpaRepository;
import juniverse.persistance.repositories.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class NewsAdapter implements NewsRepository {

    private final NewsJpaRepository newsJpaRepository;
    private final NewsMapper newsMapper;

    @Override
    public void addNews(NewsModel newsModel) {
        newsJpaRepository.save(newsMapper.modelToEntity(newsModel));
    }

    @Override
    public List<NewsModel> getAllNews() {
        return (newsJpaRepository.findAll()).stream().map(newsMapper::entityToModel).toList();
    }

    @Override
    public boolean updateNews(NewsModel newsModel) {
       return newsJpaRepository.updateNews(newsModel.getId()
                ,newsModel.getUpdatedAt()
                ,newsModel.getUpdatedById()
                ,newsModel.getContent()
                ,newsModel.getTitle())>0;
    }

    @Override
    public boolean deleteNews(Long newsId) {
        newsJpaRepository.delete(newsJpaRepository.findById(newsId).get());
        return true;
    }
}
