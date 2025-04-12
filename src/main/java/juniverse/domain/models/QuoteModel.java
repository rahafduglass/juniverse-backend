package juniverse.domain.models;


import lombok.Data;

@Data
public class QuoteModel {
    private Long id;

    private String quote;

    private Long ownerId;
}
