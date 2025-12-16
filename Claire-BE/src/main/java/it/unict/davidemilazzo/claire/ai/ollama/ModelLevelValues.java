package it.unict.davidemilazzo.claire.ai.ollama;

public final class ModelLevelValues {
    public static final double MINIMAL_MIN_SIZE = 0.0;
    public static final double MINIMAL_MAX_SIZE = 1.0;

    public static final double LIGHT_MIN_SIZE = 1.0;
    public static final double LIGHT_MAX_SIZE = 5.0;

    public static final double STANDARD_MIN_SIZE = 5.0;
    public static final double STANDARD_MAX_SIZE = 10.0;

    public static final double DETAILED_MIN_SIZE = 10.0;
    public static final double DETAILED_MAX_SIZE = 50.0;

    public static final double ENTERPRISE_MIN_SIZE = 50.0;

    public static ModelLevel fromSize(double parameterSize) {
        if (parameterSize < MINIMAL_MAX_SIZE) {
            return ModelLevel.MINIMAL;
        } else if (parameterSize < LIGHT_MAX_SIZE) {
            return ModelLevel.LIGHT;
        } else if (parameterSize < STANDARD_MAX_SIZE) {
            return ModelLevel.STANDARD;
        } else if (parameterSize < DETAILED_MAX_SIZE) {
            return ModelLevel.DETAILED;
        } else {
            return ModelLevel.ENTERPRISE;
        }
    }

}