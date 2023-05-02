package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.service.QuoteService;
import org.apache.commons.dbcp2.DataSourceConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * Manually configured dataSource and JdbcTemplate for practice
 */
@SpringBootApplication(exclude = { JdbcTemplateAutoConfiguration.class,
    DataSourceConnectionFactory.class, HibernateJpaAutoConfiguration.class })
public class Application implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(Application.class);

    @Value("%{app.init.dailyList")
    private String[] initDailyList;

    @Autowired
    private QuoteService quoteService;

    public static void main(String args[]) throws Exception {
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
        System.out.println("Hello, World");
    }

    @Override
    public void run(String[] args)  throws Exception{

    }
}
