package it.unict.davidemilazzo.claire.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Table(name = "static_tool")
public class StaticToolEntity extends ToolEntity {

    @Column(name = "version", nullable = false)
    private String version;

    @OneToMany
    @JoinTable(
            name = "static_tool_supported_languages",
            joinColumns = @JoinColumn(name = "tool_id"),
            inverseJoinColumns = @JoinColumn(name = "programming_language_id")
    )
    private Set<ProgrammingLanguageEntity> supportedLanguages;

    @Column(name = "executable_path", nullable = false)
    private String executablePath;
}
