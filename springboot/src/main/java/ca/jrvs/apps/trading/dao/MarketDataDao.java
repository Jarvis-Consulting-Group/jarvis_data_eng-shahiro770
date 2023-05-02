package ca.jrvs.apps.trading.dao;


import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.util.JsonUtil;
import com.google.common.net.PercentEscaper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.swing.text.html.Option;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.omg.CORBA.DynAnyPackage.Invalid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MarketDataDao implements CrudRepository<IexQuote, String> {

    private static final String IEX_BATCH_PATH = "stock/market/batch?symbols=%s&types=quote&token=";
    private static final Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
    private final String IEX_BATCH_URL;
    private HttpClientConnectionManager httpClientConnectionManager;

    public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager, MarketDataConfig marketDataConfig) {
        this.httpClientConnectionManager = httpClientConnectionManager;
        IEX_BATCH_URL = marketDataConfig.getHost() + IEX_BATCH_PATH + marketDataConfig.getToken();
    }

    @Override
    public <S extends IexQuote> S save(S s) {
        return null;
    }

    @Override
    public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    /**
     * Get an IexQuote
     *
     * @param ticker
     * @return IexQuote
     * @throws DataRetrievalFailureException if HTTP request failed
     */
    @Override
    public Optional<IexQuote> findById(String ticker) {
        Optional<IexQuote> iexQuote;
        List<IexQuote> quotes = findAllById(Collections.singletonList(ticker));

        if (quotes.size() == 0) {
            return Optional.empty();
        }
        else if (quotes.size() == 1) {
            iexQuote = Optional.of(quotes.get(0));
        }
        else {
            throw new DataRetrievalFailureException("Unexpected number of quotes");
        }

        return iexQuote;
    }

    public List<IexQuote> findAllById(Iterable<String> tickers) {
        try {
            Optional<String> response = executeHttpGet(getURI(tickers));

            if (response.isPresent()) {
                JSONObject quotesJson = new JSONObject(response.get());
                if (quotesJson.length() == 0 || quotesJson.length() != ((List<String>)tickers).size()) {
                    throw new IllegalArgumentException("Invalid ticker symbols given");
                }
                return parseResponseBody(quotesJson, tickers);
            }
            else {
                throw  new IllegalArgumentException("Invalid ticker symbols given");
            }
        }
        catch (URISyntaxException | IOException e) {
            throw new IllegalArgumentException("URI construction failed due to invalid ticker symbols");
        }
    }

    private Optional<String> executeHttpGet (URI url) {
        Optional<String> responseBody;
        HttpGet httpGet = new HttpGet(url);

        try (CloseableHttpClient closeableHttpClient = getHttpClient();
            CloseableHttpResponse response = closeableHttpClient.execute(httpGet)) {

            StatusLine responseStatus = response.getStatusLine();

            if (responseStatus.getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                responseBody = Optional.empty();
            }
            else if (responseStatus.getStatusCode() == HttpStatus.SC_OK) {
                responseBody = Optional.of(EntityUtils.toString(response.getEntity()));
            }
            else {
                throw new DataRetrievalFailureException("Unexpected HTTP request result or status code:\n" + response.toString());
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        return responseBody;
    }

    private URI getURI(Iterable<String> tickers) throws URISyntaxException {
        StringBuilder tickerChain = new StringBuilder();
        Iterator<String> tickerIterator = tickers.iterator();

        while (tickerIterator.hasNext()) {
            tickerChain.append(tickerIterator.next() + ",");
        }
        String modifiedBatchPath = IEX_BATCH_URL.replace("%s", tickerChain.toString());

        return new URI(modifiedBatchPath);
    }


    private CloseableHttpClient getHttpClient() {
        return HttpClients.custom()
            .setConnectionManager(httpClientConnectionManager)
            .setConnectionManagerShared(true)
            .build();
    }

    public List<IexQuote> parseResponseBody(JSONObject quotesJson, Iterable<String> tickers)
        throws IOException {
        List<IexQuote> quotes = new ArrayList<IexQuote>();
        Iterator<String> tickerIterator = tickers.iterator();

        // Check response status
        while (tickerIterator.hasNext()) {
            String quoteJson = quotesJson.getJSONObject(tickerIterator.next())
                .getJSONObject("quote")
                .toString();
            quotes.add(JsonUtil.toObjectFromJson(quoteJson, IexQuote.class));
        }

        return quotes;
    }

    @Override
    public boolean existsById(String s) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public Iterable<IexQuote> findAll() {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void deleteById(String s) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void delete(IexQuote iexQuote) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void deleteAll(Iterable<? extends IexQuote> iterable) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not supported");
    }
}