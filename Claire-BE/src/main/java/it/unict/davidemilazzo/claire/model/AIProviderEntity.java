package it.unict.davidemilazzo.claire.model;

import it.unict.davidemilazzo.claire.ai.AIProvidersEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "ai_provider")
public class AIProviderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    @Enumerated(value = EnumType.STRING)
    private AIProvidersEnum name;

    @Column(name = "base_url", nullable = false)
    private String baseUrl;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AIType aiType;

    @Lob
    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;
}
