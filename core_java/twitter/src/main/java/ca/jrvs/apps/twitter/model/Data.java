package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties({
    "edit_history_tweet_ids"
})
public class Data {

    @JsonProperty("id")
    private String id;
    @JsonProperty("text")
    private String text;
    @JsonProperty("created_at")
    private Date createdDate;
    @JsonProperty("entities")
    private Entities entities;
    @JsonProperty("public_metrics")
    private PublicMetrics publicMetrics;
    @JsonProperty("deleted")
    private boolean deleted;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Entities getEntities() {
        return entities;
    }

    public void setEntities(Entities entities) {
        this.entities = entities;
    }

    public PublicMetrics getPublicMetrics() {
        return publicMetrics;
    }

    public void setPublicMetrics(PublicMetrics publicMetrics) {
        this.publicMetrics = publicMetrics;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Data{" +
            "createdDate=" + createdDate +
            ", id='" + id + '\'' +
            ", text='" + text + '\'' +
            ", entities=" + entities +
            ", publicMetrics=" + publicMetrics +
            '}';
    }
}
