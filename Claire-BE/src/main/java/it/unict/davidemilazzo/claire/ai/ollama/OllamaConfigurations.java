package it.unict.davidemilazzo.claire.ai.ollama;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.ollama.api.OllamaOptions;

import java.util.Map;

public class OllamaConfigurations {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String DEFAULT_KEEP_ALIVE = "10m";
    private static final int GPU_ALL_LAYERS = -1;

    // ============================================================================
    // LEVEL 1: MINIMAL (0.5B-1B)
    // ============================================================================

    public static UserOllamaCustomConfigDto minimal() {
        return UserOllamaCustomConfigDto.builder()
                .temperature(0.02)
                .topK(2)
                .topP(0.05)
                .repeatPenalty(1.1)
                .seed(42)
                .numCtx(2048)
                .numPredict(256)
                .numThread(2)
                .numGpu(GPU_ALL_LAYERS)
                .useMMap(true)
                .keepAlive(DEFAULT_KEEP_ALIVE)
                .useCustomConfig(false)
                .build();
    }

    // ============================================================================
    // LEVEL 2: LIGHT (1B-3B)
    // ============================================================================

    public static UserOllamaCustomConfigDto light() {
        return UserOllamaCustomConfigDto.builder()
                .temperature(0.05)
                .topK(5)
                .topP(0.1)
                .repeatPenalty(1.05)
                .seed(42)
                .numCtx(4096)
                .numPredict(512)
                .numThread(4)
                .numGpu(GPU_ALL_LAYERS)
                .useMMap(true)
                .keepAlive(DEFAULT_KEEP_ALIVE)
                .useCustomConfig(false)
                .build();
    }

    // ============================================================================
    // LEVEL 3: STANDARD (7B-8B)
    // ============================================================================

    public static UserOllamaCustomConfigDto standard() {
        return UserOllamaCustomConfigDto.builder()
                .temperature(0.1)
                .topK(10)
                .topP(0.2)
                .repeatPenalty(1.1)
                .numCtx(8192)
                .numPredict(2048)
                .numThread(8)
                .numGpu(GPU_ALL_LAYERS)
                .mainGpu(0)
                .useMMap(true)
                .keepAlive(DEFAULT_KEEP_ALIVE)
                .useCustomConfig(false)
                .build();
    }

    // ============================================================================
    // LEVEL 4: DETAILED (13B-32B)
    // ============================================================================

    public static UserOllamaCustomConfigDto detailed() {
        return UserOllamaCustomConfigDto.builder()
                .temperature(0.08)
                .topK(8)
                .topP(0.25)
                .repeatPenalty(1.1)
                .numCtx(16384)
                .numPredict(3072)
                .numThread(12)
                .numGpu(GPU_ALL_LAYERS)
                .mainGpu(0)
                .useMMap(true)
                .keepAlive(DEFAULT_KEEP_ALIVE)
                .useCustomConfig(false)
                .build();
    }

    // ============================================================================
    // LEVEL 5: ENTERPRISE (70B+)
    // ============================================================================

    public static UserOllamaCustomConfigDto enterprise() {
        return UserOllamaCustomConfigDto.builder()
                .temperature(0.06)
                .topK(6)
                .topP(0.2)
                .repeatPenalty(1.05)
                .numCtx(32768)
                .numPredict(4096)
                .numThread(16)
                .numGpu(GPU_ALL_LAYERS)
                .mainGpu(0)
                .useMMap(true)
                .keepAlive(DEFAULT_KEEP_ALIVE)
                .useCustomConfig(false)
                .build();
    }

    // ============================================================================
    // FACTORY METHODS WITH MULTI-LEVEL SYSTEM
    // ============================================================================

    private static OllamaOptions toOptions(UserOllamaCustomConfigDto configDto, String modelName, String responseSchema) throws JsonProcessingException {
        return OllamaOptions.builder()
                .model(modelName)
                .format(objectMapper.readValue(responseSchema, Map.class))
                .temperature(configDto.getTemperature())
                .topK(configDto.getTopK())
                .topP(configDto.getTopP())
                .repeatPenalty(configDto.getRepeatPenalty())
                .numCtx(configDto.getNumCtx())
                .numPredict(configDto.getNumPredict())
                .numThread(configDto.getNumThread())
                .numGPU(configDto.getNumGpu())
                .mainGPU(configDto.getMainGpu())
                .useMMap(configDto.getUseMMap())
                .keepAlive(configDto.getKeepAlive())
                .build();
    }

    public static UserOllamaCustomConfigDto getDefaultConfigBySize(double modelSize) {

        ModelLevel level = ModelLevelValues.fromSize(modelSize);

        return switch (level) {
            case MINIMAL -> minimal();
            case LIGHT -> light();
            case STANDARD -> standard();
            case DETAILED -> detailed();
            case ENTERPRISE -> enterprise();
            default -> standard();
        };
    }

    public static OllamaOptions fromDtoConfig(String modelName, String responseSchema, UserOllamaCustomConfigDto config) throws JsonProcessingException {

        Map<String, Object> formatMap = null;
        if (responseSchema != null && !responseSchema.isBlank()) {
            formatMap = objectMapper.readValue(responseSchema, Map.class);
        }

        if (config == null) {
            config = UserOllamaCustomConfigDto.builder().build();
        }

        var builder = OllamaOptions.builder()
                .model(modelName)
                .format(formatMap)

                .temperature(config.getTemperature() != null
                        ? config.getTemperature()
                        : OllamaDefaultParameters.TEMPERATURE)

                .topK(config.getTopK() != null
                        ? config.getTopK()
                        : OllamaDefaultParameters.TOP_K)

                .topP(config.getTopP() != null
                        ? config.getTopP()
                        : OllamaDefaultParameters.TOP_P)

                .repeatPenalty(config.getRepeatPenalty() != null
                        ? config.getRepeatPenalty()
                        : OllamaDefaultParameters.REPEAT_PENALTY)

                .numCtx(config.getNumCtx() != null
                        ? config.getNumCtx()
                        : OllamaDefaultParameters.NUM_CTX)

                .numPredict(config.getNumPredict() != null
                        ? config.getNumPredict()
                        : OllamaDefaultParameters.NUM_PREDICT)

                .seed(config.getSeed() != null
                        ? config.getSeed()
                        : OllamaDefaultParameters.SEED)

                .numGPU(config.getNumGpu() != null
                        ? config.getNumGpu()
                        : OllamaDefaultParameters.NUM_GPU)

                .mainGPU(config.getMainGpu() != null
                        ? config.getMainGpu()
                        : OllamaDefaultParameters.MAIN_GPU)

                .numThread(config.getNumThread() != null
                        ? config.getNumThread()
                        : OllamaDefaultParameters.NUM_THREAD)

                .useMMap(config.getUseMMap() != null
                        ? config.getUseMMap()
                        : OllamaDefaultParameters.USE_MMAP);

        builder.keepAlive(config.getKeepAlive() != null
                ? config.getKeepAlive()
                : OllamaDefaultParameters.KEEP_ALIVE);

        return builder.build();
    }
}