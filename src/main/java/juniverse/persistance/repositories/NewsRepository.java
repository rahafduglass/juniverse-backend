package juniverse.persistance.repositories;

import juniverse.domain.models.NewsModel;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository {
    void addNews(NewsModel newsModel);
}
