package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.Service;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Data;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class TwitterService implements Service {

    private CrdDao dao;

    public TwitterService(CrdDao dao) {
        this.dao = dao;
    }

    /**
     * Validate and post a user input Tweet
     *
     * @param tweet to be created
     * @return created tweet
     * @throws IllegalArgumentException if text exceed max number of allowed characters or lat/long
     *                                  out of range
     */
    @Override
    public Tweet postTweet(Tweet tweet) {
        validatePostTweet(tweet);
        return (Tweet) dao.post(tweet);
    }

    /**
     * Search a tweet by ID
     *
     * @param id     tweet id
     * @param fields set fields not in the list to null
     * @return Tweet object which is returned by the Twitter API
     * @throws IllegalArgumentException if id or fields param is invalid
     */
    @Override
    public Tweet showTweet(String id, String[] fields) {
        validateShowTweet(id, fields);

        Tweet response = (Tweet) dao.findById(id);
        Data responseData = response.getData();
        Tweet filtered = new Tweet();
        Data filteredData = new Data();

        if (fields == null) {
            filteredData = responseData;
        }
        else {
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].equals("id")) {
                    filteredData.setId(responseData.getId());
                }
                else if (fields[i].equals("text")) {
                    filteredData.setText(responseData.getText());
                }
                else if (fields[i].equals("entities")) {
                    filteredData.setEntities(responseData.getEntities());
                }
                else if (fields[i].equals("public_metrics")) {
                    filteredData.setPublicMetrics(responseData.getPublicMetrics());
                }
                else if (fields[i].equals("created_at")){
                    filteredData.setCreatedDate(responseData.getCreatedDate());
                }
            }
        }

        filtered.setData(filteredData);
        return filtered;
    }

    /**
     * Delete Tweet(s) by id(s).
     *
     * @param ids tweet IDs which will be deleted
     * @return A list of Tweets
     * @throws IllegalArgumentException if one of the IDs is invalid.
     */
    @Override
    public List<Tweet> deleteTweets(String[] ids) {
        Supplier<Stream<String>> streamSupplier =
            () -> Stream.of(ids);
        List<Tweet> deletedTweets = new ArrayList<Tweet>();

        streamSupplier.get().forEach(id -> isValidId(id));
        streamSupplier.get().forEach(id -> {
            deletedTweets.add((Tweet) dao.deleteById(id));
        });

        return deletedTweets;
    }

    public void validatePostTweet(Tweet tweet) {
        Data d = tweet.getData();

        if (d.getText().length() > 140 || d.getText().length() == 0) {
            throw new IllegalArgumentException("Tweet length must be greater than 0 characters"
                + "and no more than 140 characters");
        }
    }

    public void validateShowTweet(String id, String[] fields) {
        if (isValidId(id) == false) {
            throw new IllegalArgumentException("Tweet id is invalid. It must be a 64 bit unsigned integer");
        }
        if (fields != null) {
            for (int i = 0; i < fields.length; i++) {
                if ((fields[i].equals("id") || fields[i].equals("text") || fields[i].equals("entities")
                || fields[i].equals("public_metrics")) == false) {
                    throw new IllegalArgumentException(fields[i] + " is not a valid field specified");
                }
            }
        }
    }

    public void validateDeleteTweet(String id, String[] fields) {
        if (isValidId(id) == false) {
            throw new IllegalArgumentException("Tweet id is invalid. It must be a 64 bit unsigned integer");
        }
    }

    public boolean isValidId(String id) {
        for (int i = 0; i < id.length(); i++) {
            if (Character.isDigit(id.charAt((i))) == false) {
                return false;
            }
        }

        // twitter ids must be an unsigned 64 bit number, which is a long in java
        // positive longs in java have a maximum length of 19 digits
        // (i.e. max value 9,223,372,036,854,775,807)
        if (id.length() == 0 || id.length() > 19) {
            return false;
        }

        return true;
    }
}
