package coinchange;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

@Path("/coin-change")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CoinChangeRouter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoinChangeRouter.class);

    private static final List<BigDecimal> VALID_DENOMINATIONS = List.of(
            new BigDecimal("0.01"), new BigDecimal("0.05"), new BigDecimal("0.1"),
            new BigDecimal("0.2"), new BigDecimal("0.5"), new BigDecimal("1"),
            new BigDecimal("2"), new BigDecimal("5"), new BigDecimal("10"),
            new BigDecimal("50"), new BigDecimal("100"), new BigDecimal("1000"));
    private final CoinChangeConfiguration config;

    public CoinChangeRouter(CoinChangeConfiguration config) {
        this.config = config;
    }

    @POST
    public Response calculateCoinChange(@NotNull CoinChangeModel request) {
        LOGGER.info("Received request to calculate coin change for target amount: {}", request.getTargetAmount());

        try {
            if (request.getTargetAmount().compareTo(BigDecimal.ZERO) < 0
                    || request.getTargetAmount().compareTo(new BigDecimal("10000")) > 0) {
                LOGGER.warn("Invalid target amount: {}", request.getTargetAmount());
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Target amount must be between 0 and 10000.").build();
            }

            if (!VALID_DENOMINATIONS.containsAll(request.getCoinDenominations())) {
                LOGGER.warn("Invalid coin denominations provided: {}", request.getCoinDenominations());
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid coin denominations provided.")
                        .build();
            }

            List<BigDecimal> coinChange = calculateMinimumCoins(request.getTargetAmount(),
                    request.getCoinDenominations());
            LOGGER.info("Calculated coin change: {}", coinChange);
            return Response.ok(coinChange).build();
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error calculating coin change: {}", e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    public List<BigDecimal> calculateMinimumCoins(BigDecimal targetAmount, List<BigDecimal> denominations) {
        LOGGER.debug("Calculating minimum coins for target amount: {} with denominations: {}", targetAmount,
                denominations);

        List<BigDecimal> sortedDenominations = denominations.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        List<BigDecimal> result = new ArrayList<>();
        BigDecimal remainingAmount = targetAmount.setScale(2, RoundingMode.HALF_UP);

        for (BigDecimal denomination : sortedDenominations) {
            while (remainingAmount.compareTo(denomination) >= 0) {
                result.add(denomination);
                remainingAmount = remainingAmount.subtract(denomination).setScale(2, RoundingMode.HALF_UP);
            }
        }

        Collections.sort(result);
        LOGGER.debug("Resulting coin change: {}", result);
        return result;
    }
}