package ca.jrvs.apps.twitter;

import com.google.gdata.util.common.base.PercentEscaper;
import java.util.Arrays;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitterApiTest {
    private static String CONSUMER_KEY = System.getenv("consumerKey");
    private static String CONSUMER_SECRET = System.getenv("consumerSecret");
    private static String ACCESS_TOKEN = System.getenv("accessToken");
    private static String TOKEN_SECRET = System.getenv("tokenSecret");

    final static Logger logger = LoggerFactory.getLogger(TwitterApiTest.class);

    public static void main (String[] args) {
        // set up OAuth
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        consumer.setTokenWithSecret(ACCESS_TOKEN, TOKEN_SECRET);

        // create an HTTP GET request
        String status = "today is the day";
        PercentEscaper pe = new PercentEscaper("", false);
        HttpGet request = new HttpGet("https://api.twitter.com/2/tweets/1638538945314078723");

        try {
            consumer.sign(request);

            logger.info("Http Request Headers: ");
            Arrays.stream(request.getAllHeaders()).forEach(System.out::println); // method reference

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse response = httpClient.execute(request);
            logger.info("PRINTING:" + EntityUtils.toString(response.getEntity()));
        }
        catch (Exception e) {
            logger.error(e.toString());
        }
    }
}
