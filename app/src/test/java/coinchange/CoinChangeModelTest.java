package coinchange;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CoinChangeModelTest {

    private CoinChangeModel.ValidDenominationsValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new CoinChangeModel.ValidDenominationsValidator();
    }

    @Test
    public void testValidDenominations() {
        List<BigDecimal> validDenominations = List.of(
                new BigDecimal("0.01"), new BigDecimal("0.05"), new BigDecimal("0.1"),
                new BigDecimal("0.2"), new BigDecimal("0.5"), new BigDecimal("1"),
                new BigDecimal("2"), new BigDecimal("5"), new BigDecimal("10"),
                new BigDecimal("50"), new BigDecimal("100"), new BigDecimal("1000"));
        assertTrue(validator.isValid(validDenominations, null));
    }

    @Test
    public void testInvalidDenominations() {
        List<BigDecimal> invalidDenominations = List.of(
                new BigDecimal("0.03"), new BigDecimal("0.07"), new BigDecimal("0.15"));
        assertFalse(validator.isValid(invalidDenominations, null));
    }

    @Test
    public void testEmptyDenominations() {
        List<BigDecimal> emptyDenominations = List.of();
        assertTrue(validator.isValid(emptyDenominations, null));
    }

    @Test
    public void testNullDenominations() {
        assertTrue(validator.isValid(null, null));
    }
}