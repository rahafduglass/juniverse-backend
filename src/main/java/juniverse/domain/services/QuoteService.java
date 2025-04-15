package juniverse.domain.services;

import juniverse.domain.provider.IdentityProvider;
import juniverse.persistance.repositories.QuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final IdentityProvider identityProvider;

    public boolean updateQuote(String quote) {
        return quoteRepository.updateQuote(quote);
    }

    public String getUserQuote() {
        return quoteRepository.getQuoteByOwnerId(identityProvider.currentIdentity().getId());
    }
}
