package it.unict.davidemilazzo.claire.model;

import it.unict.davidemilazzo.claire.ai.ollama.OllamaDefaultParameters;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_ollama_custom_config_entity")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOllamaCustomConfigEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @Column(name = "use_custom_config", columnDefinition = "boolean default false")
    @Builder.Default
    private Boolean useCustomConfig = false;

    @Column(name = "temperature", columnDefinition = "double default 0.1")
    @Builder.Default
    private Double temperature = OllamaDefaultParameters.TEMPERATURE;

    @Column(name = "top_k", columnDefinition = "integer default 20")
    @Builder.Default
    private Integer topK = OllamaDefaultParameters.TOP_K;

    @Column(name = "top_p", columnDefinition = "double default 0.2")
    @Builder.Default
    private Double topP = OllamaDefaultParameters.TOP_P;

    @Column(name = "repeat_penalty", columnDefinition = "double default 1.1")
    @Builder.Default
    private Double repeatPenalty = OllamaDefaultParameters.REPEAT_PENALTY;

    @Column(name = "num_ctx", columnDefinition = "integer default 4096")
    @Builder.Default
    private Integer numCtx = OllamaDefaultParameters.NUM_CTX;

    @Column(name = "num_predict", columnDefinition = "integer default -1")
    @Builder.Default
    private Integer numPredict = OllamaDefaultParameters.NUM_PREDICT;

    @Column(name = "num_thread", columnDefinition = "integer default 4")
    @Builder.Default
    private Integer numThread = OllamaDefaultParameters.NUM_THREAD;

    @Column(name = "keep_alive", columnDefinition = "varchar(255) default '5m'")
    @Builder.Default
    private String keepAlive = OllamaDefaultParameters.KEEP_ALIVE;

    @Column(name = "num_gpu", columnDefinition = "integer default -1")
    @Builder.Default
    private Integer numGpu = OllamaDefaultParameters.NUM_GPU;

    @Column(name = "main_gpu", columnDefinition = "integer default 0")
    @Builder.Default
    private Integer mainGpu = OllamaDefaultParameters.MAIN_GPU;

    @Column(name = "use_mmap", columnDefinition = "boolean default true")
    @Builder.Default
    private Boolean useMMap = OllamaDefaultParameters.USE_MMAP;

    @Column(name = "seed", columnDefinition = "integer default 42")
    @Builder.Default
    private Integer seed = OllamaDefaultParameters.SEED;
}