package it.unict.davidemilazzo.claire.model;

import com.fasterxml.jackson.databind.JsonNode;
import it.unict.davidemilazzo.claire.util.JsonNodeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "analysis")
public class AnalysisEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AnalysisStatus status;

    @Column(name = "message", nullable = true, columnDefinition = "LONGTEXT")
    @Lob
    private String message;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private FileEntity fileEntity;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "codesnippet_id")
    private CodeSnippetEntity codeSnippetEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tool_id")
    private ToolEntity toolEntity;

    @ManyToMany
    @JoinTable(
            name = "analysis_category_join",
            joinColumns = @JoinColumn(name = "analysis_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<AnalysisCategoryEntity> categories;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "end_datetime")
    private LocalDateTime endDatetime;

    @Column(name = "execution_ms")
    private Long executionMs;

    @Column(name = "overall_severity")
    private Byte overallSeverity;

    @Column(name = "output_length")
    private Integer outputLength;

    @Column(name = "issues_count")
    private Integer issuesCount;

    @Column(name = "result_json", columnDefinition = "JSON")
    @Convert(converter = JsonNodeConverter.class)
    private JsonNode resultJson;
}
