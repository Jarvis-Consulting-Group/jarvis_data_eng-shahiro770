package ca.jrvs.apps.twitter.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.util.JsonUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {

    @Mock
    Service service;

    @InjectMocks
    TwitterController twitterController;

    private final String tweetJsonStr = "{\n"
        + "\"data\": {\n"
        + "\"text\":\" Hello my sir! #onelove\","
        + "\"id\":\" 1645251409078566915\","
        + "\"entities\": {\n"
        + "\"hashtags\": [\n"
        + "{\n"
        + "\"start\": 14,"
        + "\"end\": 22,"
        + "\"tag\": \"onelove\"\n"
        + "}\n"
        + "]\n"
        + "},\n"
        + "\"edit_history_tweet_ids\": [\n"
        + "\"1645251409078566915\"\n"
        + "],"
        + "\"public_metrics\":{\n"
        + "\"retweet_count\": 0,"
        + "\"reply_count\": 0,"
        + "\"like_count\": 0,"
        + "\"quote_count\": 0,"
        + "\"impression_count\": 0"
        + "}\n"
        + "}\n"
        + "}\n";

    @Test
    public void postTweet() {
        Tweet testTweet = new Tweet();

        when(service.postTweet(any())).thenReturn(testTweet);
        TwitterController spyController = Mockito.spy(twitterController);
        Tweet response = spyController.postTweet(new String[2]);

        assertNotNull(response);
    }

    @Test
    public void showTweet() throws IOException {
        Tweet testTweet = JsonUtil.toObjectFromJson(tweetJsonStr, Tweet.class);
        String[] args = new String[] {"show", "1645251409078566915"};

        when(service.showTweet(any(), any())).thenReturn(testTweet);
        TwitterController spyController = Mockito.spy(twitterController);


        Tweet response = spyController.showTweet(args);

        assertEquals(response, testTweet);
    }

    @Test
    public void deleteTweet() throws IOException {
        Tweet testTweet = JsonUtil.toObjectFromJson(tweetJsonStr, Tweet.class);
        String[] args = new String[] {"delete", "1645251409078566915"};
        List<Tweet> deletedTweets = new ArrayList<>();
        deletedTweets.add(testTweet);

        when(service.deleteTweets(any())).thenReturn(deletedTweets);
        TwitterController spyController = Mockito.spy(twitterController);


        List<Tweet> response = spyController.deleteTweets(args);

        assertEquals(response.get(0), testTweet);
    }
}
