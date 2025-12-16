package it.unict.davidemilazzo.claire.util;

import it.unict.davidemilazzo.claire.exception.ExceptionMessages;
import it.unict.davidemilazzo.claire.exception.InvalidModelParameterSizeException;

public final class AIUtils {

    public static double extractModelSize(String parameterSize) {
        if (parameterSize == null) {
            throw new InvalidModelParameterSizeException(ExceptionMessages.INVALID_MODEL_PARAMETER_SIZE);
        }

        parameterSize = parameterSize.trim();

        if (parameterSize.length() < 2
                || parameterSize.charAt(parameterSize.length()-1) != 'B') {
            throw new InvalidModelParameterSizeException(ExceptionMessages.INVALID_MODEL_PARAMETER_SIZE);
        }

        double res;
        try {
            res = Double.parseDouble(parameterSize.substring(0, parameterSize.length()-1));
        } catch (NumberFormatException e) {
            throw new InvalidModelParameterSizeException(ExceptionMessages.INVALID_MODEL_PARAMETER_SIZE);
        }

        if (res <= 0) {
            throw new InvalidModelParameterSizeException(ExceptionMessages.INVALID_MODEL_PARAMETER_SIZE);
        }

        return res;
    }
}
