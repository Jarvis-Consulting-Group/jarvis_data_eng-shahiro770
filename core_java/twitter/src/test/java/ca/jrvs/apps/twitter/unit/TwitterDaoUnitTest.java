package ca.jrvs.apps.twitter.unit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Data;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {

    @Mock
    HttpHelper mockHelper;

    @InjectMocks
    TwitterDao dao;

    // created through https://jsontostring.com/
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
    public void post() throws IOException {
        Tweet postTweet = new Tweet();
        Data tweetData = new Data();
        tweetData.setText("Test post tweet");
        tweetData.setDeleted(false);
        postTweet.setData(tweetData);

        when(mockHelper.httpPost(isNotNull(), isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);

        Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr, Tweet.class);
        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());

        Tweet tweet = spyDao.post(postTweet);

        assertNotNull(tweet);
        assertNotNull(tweet.getData().getText());
    }

    @Test
    public void findById() throws IOException {
        when(mockHelper.httpGet(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);

        Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr, Tweet.class);
        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());

        Tweet tweet = spyDao.findById("1645251409078566915");

        assertNotNull(tweet);
        assertNotNull(tweet.getData().getText());
    }

    @Test
    public void delete() throws IOException {
        when(mockHelper.httpDelete(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);

        Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr, Tweet.class);
        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());

        Tweet tweet = spyDao.deleteById("1645251409078566915");

        assertNotNull(tweet);
        assertNotNull(tweet.getData().getText());
    }

    @Test
    public void unhappyPath() {
        Tweet sadTweet = new Tweet();

        try {
            dao.post(sadTweet);
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }

        when(mockHelper.httpGet(isNotNull())).thenThrow(new RuntimeException("bad data"));
        try {
            dao.findById("1645251409078566915");
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }

        when(mockHelper.httpDelete(isNotNull())).thenThrow(new RuntimeException("bad data"));
        try {
            dao.deleteById("1645251409078566915");
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }
    }
}
