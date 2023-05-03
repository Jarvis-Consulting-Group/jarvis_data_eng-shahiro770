package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
// @EnableTransactionManagement
public class AppConfig {

    private Logger logger = LoggerFactory.getLogger(AppConfig.class);



    @Bean
    public MarketDataConfig marketDataConfig() {
        MarketDataConfig marketDataConfig = new MarketDataConfig();
        marketDataConfig.setHost("https://cloud.iexapis.com/v1/");
        System.out.println(System.getenv("IEX_PUB_TOKEN"));
        marketDataConfig.setToken(System.getenv("IEX_PUB_TOKEN"));

        return marketDataConfig;
    }

    @Bean
    public HttpClientConnectionManager getConnectionManager() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);

        return cm;
    }

    @Bean
    public DataSource getDataSource() {
        StringBuilder jdbcUrl = new StringBuilder();
        jdbcUrl.append("jdbc:postgresql://");
        jdbcUrl.append(System.getenv("PSQL_HOST"));
        jdbcUrl.append(":");
        jdbcUrl.append(System.getenv("PSQL_PORT"));
        jdbcUrl.append("/");
        jdbcUrl.append( System.getenv("PSQL_DB"));
        String user = System.getenv("PSQL_USER");
        String password = System.getenv("PSQL_PASSWORD");

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(jdbcUrl.toString());
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);
        return basicDataSource;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
