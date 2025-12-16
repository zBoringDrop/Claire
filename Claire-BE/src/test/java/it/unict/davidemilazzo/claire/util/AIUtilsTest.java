package it.unict.davidemilazzo.claire.util;

import it.unict.davidemilazzo.claire.exception.InvalidModelParameterSizeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AIUtilsTest {

    private static final Logger log = LogManager.getLogger(AIUtilsTest.class);

    @Test
    @DisplayName("TEST extraction parameterSize from a String (es. '7.2B' -> 7.2)")
    void modelParameterSizeExtract_success() {
        Assertions.assertEquals(7.0d, AIUtils.extractModelSize("7B"));
        Assertions.assertEquals(13.0d, AIUtils.extractModelSize("13B"));
        Assertions.assertEquals(175.0d, AIUtils.extractModelSize("175B"));
        Assertions.assertEquals(1.0d, AIUtils.extractModelSize("1B"));
        Assertions.assertEquals(3.5d, AIUtils.extractModelSize("3.5B"));
        Assertions.assertEquals(0.5d, AIUtils.extractModelSize("0.5B"));
        Assertions.assertEquals(1000.0d, AIUtils.extractModelSize("1000B"));
        Assertions.assertEquals(0.1d, AIUtils.extractModelSize("0.1B"));
        Assertions.assertEquals(2.0d, AIUtils.extractModelSize("2B"));
        Assertions.assertEquals(50.0d, AIUtils.extractModelSize("50B"));
        Assertions.assertEquals(0.75d, AIUtils.extractModelSize("0.75B"));
        Assertions.assertEquals(7.0d, AIUtils.extractModelSize("7B "));
        Assertions.assertEquals(7.0d, AIUtils.extractModelSize(" 7B"));
        Assertions.assertThrows(InvalidModelParameterSizeException.class, () -> AIUtils.extractModelSize(null));
        Assertions.assertThrows(InvalidModelParameterSizeException.class, () -> AIUtils.extractModelSize("0B"));
        Assertions.assertThrows(InvalidModelParameterSizeException.class, () -> AIUtils.extractModelSize(""));
        Assertions.assertThrows(InvalidModelParameterSizeException.class, () -> AIUtils.extractModelSize("abcB"));
        Assertions.assertThrows(InvalidModelParameterSizeException.class, () -> AIUtils.extractModelSize("7.5.3B"));
        Assertions.assertThrows(InvalidModelParameterSizeException.class, () -> AIUtils.extractModelSize("7B7"));
        Assertions.assertThrows(InvalidModelParameterSizeException.class, () -> AIUtils.extractModelSize("B7"));
        Assertions.assertThrows(InvalidModelParameterSizeException.class, () -> AIUtils.extractModelSize("7B7B"));
        Assertions.assertThrows(InvalidModelParameterSizeException.class, () -> AIUtils.extractModelSize("7B!"));
    }
}
