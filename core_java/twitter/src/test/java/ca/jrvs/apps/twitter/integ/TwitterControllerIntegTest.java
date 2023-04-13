package ca.jrvs.apps.twitter.integ;

import static org.junit.Assert.assertEquals;

import ca.jrvs.apps.twitter.controller.TwitterController;
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

public class TwitterControllerIntegTest {

    private CrdDao dao;
    private TwitterService service;
    private TwitterController controller;

    private static final String POST = "post";
    private static final String SHOW = "show";
    private static final String DELETE = "delete";


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
        // pass dependencies
        this.dao = new TwitterDao(httpHelper);
        this.service = new TwitterService(dao);
        this.controller = new TwitterController(service);
    }

    @Test
    public void post_THEN_findById_THEN_DeleteById() {
        Tweet postTweet = new Tweet();
        postTweet.setData(buildTestData());

        String[] postArgs = new String[] { POST, postTweet.getData().getText() };
        Tweet postResponse = controller.postTweet(postArgs);
        assertEquals(postTweet.getData().getText(), postResponse.getData().getText());

        String[] showArgs = new String[] { SHOW, postResponse.getData().getId() };
        Tweet showResponse = controller.showTweet(showArgs);
        assertEquals(postTweet.getData().getText(), showResponse.getData().getText());

        String[] ids = new String[] { DELETE, showResponse.getData().getId() };

        List<Tweet> deleteResponse = controller.deleteTweets(ids);

        assertEquals(deleteResponse.get(0).getData().isDeleted(), true);
    }

    private Data buildTestData() {
        Data data = new Data();
        data.setText("Testing posting, finding, and deleting 16");

        return data;
    }
}
