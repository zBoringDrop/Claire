package it.unict.davidemilazzo.claire.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "code_snippet")
@SQLDelete(sql = "UPDATE code_snippet SET deleted = true WHERE id = ?")
public class CodeSnippetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity userEntity;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "creation_datetime", nullable = false)
    private LocalDateTime creationDatetime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "language_id", nullable = false)
    private ProgrammingLanguageEntity languageEntity;

    @Column(name = "data", nullable = false, columnDefinition = "LONGTEXT")
    @Lob
    private String codeText;

    @Column(name = "deleted")
    @ColumnDefault("false")
    private Boolean deleted = false;
}
