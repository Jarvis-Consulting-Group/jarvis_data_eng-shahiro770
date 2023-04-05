package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

public class TwitterDao implements CrdDao<Tweet, String> {

    // URI constants
    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String API_PATH = "/2/tweets";

    // URI symbols
    private static final String QUERY_SYM = "?";
    private static final String SLASH = "/";
    private static final String EQUAL = "=";

    // Response Code
    private static final int HTTP_OK = 200;
    private static final int HTTP_CREATED = 201;


    private HttpHelper httpHelper;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TwitterDao.class);

    /**
     * Constructor to set up dependencies
     *
     * @param httpHelper
     */
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
    public Tweet post(Tweet tweet) {
        URI uri;
        StringEntity entityBody;
        try {
            uri = getPostURI();
            entityBody = getPostEntity(tweet);
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Invalid tweet input", e);
        }

        // Execute HTTP Request
        HttpResponse response = httpHelper.httpPost(uri, entityBody);

        return parseResponseBody(response, HTTP_CREATED);
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

    private URI getPostURI() throws URISyntaxException {
        StringBuilder sb = new StringBuilder();
        sb.append(API_BASE_URI);
        sb.append(API_PATH);

        return new URI(sb.toString());
    }

    private StringEntity getPostEntity(Tweet tweet) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n\"text\":\"");
        sb.append(tweet.getData().getText());
        sb.append("\"\n}");

        return new StringEntity(sb.toString());
    }

    private URI getReadURI(String id) throws URISyntaxException {
        StringBuilder sb = new StringBuilder();
        PercentEscaper pe = new PercentEscaper("", false);

        sb.append(API_BASE_URI);
        sb.append(API_PATH);
        sb.append(SLASH);
        sb.append(pe.escape(id));
        sb.append(QUERY_SYM);
        sb.append("tweet.fields");
        sb.append(EQUAL);
        sb.append("created_at,entities,public_metrics");

        return new URI(sb.toString());
    }

    private URI getDeleteURI(String id) throws URISyntaxException {
        StringBuilder sb = new StringBuilder();
        PercentEscaper pe = new PercentEscaper("", false);

        sb.append(API_BASE_URI);
        sb.append(API_PATH);
        sb.append(SLASH);
        sb.append(pe.escape(id));

        return new URI(sb.toString());
    }
}
