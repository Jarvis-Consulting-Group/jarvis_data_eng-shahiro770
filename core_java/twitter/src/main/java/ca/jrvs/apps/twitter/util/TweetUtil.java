package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Data;
import ca.jrvs.apps.twitter.model.Entities;
import ca.jrvs.apps.twitter.model.Hashtag;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;

public class TweetUtil {

    public static Tweet buildTweet(String text) {
        Tweet tweet = new Tweet();
        Data data = new Data();
        Entities entities = new Entities();

        data.setText(text);
        data.setEntities(entities);

        tweet.setData(data);

        return tweet;
    }

    private static Hashtag[] getHashTags(String text) {
        ArrayList<Hashtag> hashtags = new ArrayList<>();
        boolean searchHash = false;
        Hashtag currentHash = new Hashtag();

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '#' && searchHash == false) {
                currentHash.setStart(i);
                currentHash.setEnd(-1);
                searchHash = true;
            }
            else if ((text.charAt(i) == '#' || i == text.length() - 1) && searchHash == true) {
                currentHash.setEnd(i + 1);
                currentHash.setTag(text.substring(currentHash.getStart(), currentHash.getEnd()));
                hashtags.add(currentHash);
                searchHash = false;
            }
        }

        return  hashtags.toArray(new Hashtag[hashtags.size()]);
    }
}
