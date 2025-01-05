package coinchange;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CoinChangeRouterTest {

        private static CoinChangeConfiguration config;

        @BeforeAll
        public static void setUp() throws IOException {
                config = CoinChangeApplication.loadConfiguration();
        }

        @Test
        public void testCalculateCoinChange() {
                CoinChangeRouter router = new CoinChangeRouter(config);

                CoinChangeModel request1 = new CoinChangeModel();
                request1.setTargetAmount(new BigDecimal("7.03"));
                request1.setCoinDenominations(
                                List.of(new BigDecimal("0.01"), new BigDecimal("0.5"), new BigDecimal("1"),
                                                new BigDecimal("5"), new BigDecimal("10")));
                List<BigDecimal> expected1 = List.of(new BigDecimal("0.01"), new BigDecimal("0.01"),
                                new BigDecimal("0.01"),
                                new BigDecimal("1"), new BigDecimal("1"), new BigDecimal("5"));
                assertEquals(expected1,
                                router.calculateMinimumCoins(request1.getTargetAmount(),
                                                request1.getCoinDenominations()));

                CoinChangeModel request2 = new CoinChangeModel();
                request2.setTargetAmount(new BigDecimal("103"));
                request2.setCoinDenominations(List.of(new BigDecimal("1"), new BigDecimal("2"), new BigDecimal("50")));
                List<BigDecimal> expected2 = List.of(new BigDecimal("1"), new BigDecimal("2"), new BigDecimal("50"),
                                new BigDecimal("50"));
                assertEquals(expected2,
                                router.calculateMinimumCoins(request2.getTargetAmount(),
                                                request2.getCoinDenominations()));
        }
}