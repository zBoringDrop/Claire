package it.unict.davidemilazzo.claire.respository;

import it.unict.davidemilazzo.claire.dto.CodeSnippetPreviewDto;
import it.unict.davidemilazzo.claire.dto.FilePreviewDto;
import it.unict.davidemilazzo.claire.model.CodeSnippetEntity;
import it.unict.davidemilazzo.claire.model.ProgrammingLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CodeSnippetEntityRepository extends JpaRepository<CodeSnippetEntity, Long> {
    List<CodeSnippetEntity> findByLanguageEntity_Name(String name);
    List<CodeSnippetEntity> findByUserEntity_id(Long id);

    @Query("""
        SELECT new it.unict.davidemilazzo.claire.dto.CodeSnippetPreviewDto(
            s.id,
            s.userEntity.id,
            s.title,
            s.creationDatetime,
            lang.name,
            SUBSTRING(CAST(s.codeText AS string), 1, 150),
            COUNT(a.id),
            s.deleted
        )
        FROM CodeSnippetEntity s
        LEFT JOIN s.languageEntity lang
        LEFT JOIN AnalysisEntity a ON a.codeSnippetEntity.id = s.id
        WHERE s.userEntity.id = :userId
          AND s.deleted = false
        GROUP BY 
            s.id, s.userEntity.id, s.title, s.creationDatetime,
            lang.name, s.codeText, s.deleted
        ORDER BY s.creationDatetime DESC
        """)
    List<CodeSnippetPreviewDto> listAllUserPreviews(@Param("userId") Long userId);

    @Query("""
    SELECT new it.unict.davidemilazzo.claire.dto.CodeSnippetPreviewDto(
        s.id,
        s.userEntity.id,
        s.title,
        s.creationDatetime,
        lang.name,
        s.codeText,
        COUNT(a.id),
        s.deleted
    )
    FROM CodeSnippetEntity s
    LEFT JOIN s.languageEntity lang
    LEFT JOIN AnalysisEntity a ON a.codeSnippetEntity.id = s.id
    WHERE s.id = :snippetId
    GROUP BY 
        s.id, s.userEntity.id, s.title, s.creationDatetime,
        lang.name, s.codeText, s.deleted
    ORDER BY s.creationDatetime DESC
    """)
    CodeSnippetPreviewDto findPreviewById(@Param("snippetId") Long snippetId);


}
