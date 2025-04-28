package juniverse.persistance.adapter.dashboard;

import juniverse.domain.provider.IdentityProvider;
import juniverse.persistance.entities.dashboard.QuoteEntity;
import juniverse.persistance.entities.user.SysUserEntity;
import juniverse.persistance.jpa.dashboard.QuoteJpaRepository;
import juniverse.persistance.repositories.dashboard.QuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuoteAdapter implements QuoteRepository {

    private final QuoteJpaRepository quoteJpaRepository;
    private final IdentityProvider identityProvider;

    @Override
    public boolean updateQuote(String quote) {
        SysUserEntity owner = identityProvider.currentIdentity();

        if (quoteJpaRepository.findByOwnerId(owner.getId()).isPresent())
            return quoteJpaRepository.updateQuoteByOwnerId(quote, owner.getId()) > 0;

        QuoteEntity quoteEntity = new QuoteEntity();
        quoteEntity.setQuote(quote);
        quoteEntity.setOwner(owner);
        quoteJpaRepository.save(quoteEntity);
        return true;

    }

    @Override
    public String getQuoteByOwnerId(Long id) {
        return quoteJpaRepository.findByOwnerId(id).get().getQuote();
    }
}
