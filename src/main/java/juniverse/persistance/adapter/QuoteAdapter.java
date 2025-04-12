package juniverse.persistance.adapter;

import juniverse.domain.provider.IdentityProvider;
import juniverse.persistance.entities.QuoteEntity;
import juniverse.persistance.entities.SysUserEntity;
import juniverse.persistance.jpa.QuoteJpaRepository;
import juniverse.persistance.repositories.QuoteRepository;
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
}
