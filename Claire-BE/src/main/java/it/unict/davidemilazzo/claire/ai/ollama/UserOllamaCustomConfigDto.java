package it.unict.davidemilazzo.claire.ai.ollama;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
public class UserOllamaCustomConfigDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("use_custom_config")
    private Boolean useCustomConfig;

    @JsonProperty("temperature")
    private Double temperature;

    @JsonProperty("top_k")
    private Integer topK;

    @JsonProperty("top_p")
    private Double topP;

    @JsonProperty("repeat_penalty")
    private Double repeatPenalty;

    @JsonProperty("num_ctx")
    private Integer numCtx;

    @JsonProperty("num_predict")
    private Integer numPredict;

    @JsonProperty("num_thread")
    private Integer numThread;

    @JsonProperty("keep_alive")
    private String keepAlive;

    @JsonProperty("num_gpu")
    private Integer numGpu = -1;

    @JsonProperty("main_gpu")
    private Integer mainGpu;

    @JsonProperty("use_mmap")
    private Boolean useMMap;

    @JsonProperty("seed")
    private Integer seed;
}
