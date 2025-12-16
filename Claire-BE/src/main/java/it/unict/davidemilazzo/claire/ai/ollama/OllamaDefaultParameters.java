package it.unict.davidemilazzo.claire.ai.ollama;

public final class OllamaDefaultParameters {

    private OllamaDefaultParameters() {}

    public static final double TEMPERATURE = 0.1;
    public static final int TOP_K = 20;
    public static final double TOP_P = 0.2;
    public static final double REPEAT_PENALTY = 1.1;
    public static final int NUM_CTX = 4096;
    public static final int NUM_PREDICT = -1;
    public static final int NUM_THREAD = 4;
    public static final String KEEP_ALIVE = "5m";
    public static final int NUM_GPU = -1;
    public static final int MAIN_GPU = 0;
    public static final boolean USE_MMAP = true;
    public static final int SEED = 42;
}