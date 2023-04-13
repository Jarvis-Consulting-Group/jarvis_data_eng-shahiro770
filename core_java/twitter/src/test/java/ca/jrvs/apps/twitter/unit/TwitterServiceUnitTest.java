package ca.jrvs.apps.twitter.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Data;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {

    @Mock
    CrdDao dao;

    @InjectMocks
    TwitterService service;

    @Test
    public void postTweet() {
        Tweet testTweet = new Tweet();
        Data testData = new Data();

        testData.setText("Testing");
        testTweet.setData(testData);

        when(dao.post(any())).thenReturn(new Tweet());
        Tweet response = service.postTweet(testTweet);
        assertNotNull(response);
    }

    @Test
    public void findById_THEN_deleteById() {
        Tweet testTweet = new Tweet();
        Data testData = new Data();

        testData.setId("1645251409078566915");
        testData.setText("Testing");
        testTweet.setData(testData);

        when(dao.findById(any())).thenReturn(testTweet);

        String[] fields = new String[] { "text" };

        Tweet findResponse = service.showTweet("1645251409078566915", fields);

        assertNull(findResponse.getData().getId());
        assertEquals(findResponse.getData().getText(), "Testing");

        when(dao.deleteById(any())).thenReturn(testTweet);

        String[] ids = new String[] { "1645251409078566915" };

        List<Tweet> deleteResponse = service.deleteTweets(ids);

        assertEquals(deleteResponse.size(), 1);
        assertNotNull(deleteResponse.get(0));
    }
}
