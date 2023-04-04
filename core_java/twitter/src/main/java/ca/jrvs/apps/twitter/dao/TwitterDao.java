package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

public class TwitterDao implements CrdDao<Tweet, String> {

    // URI constants
    private static final String API_BASE_URI = "https://api.twitter.com/";
    private static final String POST_PATH = "/2/tweets/";
    private static final String READ_PATH = "/2/tweets/";
    private static final String DELETE_PATH = "/2/tweets/";
    // URI symbols
    private static final String QUERY_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";

    // Response Code
    private static final int HTTP_OK = 200;

    private HttpHelper httpHelper;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TwitterDao.class);

    public TwitterDao(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    /**
     * Create an entity(Tweet) to the underlying storage
     *
     * @param tweet entity that to be created
     * @return created entity
     */
    @Override
    public Tweet create(Tweet tweet) {
        URI uri;
        try {
            uri = getPostURI(tweet);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid tweet input", e);
        }

        // Execute HTTP Request
        HttpResponse response = httpHelper.httpPost(uri);

        return parseResponseBody(response, HTTP_OK);
    }

    /**
     * Find an entity(Tweet) by its id
     *
     * @param id entity id
     * @return Tweet entity
     */
    @Override
    public Tweet findById(String id) {
        URI uri;
        try {
            uri = getReadURI(id);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid tweet input", e);
        }

        // Execute HTTP Request
        HttpResponse response = httpHelper.httpGet(uri);

        return parseResponseBody(response, HTTP_OK);
    }

    /**
     * Delete an entity(Tweet) by its ID
     *
     * @param id of the entity to be deleted
     * @return deleted entity
     */
    @Override
    public Tweet deleteById(String id) {
        URI uri;
        try {
            uri = getDeleteURI(id);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid tweet input", e);
        }

        // Execute HTTP Request
        HttpResponse response = httpHelper.httpDelete(uri);

        return parseResponseBody(response, HTTP_OK);
    }

    private Tweet parseResponseBody(HttpResponse response, int expectedStatusCode) {
        Tweet tweet = null;

        // Check response status
        int status = response.getStatusLine().getStatusCode();
        if (status != expectedStatusCode) {
            try {
                logger.info(EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                logger.warn("Response has no entity");
            }
            throw new RuntimeException("Unexpected HTTP status: " + status);
        }

        if (response.getEntity() == null) {
            throw new RuntimeException("Empty response body");
        }

        // Process the response entity, converting it to a json string and then deserializing it
        // into a Tweet object
        String jsonStr;
        try {
            jsonStr = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert entity to String", e);
        }

        try {
            tweet = JsonUtil.toObjectFromJson(jsonStr, Tweet.class);
        } catch (IOException e) {
            throw new RuntimeException("Unable to convert JSON str to Tweet Object", e);
        }

        return tweet;
    }

    private URI getPostURI(Tweet tweet) throws URISyntaxException {
        StringBuilder sb = new StringBuilder();
        sb.append(API_BASE_URI);
        sb.append(POST_PATH);

        return new URI(sb.toString());
    }

    private URI getReadURI(String id) throws URISyntaxException {
        StringBuilder sb = new StringBuilder();
        sb.append(API_BASE_URI);
        sb.append(READ_PATH);
        sb.append(id);

        return new URI(sb.toString());
    }

    private URI getDeleteURI(String id) throws URISyntaxException {
        StringBuilder sb = new StringBuilder();
        sb.append(API_BASE_URI);
        sb.append(DELETE_PATH);
        sb.append(id);

        return new URI(sb.toString());
    }
}
