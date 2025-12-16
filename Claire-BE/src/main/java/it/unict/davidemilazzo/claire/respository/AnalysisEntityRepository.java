package it.unict.davidemilazzo.claire.respository;

import it.unict.davidemilazzo.claire.dto.AnalysisPreviewDto;
import it.unict.davidemilazzo.claire.model.AnalysisEntity;
import it.unict.davidemilazzo.claire.model.AnalysisStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnalysisEntityRepository extends JpaRepository<AnalysisEntity, Long> {
    List<AnalysisEntity> findByFileEntity_id(Long id);
    List<AnalysisEntity> findByCodeSnippetEntity_id(Long id);
    List<AnalysisEntity> findByToolEntity_id(Long id);
    List<AnalysisEntity> findByStatus(AnalysisStatus status);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE AnalysisEntity a SET a.status = :status WHERE a.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") AnalysisStatus status);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE AnalysisEntity a SET a.message = :message WHERE a.id = :id")
    void updateStatusMessage(@Param("id") Long id, @Param("message") String message);

    @Query("""
        SELECT new it.unict.davidemilazzo.claire.dto.AnalysisPreviewDto(
            a.id,
            a.status,
            a.message,
            f.id,
            s.id,
            COALESCE(f.filename, s.title),
            COALESCE(
                SUBSTRING(CAST(f.data AS string), 1, 150),
                SUBSTRING(CAST(s.codeText AS string), 1, 150)
            ),
            COALESCE(f.deleted, s.deleted, false),
            t.name,
            a.overallSeverity,
            a.issuesCount,
            a.createdAt
        )
        FROM AnalysisEntity a
        LEFT JOIN a.fileEntity f
        LEFT JOIN a.codeSnippetEntity s
        LEFT JOIN a.toolEntity t
        WHERE a.status IN :states
          AND (f.userEntity.id = :userId OR s.userEntity.id = :userId)
        ORDER BY a.createdAt DESC
        """)
    List<AnalysisPreviewDto> findAllPreviewsByUserAndStates(
            @Param("userId") Long userId,
            @Param("states") List<AnalysisStatus> states);

    @Query("""
        SELECT new it.unict.davidemilazzo.claire.dto.AnalysisPreviewDto(
            a.id,
            a.status,
            a.message,
            f.id,
            s.id,
            COALESCE(f.filename, s.title),
            COALESCE(
                SUBSTRING(CAST(f.data AS string), 1, 150),
                SUBSTRING(CAST(s.codeText AS string), 1, 150)
            ),
            COALESCE(f.deleted, s.deleted, false),
            t.name,
            a.overallSeverity,
            a.issuesCount,
            a.createdAt
        )
        FROM AnalysisEntity a
        LEFT JOIN a.fileEntity f
        LEFT JOIN a.codeSnippetEntity s
        LEFT JOIN a.toolEntity t
        WHERE (f.userEntity.id = :userId OR s.userEntity.id = :userId)
            AND f.id = :fileId
        """)
    List<AnalysisPreviewDto> findAllPreviewsByUserAndFileId(
            @Param("userId") Long userId,
            @Param("fileId") Long fileId);

    @Query("""
        SELECT new it.unict.davidemilazzo.claire.dto.AnalysisPreviewDto(
            a.id,
            a.status,
            a.message,
            f.id,
            s.id,
            COALESCE(f.filename, s.title),
            COALESCE(
                SUBSTRING(CAST(f.data AS string), 1, 150),
                SUBSTRING(CAST(s.codeText AS string), 1, 150)
            ),
            COALESCE(f.deleted, s.deleted, false),
            t.name,
            a.overallSeverity,
            a.issuesCount,
            a.createdAt
        )
        FROM AnalysisEntity a
        LEFT JOIN a.fileEntity f
        LEFT JOIN a.codeSnippetEntity s
        LEFT JOIN a.toolEntity t
        WHERE (f.userEntity.id = :userId OR s.userEntity.id = :userId)
            AND s.id = :snippetId 
        """)
    List<AnalysisPreviewDto> findAllPreviewsByUserAndSnippetId(
            @Param("userId") Long userId,
            @Param("snippetId") Long snippetId);

    @Query("""
        SELECT new it.unict.davidemilazzo.claire.dto.AnalysisPreviewDto(
            a.id,
            a.status,
            a.message,
            f.id,
            s.id,
            COALESCE(f.filename, s.title),
            COALESCE(
                SUBSTRING(CAST(f.data AS string), 1, 150),
                SUBSTRING(CAST(s.codeText AS string), 1, 150)
            ),
            COALESCE(f.deleted, s.deleted, false),
            t.name,
            a.overallSeverity,
            a.issuesCount,
            a.createdAt
        )
        FROM AnalysisEntity a
        LEFT JOIN a.fileEntity f
        LEFT JOIN a.codeSnippetEntity s
        LEFT JOIN a.toolEntity t
        WHERE (f.userEntity.id = :userId OR s.userEntity.id = :userId)
        GROUP BY a.id, a.status, a.message, f.id, f.filename, s.id, s.title,
                 f.data, s.codeText, t.name, a.overallSeverity, a.issuesCount, a.createdAt
        """)
    List<AnalysisPreviewDto> findAllPreviewsByUser(@Param("userId") Long userId);

    @Query("""
        SELECT new it.unict.davidemilazzo.claire.dto.AnalysisPreviewDto(
            a.id,
            a.status,
            a.message,
            f.id,
            s.id,
            COALESCE(f.filename, s.title),
            COALESCE(
                SUBSTRING(CAST(f.data AS string), 1, 150),
                SUBSTRING(CAST(s.codeText AS string), 1, 150)
            ),
            COALESCE(f.deleted, s.deleted, false),
            t.name,
            a.overallSeverity,
            a.issuesCount,
            a.createdAt
        )
        FROM AnalysisEntity a
        LEFT JOIN a.fileEntity f
        LEFT JOIN a.codeSnippetEntity s
        LEFT JOIN a.toolEntity t
        WHERE a.status = :status
          AND (f.userEntity.id = :userId OR s.userEntity.id = :userId)
        GROUP BY a.id, a.status, a.message, f.id, f.filename, s.id, s.title,
                 f.data, s.codeText, t.name, a.overallSeverity, a.issuesCount, a.createdAt
        """)
    List<AnalysisPreviewDto> findAllPreviewsByUserAndStatus(
            @Param("userId") Long userId,
            @Param("status") AnalysisStatus status);

}
