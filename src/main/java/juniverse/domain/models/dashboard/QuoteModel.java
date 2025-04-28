package juniverse.domain.models.dashboard;


import lombok.Data;

@Data
public class QuoteModel {
    private Long id;

    private String quote;

    private Long ownerId;
}
