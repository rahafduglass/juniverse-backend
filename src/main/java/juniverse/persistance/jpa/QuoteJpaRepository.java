package juniverse.persistance.jpa;

import jakarta.transaction.Transactional;
import juniverse.persistance.entities.QuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuoteJpaRepository extends JpaRepository<QuoteEntity,Long> {

    @Query("SELECT q FROM quote q WHERE q.owner.id=:id")
    Optional<QuoteEntity> findByOwnerId(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE quote q SET q.quote=:quote WHERE q.owner.id=:ownerId")
    Integer updateQuoteByOwnerId(String quote, Long ownerId);
}
