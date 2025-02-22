package juniverse.domain.services;

import juniverse.domain.enums.FolderStatus;
import juniverse.domain.models.FolderModel;
import juniverse.domain.provider.IdentityProvider;
import juniverse.persistance.repositories.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;
    private final IdentityProvider identityProvider;

    public Boolean addFolder(FolderModel folderModel) {

        if (folderRepository.findByName(folderModel.getName()) != null)
            throw new RuntimeException("Folder already exists");
        else if (folderModel.getName().isEmpty())
            throw new RuntimeException("Folder name is empty");

        folderModel.setCreatedBy(identityProvider.currentIdentity());
        folderModel.setStatus(FolderStatus.UPLOADED);
        folderModel.setPath("src/main/resources/juniverse_files/folders/" + folderModel.getName());

        folderRepository.save(folderModel);
        File file = new File(folderModel.getPath());
        if (!file.mkdir()) throw new RuntimeException("Failed to create folder in local storage");

        return true;
    }
}
