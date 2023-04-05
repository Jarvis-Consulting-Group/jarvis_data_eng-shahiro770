package ca.jrvs.apps.twitter.integ;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import ca.jrvs.apps.twitter.TwitterApiTest;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Data;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitterDaoIntegTest {

    final static Logger logger = LoggerFactory.getLogger(TwitterApiTest.class);

    private TwitterDao dao;

    @Before
    public void setup() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        logger.debug(consumerKey + " | " + consumerSecret + " | " + accessToken + " | " + tokenSecret);

        // Set up dependency
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        // pass dependency
        this.dao = new TwitterDao(httpHelper);
    }

    @Test
    public void post_THEN_findById_THEN_DeleteById() {
        Tweet postTweet = new Tweet();
        postTweet.setData(buildTestData());

        try {
            logger.debug(JsonUtil.toJson(postTweet, true, false));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Tweet postResponse = dao.post(postTweet);
        assertEquals(postTweet.getData().getText(), postResponse.getData().getText());
        try {
            logger.debug(JsonUtil.toJson(postResponse, true, false));
            logger.debug(postResponse.getData().getId());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Tweet findResponse = dao.findById(postResponse.getData().getId());
        assertEquals(postTweet.getData().getText(), findResponse.getData().getText());

        Tweet deleteResponse = dao.deleteById(findResponse.getData().getId());
        try {
            logger.debug(JsonUtil.toJson(deleteResponse, true, false));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(deleteResponse);
    }

    private Data buildTestData() {
        Data data = new Data();
        data.setText("Testing posting, finding, and deleting 13");

        return data;
    }
}
