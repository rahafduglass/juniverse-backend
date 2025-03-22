package juniverse.persistance.jpa;

import juniverse.persistance.entities.MessageEntity;
import juniverse.persistance.entities.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsJpaRepository extends JpaRepository<NewsEntity, Long> {
}
