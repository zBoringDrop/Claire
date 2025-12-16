package it.unict.davidemilazzo.claire.respository;

import it.unict.davidemilazzo.claire.dto.FilePreviewDto;
import it.unict.davidemilazzo.claire.model.FileEntity;
import it.unict.davidemilazzo.claire.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FileEntityRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByUserEntity(UserEntity userEntity);
    List<FileEntity> findByUserEntity_Id(Long userId);
    List<FileEntity> findByFilename(String filename);
    List<FileEntity> findByLanguageEntity_Name(String name);
    List<FileEntity> findByUploadDatetime(LocalDateTime localDateTime);

    @Query("""
        SELECT new it.unict.davidemilazzo.claire.dto.FilePreviewDto(
            f.id,
            f.userEntity.id,
            f.filename,
            f.contentType,
            f.uploadDatetime,
            lang.name,
            f.size,
            SUBSTRING(CAST(f.data AS string), 1, 150),
            COUNT(a.id),
            f.deleted
        )
        FROM FileEntity f
        LEFT JOIN f.languageEntity lang
        LEFT JOIN AnalysisEntity a ON a.fileEntity.id = f.id
        WHERE f.userEntity.id = :userId
          AND f.deleted = false
        GROUP BY 
            f.id, f.userEntity.id, f.filename, f.contentType, f.uploadDatetime,
            lang.name, f.size, f.data, f.deleted
        ORDER BY f.uploadDatetime DESC
        """)
    List<FilePreviewDto> listAllUserFilePreviews(@Param("userId") Long userId);

    @Query("""
    SELECT new it.unict.davidemilazzo.claire.dto.FilePreviewDto(
        f.id,
        f.userEntity.id,
        f.filename,
        f.contentType,
        f.uploadDatetime,
        lang.name,
        f.size,
        f.data,
        COUNT(a.id),
        f.deleted
    )
    FROM FileEntity f
    LEFT JOIN f.languageEntity lang
    LEFT JOIN AnalysisEntity a ON a.fileEntity.id = f.id
    WHERE f.id = :fileId
    GROUP BY 
        f.id, f.userEntity.id, f.filename, f.contentType, f.uploadDatetime,
        lang.name, f.size, f.data, f.deleted
    ORDER BY f.uploadDatetime DESC
    """)
    FilePreviewDto findPreviewById(@Param("fileId") Long fileId);


}
