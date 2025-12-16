package it.unict.davidemilazzo.claire.model;

import com.fasterxml.jackson.databind.JsonNode;
import it.unict.davidemilazzo.claire.util.JsonNodeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "analysis_category")
public class AnalysisCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", unique = true, nullable = false)
    private AnalysisCategoryType type;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "prompt", columnDefinition = "TEXT", nullable = false)
    private String prompt;

    @Column(name = "result_json", columnDefinition = "JSON")
    @Convert(converter = JsonNodeConverter.class)
    private JsonNode jsonSchema;

    @ManyToMany(mappedBy = "categories")
    private Set<AnalysisEntity> analysisEntities;
}
