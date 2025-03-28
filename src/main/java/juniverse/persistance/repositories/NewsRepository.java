package juniverse.persistance.repositories;

import juniverse.domain.models.NewsModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository {
    void addNews(NewsModel newsModel);

    List<NewsModel> getAllNews();

    boolean updateNews(NewsModel newsModel);

    boolean deleteNews(Long newsId);
}
