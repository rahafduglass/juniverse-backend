package juniverse.domain.services;

import juniverse.domain.enums.FolderStatus;
import juniverse.domain.models.FolderModel;
import juniverse.domain.provider.IdentityProvider;
import juniverse.persistance.repositories.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;
    private final IdentityProvider identityProvider;

    public Boolean addFolder(FolderModel folderModel) {

        if (folderRepository.findByName(folderModel.getName()) != null)
            throw new RuntimeException("Folder already exists");
        else if (folderModel.getName().isEmpty() || folderModel.getDescription().isEmpty())
            throw new RuntimeException("data is empty");

        folderModel.setCreatedBy(identityProvider.currentIdentity().getId());
        folderModel.setStatus(FolderStatus.UPLOADED);
        folderModel.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
        Long generatedFolderId = folderRepository.save(folderModel).getId();
        String path = ("src/main/resources/juniverse_files/folders/" + generatedFolderId);
        folderRepository.updatePath(generatedFolderId, path);

        File file = new File(path);
        if (file.mkdir()) throw new RuntimeException("Failed to create folder in local storage");

        return true;
    }

    public boolean updateFolder(FolderModel folderModel) {
        FolderModel currentFolder = folderRepository.findById(folderModel.getId());

        if (currentFolder == null)
            throw new RuntimeException("Folder doesnt exists");
        else if (folderModel.getName() == null || folderModel.getName().isEmpty()
                || folderModel.getDescription() == null || folderModel.getDescription().isEmpty())
            throw new RuntimeException("data is empty");

        currentFolder.setName(folderModel.getName());
        currentFolder.setDescription(folderModel.getDescription());
        currentFolder.setModifiedOn(Timestamp.valueOf(LocalDateTime.now()));
        currentFolder.setModifiedBy(identityProvider.currentIdentity().getId());

        folderRepository.save(currentFolder);
        return true;
    }

    public List<FolderModel> getFolders() {
        return folderRepository.getFolders();
    }

    public boolean updateFolderName(Long folderId, String folderName) {
        FolderModel currentFolder = folderRepository.findById(folderId);

        if (currentFolder == null)
            throw new RuntimeException("Folder doesnt exists");
        else if (folderName == null || folderName.isEmpty())
            throw new RuntimeException("data is empty");

        currentFolder.setName(folderName);
        currentFolder.setModifiedOn(Timestamp.valueOf(LocalDateTime.now()));
        currentFolder.setModifiedBy(identityProvider.currentIdentity().getId());

        folderRepository.save(currentFolder);
        return true;
    }

    public boolean updateFolderDescription(Long folderId, String folderDescription) {
        FolderModel currentFolder = folderRepository.findById(folderId);

        if (currentFolder == null)
            throw new RuntimeException("Folder doesnt exist");
        else if (folderDescription == null || folderDescription.isEmpty())
            throw new RuntimeException("data is empty");

        currentFolder.setDescription(folderDescription);
        currentFolder.setModifiedOn(Timestamp.valueOf(LocalDateTime.now()));
        currentFolder.setModifiedBy(identityProvider.currentIdentity().getId());

        folderRepository.save(currentFolder);
        return true;
    }

    public boolean deleteFolder(Long folderId) {
        FolderModel currentFolder = folderRepository.findById(folderId);

        if (currentFolder == null)
            throw new RuntimeException("folder doesnt exist");

        folderRepository.remove(folderId);
        return true;
    }
}
