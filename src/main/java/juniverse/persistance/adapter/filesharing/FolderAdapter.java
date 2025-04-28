package juniverse.persistance.adapter.filesharing;


import juniverse.domain.mappers.filesharing.FolderMapper;
import juniverse.domain.models.filesharing.FolderModel;
import juniverse.persistance.jpa.filesharing.FolderJpaRepository;
import juniverse.persistance.repositories.filesharing.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public FolderModel save(FolderModel folderModel) {

        return folderMapper.entityToModel(folderJpaRepository.save(folderMapper.modelToEntity(folderModel)));
    }

    @Override
    public FolderModel findById(Long id) {
        return folderMapper.entityToModel(folderJpaRepository.findById(id).get());
    }

    @Override
    public void updatePath(Long id, String path) {
        folderJpaRepository.updatePathById(id, path);
    }

    @Override
    public List<FolderModel> getFolders() {
        return folderMapper.listOfEntitiesToListOfModels(folderJpaRepository.findAll());
    }

    @Override
    public void remove(Long folderId) {
        folderJpaRepository.removeById(folderId);
    }
}
