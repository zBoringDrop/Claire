package it.unict.davidemilazzo.claire.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Table(name = "ai")
public class AIEntity extends ToolEntity {

    @Column(name = "model", nullable = false, unique = true)
    private String model;

    @Column(name = "family")
    private String family;

    @Column(name = "parameter_size")
    private String parameterSize;

    @Column(name = "size")
    private Long size;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ai_provider_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AIProviderEntity aiProviderEntity;

    @Lob
    @Column(name = "description", columnDefinition = "LONGTEXT", nullable = true)
    private String description;

    @Column(name = "is_active", nullable = false)
    @ColumnDefault("true")
    private boolean active;

    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    @Column(name = "last_db_sync", nullable = false)
    private LocalDateTime lastDBSync;

    @Column(name = "json_extra", columnDefinition = "TEXT")
    private String jsonExtra;
}
