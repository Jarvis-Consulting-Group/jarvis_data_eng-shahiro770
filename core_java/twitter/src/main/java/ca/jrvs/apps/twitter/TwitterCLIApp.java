package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

public class TwitterCLIApp {

    private TwitterController controller;

    public TwitterCLIApp(Controller controller) {
        this.controller = (TwitterController) controller;
    }

    public static void main(String[] args) {
        // get secrets from env vars
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        // create components and chain dependencies
        HttpHelper helper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        CrdDao dao = new TwitterDao(helper);
        Service service = new TwitterService(dao);
        Controller controller = new TwitterController(service);

        TwitterCLIApp app = new TwitterCLIApp(controller);

        app.run(args);
    }

    public void run (String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("USAGE: TwitterCLIApp post|show|delete \"options\"");
        }

        switch (args[0]) {
            case "post":
                printTweet(controller.postTweet(args));
                break;
            case "show":
                printTweet(controller.showTweet(args));
                break;
            case "delete":
                controller.deleteTweets(args).forEach(tweet -> printTweet(tweet));
                break;
            default:
                throw new RuntimeException("USAGE: TwitterCLIApp post|show|delete \"options\"");
        }
    }

    private void printTweet(Tweet tweet) {
        try {
            System.out.println(JsonUtil.toJson(tweet, true, false));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to convert tweet object to string", e);
        }
    }
}
