package juniverse.persistance.repositories;

import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository {
    boolean updateQuote(String quote);
}
