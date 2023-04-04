package ca.jrvs.apps.twitter.dao.helper;

import java.io.IOException;
import java.net.URI;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.HttpMethod;

public class TwitterHttpHelper implements HttpHelper {

    // dependencies
    private OAuthConsumer consumer;
    private HttpClient httpClient;

    /**
     *  Constructor to set up dependencies using secrets
     *
     * @param consumerKey
     * @param consumerSecret
     * @param accessToken
     * @param tokenSecret
     */
    public TwitterHttpHelper(String consumerKey, String consumerSecret, String accessToken, String tokenSecret) {
        consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(accessToken, tokenSecret);

        httpClient = new DefaultHttpClient();   // only need a single connection for app purposes
    }

    /**
     * Execute a HTTP Post call
     *
     * @param uri
     * @return
     */
    @Override
    public HttpResponse httpPost(URI uri) {
        try {
            return executeHttpRequest(HttpMethod.POST, uri, null);
        } catch (OAuthException | IOException e) {
            throw new RuntimeException("Failed to execute", e);
        }
    }

    /**
     * Execute a HTTP Get call
     *
     * @param uri
     * @return
     */
    @Override
    public HttpResponse httpDelete(URI uri) {
        try {
            return executeHttpRequest(HttpMethod.DELETE, uri, null);
        } catch (OAuthException | IOException e) {
            throw new RuntimeException("Failed to execute", e);
        }
    }

    /**
     * Execute a HTTP Get call
     *
     * @param uri
     * @return
     */
    @Override
    public HttpResponse httpGet(URI uri) {
        try {
            return executeHttpRequest(HttpMethod.GET, uri, null);
        } catch (OAuthException | IOException e) {
            throw new RuntimeException("Failed to execute", e);
        }
    }

    private HttpResponse executeHttpRequest(HttpMethod method, URI uri, StringEntity stringEntity)
        throws OAuthException, IOException {
        switch (method) {
            case GET:
                HttpGet getRequest = new HttpGet(uri);
                consumer.sign(getRequest);
                return httpClient.execute(getRequest);
            case POST:
                HttpPost postRequest = new HttpPost(uri);
                if (stringEntity != null) {
                    postRequest.setEntity(stringEntity);
                }
                consumer.sign(postRequest);
                return httpClient.execute(postRequest);
            case DELETE:
                HttpDelete deleteRequest = new HttpDelete(uri);
                consumer.sign(deleteRequest);
                return httpClient.execute(deleteRequest);
            default:
                return null;
        }
    }
}
