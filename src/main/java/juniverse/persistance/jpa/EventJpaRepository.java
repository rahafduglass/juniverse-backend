package juniverse.persistance.jpa;

import juniverse.persistance.entities.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventJpaRepository extends JpaRepository<EventEntity, Long> {
}
