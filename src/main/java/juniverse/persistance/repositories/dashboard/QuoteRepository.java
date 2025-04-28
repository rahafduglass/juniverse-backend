package juniverse.persistance.repositories.dashboard;

import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository {
    boolean updateQuote(String quote);

    String getQuoteByOwnerId(Long id);
}
