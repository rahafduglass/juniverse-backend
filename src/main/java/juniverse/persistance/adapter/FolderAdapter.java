package juniverse.persistance.adapter;


import juniverse.domain.mappers.FolderMapper;
import juniverse.domain.models.FolderModel;
import juniverse.persistance.jpa.FolderJpaRepository;
import juniverse.persistance.repositories.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FolderAdapter implements FolderRepository {

    private final FolderJpaRepository folderJpaRepository;
    private final FolderMapper folderMapper;

    @Override
    public FolderModel findByName(String name) {
        return folderMapper.entityToModel(folderJpaRepository.findByName(name));
    }

    @Override
    public Boolean save(FolderModel folderModel) {
        folderJpaRepository.save(folderMapper.modelToEntity(folderModel));
        return true;
    }
}
