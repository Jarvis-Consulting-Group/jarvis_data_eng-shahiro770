package ca.jrvs.apps.twitter.model;

import java.util.Date;

public class Tweet {

    private Date createdDate;

    private long id;

    private String idStr;

    private String text;

    private Entities entities;

    private PublicMetrics publicMetrics;


    public PublicMetrics getPublicMetrics() {
        return publicMetrics;
    }

    public void setOrgMetrics(PublicMetrics orgMetrics) {
        this.publicMetrics = orgMetrics;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
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
}
