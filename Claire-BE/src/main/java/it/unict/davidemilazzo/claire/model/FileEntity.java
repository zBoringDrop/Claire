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
@Table(name = "file")
@SQLDelete(sql = "UPDATE file SET deleted = true WHERE id = ?")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity userEntity;

    @Column(name = "filename", nullable = false)
    private String filename;

    @Column(name = "upload_datetime", nullable = false)
    private LocalDateTime uploadDatetime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "language_id", nullable = false)
    private ProgrammingLanguageEntity languageEntity;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "size", nullable = false)
    private long size;

    @Column(name = "data", nullable = false, columnDefinition = "LONGTEXT")
    @Lob
    private String data;

    @Column(name = "deleted")
    @ColumnDefault("false")
    private Boolean deleted = false;
}
