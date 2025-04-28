package juniverse.persistance.jpa.news;

import juniverse.persistance.entities.news.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventJpaRepository extends JpaRepository<EventEntity, Long> {
}
