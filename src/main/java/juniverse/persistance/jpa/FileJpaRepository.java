package juniverse.persistance.jpa;

import io.micrometer.common.KeyValues;
import jakarta.transaction.Transactional;
import juniverse.domain.enums.FileStatus;
import juniverse.domain.models.FileModel;
import juniverse.persistance.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FileJpaRepository extends JpaRepository<FileEntity, Long> {


    @Transactional
    @Modifying
    @Query("UPDATE file f SET f.path= :filePath WHERE f.id= :id")
    int updatePath(Long id, String filePath);


    @Query("SELECT f FROM file f WHERE f.folder.id = :folderId AND f.status = :status")
    List<FileEntity> findAllByStatus(Long folderId, FileStatus status);

    @Query("SELECT f FROM file f WHERE f.status = :status")
    List<FileEntity> findAllByStatus(FileStatus status);


    @Transactional
    @Modifying
    @Query("UPDATE file f SET f.status= :status,f.monitoredAt=:monitoredAt,f.monitoredBy.id=:monitoredBy WHERE f.id= :fileId")
    int updateFileStatus(Long fileId, FileStatus status, LocalDateTime monitoredAt, Long monitoredBy);

    @Transactional
    @Modifying
    @Query("UPDATE file f SET f.name= :name WHERE f.id= :fileId")
    int updateFileName(Long fileId, String name);

    @Transactional
    @Modifying
    @Query("UPDATE file f SET f.description= :description WHERE f.id= :fileId")
    int updateFileDescription(Long fileId, String description);

    @Query("SELECT f FROM file f WHERE f.status =0 AND f.owner.id=:userId")
    List<FileEntity> getUserPendingFiles(Long userId);

    @Query("SELECT f FROM file f WHERE f.status =2 AND f.owner.id=:userId")
    List<FileEntity> getUserAcceptedFiles(Long userId);
}
