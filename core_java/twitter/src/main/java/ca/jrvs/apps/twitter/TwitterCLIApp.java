package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TwitterCLIApp {

    private TwitterController controller;

    @Autowired
    public TwitterCLIApp(Controller controller) {
        this.controller = (TwitterController) controller;
    }

    /**
     * Instructs the controller to perform a specific action based on the user's input
     * @param args command line arguments passed in when running the class
     */
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
