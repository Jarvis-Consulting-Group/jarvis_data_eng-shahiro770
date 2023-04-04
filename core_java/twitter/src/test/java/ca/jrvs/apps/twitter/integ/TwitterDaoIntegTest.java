package ca.jrvs.apps.twitter.integ;

import ca.jrvs.apps.twitter.TwitterApiTest;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
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

        logger.debug(consumerKey + "|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);

        // Set up dependency
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        // pass dependency
        this.dao = new TwitterDao(httpHelper);
    }

    @Test
    public void create() {
        String hashTag = "#abc";
        String text = "@someone test text " + hashTag + " " + System.currentTimeMillis();
        Double lat = 1d;
        double lon = -1d;
        Tweet postTweet = TweetUtil.buildTweet(text, lon, lat);
        logger.debug(JsonUtil.toJson(postTweet, true, false));
    }

    @Test
    public void findById() {
    }

    @Test
    public void deleteById() {
    }
}
