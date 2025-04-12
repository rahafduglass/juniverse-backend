package juniverse.domain.services;

import juniverse.persistance.repositories.QuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final QuoteRepository quoteRepository;

    public boolean updateQuote(String quote) {
        return quoteRepository.updateQuote(quote);
    }
}
