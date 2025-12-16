package it.unict.davidemilazzo.claire.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_provider_apikey")
public class UserProviderApiKeyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ai_provider_id", nullable = false)
    private AIProviderEntity provider;

    @Column(name = "api_key", nullable = false)
    private String apiKey;

    @Column(name = "active", nullable = false)
    private Boolean active;
}
