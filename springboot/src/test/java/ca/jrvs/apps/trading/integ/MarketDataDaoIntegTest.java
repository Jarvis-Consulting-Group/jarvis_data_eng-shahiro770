package ca.jrvs.apps.trading.integ;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Before;
import org.junit.Test;

public class MarketDataDaoIntegTest {

    private MarketDataDao marketDataDao;

    @Before
    public void init() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);

        MarketDataConfig marketDataConfig = new MarketDataConfig();
        marketDataConfig.setHost("https://cloud.iexapis.com/v1/");
        marketDataConfig.setToken(System.getenv("IEX_PUB_TOKEN"));

        marketDataDao = new MarketDataDao(cm, marketDataConfig);
    }

    @Test
    public void findIexQuotesByTickers() throws IOException {
        // happy path
        List<IexQuote> quotes = marketDataDao.findAllById(Arrays.asList("AAPL" , "FB"));
        assertEquals(2, quotes.size());
        assertEquals("AAPL", quotes.get(0).getSymbol());

        // sad path
        try {
            marketDataDao.findAllById(Arrays.asList("A111" , "FB2"));
            fail();
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void findByTicker() {
        IexQuote quote = marketDataDao.findById("AAPL").get();
        assertEquals("AAPL", quote.getSymbol());
    }
}
