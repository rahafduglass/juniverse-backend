package juniverse.persistance.adapter;

import juniverse.domain.mappers.FileMapper;
import juniverse.domain.models.FileModel;
import juniverse.persistance.jpa.FileJpaRepository;
import juniverse.persistance.repositories.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class FileAdapter implements FileRepository {

    private final FileJpaRepository fileJpaRepository;
    private final FileMapper fileMapper;

    @Override
    public FileModel addFile(FileModel fileModel) {
        return fileMapper.entityToModel(fileJpaRepository.addFile(fileMapper.modelToEntity(fileModel)));
    }
}
