package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Data;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Controller
public class TwitterController implements Controller {

    private Service service;

    @Autowired
    public TwitterController(Service service) {
        this.service = service;
    }

    /**
     * Parse user argument and post a tweet by calling service classes
     *
     * @param args
     * @return a posted tweet
     * @throws IllegalArgumentException if args are invalid
     */
    @Override
    public Tweet postTweet(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("USAGE: TwitterCLIApp post \"tweet_text\"");
        }

        String tweet_txt = args[1];

        Tweet postTweet = new Tweet();
        Data d = new Data();
        d.setText(tweet_txt);
        postTweet.setData(d);

        return service.postTweet(postTweet);
    }

    /**
     * Parse user argument and search a tweet by calling service classes
     *
     * @param args
     * @return a tweet
     * @throws IllegalArgumentException if args are invalid
     */
    @Override
    public Tweet showTweet(String[] args) {
        if (args.length < 2 || args.length > 3) {
            throw new IllegalArgumentException("USAGE: TwitterCLIApp show tweet_id \"field1,fields2,...\"");
        }

        String id = args[1];

        String[] fields = null;
        if (args.length == 3) {
            fields = args[2].split(",");
        }

        return service.showTweet(id, fields);
    }

    /**
     * Parse user argument and delete tweets by calling service classes
     *
     * @param args
     * @return a list of deleted tweets
     * @throws IllegalArgumentException if args are invalid
     */
    @Override
    public List<Tweet> deleteTweets(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("USAGE: TwitterCLIApp delete \"id1,id2,...\"");
        }

        String[] ids = args[1].split(",");

        return service.deleteTweets(ids);
    }
}