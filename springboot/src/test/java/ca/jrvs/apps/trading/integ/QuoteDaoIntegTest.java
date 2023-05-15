package ca.jrvs.apps.trading.integ;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteDaoIntegTest {

    @Autowired
    private QuoteDao quoteDao;
    private Quote savedQuote;

    @Before
    public void insertOne() {
        savedQuote.setAskPrice(10d);
        savedQuote.setAskSize(10);
        savedQuote.setBidPrice(10.2d);
        savedQuote.setBidSize(10);
        savedQuote.setId("aapl");
        savedQuote.setLastPrice(10.1d);
        quoteDao.save(savedQuote);
    }

    @After
    public void deleteOne() {
        quoteDao.deleteById(savedQuote.getId());
    }

    @Test
    public void saveExistsAndFindById() {
        Quote saveQuote = new Quote();

        saveQuote.setAskPrice(5d);
        saveQuote.setAskSize(10);
        saveQuote.setBidPrice(15d);
        saveQuote.setBidSize(5);
        saveQuote.setId("FB");
        saveQuote.setLastPrice(4d);
        Quote savedQuote = quoteDao.save(saveQuote);
        assertEquals(saveQuote.getId(), savedQuote.getId());

        boolean existsFB = quoteDao.existsById("FB");
        assertTrue(existsFB);

        Quote findQuote = quoteDao.findById("FB").get();
        assertEquals(findQuote.getAskPrice(), saveQuote.getAskPrice());
    }
}
