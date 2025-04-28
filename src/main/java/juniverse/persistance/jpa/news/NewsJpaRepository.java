package juniverse.persistance.jpa.news;

import jakarta.transaction.Transactional;
import juniverse.persistance.entities.news.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface NewsJpaRepository extends JpaRepository<NewsEntity, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE news n SET n.updatedAt=:updatedAt,n.updatedBy.id=:updatedById,n.content=:content,n.title=:title WHERE n.id=:id")
    int updateNews(Long id, LocalDateTime updatedAt, Long updatedById, String content, String title);
}
