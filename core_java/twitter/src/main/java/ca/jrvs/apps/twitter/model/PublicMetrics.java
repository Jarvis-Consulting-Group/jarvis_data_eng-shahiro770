package ca.jrvs.apps.twitter.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties({
    "impression_count"
})
public class PublicMetrics {

    @JsonProperty("retweet_count")
    private int retweets;
    @JsonProperty("reply_count")
    private int replies;
    @JsonProperty("like_count")
    private int likes;
    @JsonProperty("quote_count")
    private int quotes;

    public int getRetweets() {
        return retweets;
    }

    public void setRetweets(int retweets) {
        this.retweets = retweets;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getQuotes() {
        return quotes;
    }

    public void setQuotes(int quotes) {
        this.quotes = quotes;
    }
}
