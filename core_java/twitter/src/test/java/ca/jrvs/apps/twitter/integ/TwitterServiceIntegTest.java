package ca.jrvs.apps.twitter.integ;

import static org.junit.Assert.assertEquals;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Data;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitterServiceIntegTest {

    private CrdDao dao;
    private TwitterService service;

    final static Logger logger = LoggerFactory.getLogger(TwitterServiceIntegTest.class);

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
        this.service = new TwitterService(dao);
    }

    @Test
    public void post_THEN_findById_THEN_DeleteById() {
        Tweet postTweet = new Tweet();
        postTweet.setData(buildTestData());

        Tweet postResponse = service.postTweet(postTweet);
        assertEquals(postTweet.getData().getText(), postResponse.getData().getText());

        Tweet showResponse = service.showTweet(postResponse.getData().getId(), null);
        assertEquals(postTweet.getData().getText(), showResponse.getData().getText());

        String[] ids = new String[] { showResponse.getData().getId() };

        List<Tweet> deleteResponse = service.deleteTweets(ids);

        assertEquals(deleteResponse.get(0).getData().isDeleted(), true);
    }

    private Data buildTestData() {
        Data data = new Data();
        data.setText("Testing posting, finding, and deleting 15");

        return data;
    }
}
