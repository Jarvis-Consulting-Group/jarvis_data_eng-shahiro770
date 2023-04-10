package ca.jrvs.apps.twitter.unit;

import static org.junit.Assert.assertNotNull;

import ca.jrvs.apps.twitter.TwitterApiTest;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import com.google.gdata.util.common.base.PercentEscaper;
import java.net.URI;
import java.util.StringTokenizer;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitterHttpHelperTest {

    private static final Logger logger = LoggerFactory.getLogger(TwitterApiTest.class);
    private static final int ID_TOKEN_NUM = 5;

    private HttpHelper httpHelper;

    @Before
    public void setup() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        logger.debug(consumerKey + " | " + consumerSecret + " | " + accessToken + " | " + tokenSecret);
    }

    @Test
    public void httpPost() throws Exception {
        String jsonText = "{\n\"text\": \"Testing?\"\n}";
        HttpResponse response = httpHelper.httpPost(new URI("https://api.twitter.com/2/tweets"), new StringEntity(jsonText));
        assertNotNull(response);
        logger.debug(EntityUtils.toString(response.getEntity()));
    }

    @Test
    public void httpGet() throws Exception {
        String testTweetId = "1644037234377539586";
        PercentEscaper pe = new PercentEscaper("", false);
        HttpResponse response = httpHelper.httpGet(new URI("https://api.twitter.com/2/tweets/" + pe.escape(testTweetId)));
        assertNotNull(response);
        logger.debug(EntityUtils.toString(response.getEntity()));
    }

    @Test
    public void httpDelete() throws Exception {
        String testTweetId = "1644037234377539586";
        PercentEscaper pe = new PercentEscaper("", false);
        HttpResponse response = httpHelper.httpDelete(new URI("https://api.twitter.com/2/tweets/" + pe.escape(testTweetId)));
        assertNotNull(response);
        logger.debug(EntityUtils.toString(response.getEntity()));
    }

    private String parseId(String json) {
        StringTokenizer st = new StringTokenizer(json, "\"");
        int tokenCount = 0;
        while (tokenCount < ID_TOKEN_NUM) {
            st.nextToken();
            tokenCount++;
        }

        return st.nextToken();
    }
}
