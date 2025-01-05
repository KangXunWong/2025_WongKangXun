package coinchange;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CoinChangeModel {

    private static final List<BigDecimal> VALID_DENOMINATIONS = Arrays.asList(
            new BigDecimal("0.01"), new BigDecimal("0.05"), new BigDecimal("0.1"),
            new BigDecimal("0.2"), new BigDecimal("0.5"), new BigDecimal("1"),
            new BigDecimal("2"), new BigDecimal("5"), new BigDecimal("10"),
            new BigDecimal("50"), new BigDecimal("100"), new BigDecimal("1000"));

    @JsonProperty
    @NotNull(message = "Target amount is required")
    @Min(value = 0, message = "Target amount must be non-negative")
    @Max(value = 10000, message = "Target amount must be at most 10000")
    private BigDecimal targetAmount;

    @JsonProperty
    @NotNull(message = "Coin denominations are required")
    @ValidDenominations
    private List<BigDecimal> coinDenominations;

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public List<BigDecimal> getCoinDenominations() {
        return coinDenominations;
    }

    public void setCoinDenominations(List<BigDecimal> coinDenominations) {
        this.coinDenominations = coinDenominations;
    }

    public List<BigDecimal> getFilteredDenominations() {
        if (targetAmount == null) {
            return VALID_DENOMINATIONS; // Return all if target is not set yet
        }
        return VALID_DENOMINATIONS.stream()
                .filter(d -> d.compareTo(targetAmount) <= 0)
                .collect(Collectors.toList());
    }

    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = ValidDenominationsValidator.class)
    @Documented
    public @interface ValidDenominations {
        String message() default "Invalid coin denomination. Must be one of: 0.01, 0.05, 0.1, 0.2, 0.5, 1, 2, 5, 10, 50, 100, 1000";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

    public static class ValidDenominationsValidator
            implements ConstraintValidator<ValidDenominations, List<BigDecimal>> {
        @Override
        public boolean isValid(List<BigDecimal> values, ConstraintValidatorContext context) {
            if (values == null || values.isEmpty()) {
                return true; // Let @NotNull handle empty lists
            }
            return VALID_DENOMINATIONS.containsAll(values);
        }
    }
}